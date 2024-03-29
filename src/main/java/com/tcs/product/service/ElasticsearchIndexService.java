package com.tcs.product.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcs.product.domain.*;
import com.tcs.product.repository.*;
import com.tcs.product.repository.search.*;

import io.micrometer.core.annotation.Timed;

import org.elasticsearch.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ProductRepository productRepository;

    private final ProductSearchRepository productSearchRepository;

    private final ElasticsearchOperations elasticsearchTemplate;

    @Value("#{systemEnvironment['REINDEX_ELASTICSEARCH'] ?: ''}")
    private String reindex_elasticsearch;

    public ElasticsearchIndexService(
        ProductRepository productRepository,
        ProductSearchRepository productSearchRepository,
        ElasticsearchOperations elasticsearchTemplate) {
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent ) {
        if (this.reindex_elasticsearch.isEmpty()) {
            log.info("You may reindex all ElasticSearch entities on start-up with the REINDEX_ELASTICSEARCH environment variable set");
        } else {
            log.info("Reindexing all ElasticSearch entities");
            this.reindexSelected(null, true);
        }
    }

    @Async
    @Timed
    public void reindexSelected(List<String> classesForReindex, boolean all) {
        if (reindexLock.tryLock()) {
            try {
                if (all || classesForReindex.contains("Product")) {
                    reindexForClass(Product.class, productRepository, productSearchRepository);
                }

                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }

    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        String className = entityClass.getSimpleName();
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (ResourceAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            className, field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = PageRequest.of(i, size);
                log.info("Indexing {} page {} of {}, size {}", className, i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.saveAll(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", className);
    }
}
