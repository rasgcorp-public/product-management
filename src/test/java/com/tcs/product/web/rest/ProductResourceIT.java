package com.tcs.product.web.rest;

import com.tcs.product.ProductManagementApp;
import com.tcs.product.domain.Product;
import com.tcs.product.repository.ProductRepository;
import com.tcs.product.repository.search.ProductSearchRepository;
import com.tcs.product.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.tcs.product.web.rest.TestUtil.sameInstant;
import static com.tcs.product.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = ProductManagementApp.class)
public class ProductResourceIT {

    private static final Double DEFAULT_ID_PRODUTO = 1D;
    private static final Double UPDATED_ID_PRODUTO = 2D;
    private static final Double SMALLER_ID_PRODUTO = 1D - 1D;

    private static final String DEFAULT_COD_RECIPIENTE = "AAAAAAAAAA";
    private static final String UPDATED_COD_RECIPIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TP_EMBALAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TP_EMBALAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_DISPOSITIVO = 1D;
    private static final Double UPDATED_ID_DISPOSITIVO = 2D;
    private static final Double SMALLER_ID_DISPOSITIVO = 1D - 1D;

    private static final String DEFAULT_UNIDADE_ESTOQUE = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_ESTOQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NALADINCCA = "AAAAAAAAAA";
    private static final String UPDATED_NALADINCCA = "BBBBBBBBBB";

    private static final String DEFAULT_NCM = "AAAAAAAAAA";
    private static final String UPDATED_NCM = "BBBBBBBBBB";

    private static final String DEFAULT_NALADISH = "AAAAAAAAAA";
    private static final String UPDATED_NALADISH = "BBBBBBBBBB";

    private static final String DEFAULT_LINHA_PRODUTO = "AAAAAAAAAA";
    private static final String UPDATED_LINHA_PRODUTO = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO_BRUTO = 1D;
    private static final Double UPDATED_PESO_BRUTO = 2D;
    private static final Double SMALLER_PESO_BRUTO = 1D - 1D;

    private static final Double DEFAULT_PESO_LIQUIDO = 1D;
    private static final Double UPDATED_PESO_LIQUIDO = 2D;
    private static final Double SMALLER_PESO_LIQUIDO = 1D - 1D;

    private static final String DEFAULT_REGISTRO_MS = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRO_MS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_VALIDADE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALIDADE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VALIDADE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_NECESSITA_LI = "AAAAAAAAAA";
    private static final String UPDATED_NECESSITA_LI = "BBBBBBBBBB";

    private static final String DEFAULT_RECOF = "AAAAAAAAAA";
    private static final String UPDATED_RECOF = "BBBBBBBBBB";

    private static final Double DEFAULT_REDUCAO_ICMS = 1D;
    private static final Double UPDATED_REDUCAO_ICMS = 2D;
    private static final Double SMALLER_REDUCAO_ICMS = 1D - 1D;

    private static final String DEFAULT_COD_ONU = "AAAAAAAAAA";
    private static final String UPDATED_COD_ONU = "BBBBBBBBBB";

    private static final String DEFAULT_SEQ_SUFRAMA = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_SUFRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_NAO_TRIBUTAVEL = "AAAAAAAAAA";
    private static final String UPDATED_NAO_TRIBUTAVEL = "BBBBBBBBBB";

    private static final String DEFAULT_IPI_ESPECIFICO = "AAAAAAAAAA";
    private static final String UPDATED_IPI_ESPECIFICO = "BBBBBBBBBB";

    private static final String DEFAULT_II_ESPECIFICO = "AAAAAAAAAA";
    private static final String UPDATED_II_ESPECIFICO = "BBBBBBBBBB";

    private static final Double DEFAULT_II = 1D;
    private static final Double UPDATED_II = 2D;
    private static final Double SMALLER_II = 1D - 1D;

    private static final Double DEFAULT_IPI = 1D;
    private static final Double UPDATED_IPI = 2D;
    private static final Double SMALLER_IPI = 1D - 1D;

    private static final Double DEFAULT_VALOR_UNITARIA = 1D;
    private static final Double UPDATED_VALOR_UNITARIA = 2D;
    private static final Double SMALLER_VALOR_UNITARIA = 1D - 1D;

    private static final Double DEFAULT_CAPACIDADE_UNITARIA = 1D;
    private static final Double UPDATED_CAPACIDADE_UNITARIA = 2D;
    private static final Double SMALLER_CAPACIDADE_UNITARIA = 1D - 1D;

    private static final Double DEFAULT_FATOR_CONVERSAO = 1D;
    private static final Double UPDATED_FATOR_CONVERSAO = 2D;
    private static final Double SMALLER_FATOR_CONVERSAO = 1D - 1D;

    private static final String DEFAULT_DESCRICAO_RESUMIDA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_RESUMIDA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ATUALIZACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATUALIZACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ATUALIZACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE_PESO = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_PESO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_UNIDADE_QTDE = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_QTDE = "BBBBBBBBBB";

    private static final String DEFAULT_COD_UNIDADE_COMERCIALIZADA = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_COMERCIALIZADA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_UNIDADE_UNITARIA = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_UNITARIA = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO_QUILO = 1D;
    private static final Double UPDATED_PESO_QUILO = 2D;
    private static final Double SMALLER_PESO_QUILO = 1D - 1D;

    private static final Double DEFAULT_PESO_UNID_COMERCIALIZADA = 1D;
    private static final Double UPDATED_PESO_UNID_COMERCIALIZADA = 2D;
    private static final Double SMALLER_PESO_UNID_COMERCIALIZADA = 1D - 1D;

    private static final String DEFAULT_COD_EXTERNO_GIP = "AAAAAAAAAA";
    private static final String UPDATED_COD_EXTERNO_GIP = "BBBBBBBBBB";

    private static final Double DEFAULT_ULTIMO_INFORMANTE = 1D;
    private static final Double UPDATED_ULTIMO_INFORMANTE = 2D;
    private static final Double SMALLER_ULTIMO_INFORMANTE = 1D - 1D;

    private static final String DEFAULT_TSP = "AAAAAAAAAA";
    private static final String UPDATED_TSP = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_RECOF = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_RECOF = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_PESO_RATEAVEL = "AAAAAAAAAA";
    private static final String UPDATED_PESO_RATEAVEL = "BBBBBBBBBB";

    private static final String DEFAULT_NECESSITA_REVISAO = "AAAAAAAAAA";
    private static final String UPDATED_NECESSITA_REVISAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_PRODUTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PRODUTO = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CHASSI = "AAAAAAAAAA";
    private static final String UPDATED_CHASSI = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIFICACAO_TECNICA = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIFICACAO_TECNICA = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIA_PRIMA_BASICA = "AAAAAAAAAA";
    private static final String UPDATED_MATERIA_PRIMA_BASICA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOMATICO = "AAAAAAAAAA";
    private static final String UPDATED_AUTOMATICO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_COD_ORIGEM = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_GENERICO = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_GENERICO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGA_PERIGOSA = "AAAAAAAAAA";
    private static final String UPDATED_CARGA_PERIGOSA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_UNIDADE_VENDA = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_VENDA = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_DETALHADA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_DETALHADA = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_ORGANIZACAO = 1D;
    private static final Double UPDATED_ID_ORGANIZACAO = 2D;
    private static final Double SMALLER_ID_ORGANIZACAO = 1D - 1D;

    private static final String DEFAULT_COD_PAIS_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_COD_PAIS_ORIGEM = "BBBBBBBBBB";

    private static final Double DEFAULT_CICLO_PRODUTIVO = 1D;
    private static final Double UPDATED_CICLO_PRODUTIVO = 2D;
    private static final Double SMALLER_CICLO_PRODUTIVO = 1D - 1D;

    private static final String DEFAULT_PART_NUMBER_FORNECEDOR = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER_FORNECEDOR = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG_ATUALIZA_ICMS = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_ATUALIZA_ICMS = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_DISPOSITIVO_IPI = 1D;
    private static final Double UPDATED_ID_DISPOSITIVO_IPI = 2D;
    private static final Double SMALLER_ID_DISPOSITIVO_IPI = 1D - 1D;

    private static final String DEFAULT_CODIGO_MOEDA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_MOEDA = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR_UNITARIO = 1D;
    private static final Double UPDATED_VALOR_UNITARIO = 2D;
    private static final Double SMALLER_VALOR_UNITARIO = 1D - 1D;

    private static final String DEFAULT_COD_PROD = "AAAAAAAAAA";
    private static final String UPDATED_COD_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_COD_PRODUCAO = "AAAAAAAAAA";
    private static final String UPDATED_COD_PRODUCAO = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDENCIA_EXP = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDENCIA_EXP = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_ANUENCIA = 1D;
    private static final Double UPDATED_ID_ANUENCIA = 2D;
    private static final Double SMALLER_ID_ANUENCIA = 1D - 1D;

    private static final Double DEFAULT_PESO_METRO_CUBICO = 1D;
    private static final Double UPDATED_PESO_METRO_CUBICO = 2D;
    private static final Double SMALLER_PESO_METRO_CUBICO = 1D - 1D;

    private static final String DEFAULT_HTS = "AAAAAAAAAA";
    private static final String UPDATED_HTS = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMERCIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_MODELO = 1D;
    private static final Double UPDATED_ID_MODELO = 2D;
    private static final Double SMALLER_ID_MODELO = 1D - 1D;

    private static final String DEFAULT_UNIDADE_FRACIONADA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_FRACIONADA = "BBBBBBBBBB";

    private static final Double DEFAULT_DIF_PESO_EMB = 1D;
    private static final Double UPDATED_DIF_PESO_EMB = 2D;
    private static final Double SMALLER_DIF_PESO_EMB = 1D - 1D;

    private static final String DEFAULT_CLASS_PROD_RECOF = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_PROD_RECOF = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_FIM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_INSERT_MOV = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_INSERT_MOV = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_INSERT_MOV = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_ID_CORPORATIVO = "AAAAAAAAAA";
    private static final String UPDATED_ID_CORPORATIVO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_GER_LEG = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_GER_LEG = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_GER_LEG = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_PROCEDENCIA_INFO = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDENCIA_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_PROD_SUFRAMA = "AAAAAAAAAA";
    private static final String UPDATED_COD_PROD_SUFRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_PX_EXP_TIPOINS = "AAAAAAAAAA";
    private static final String UPDATED_PX_EXP_TIPOINS = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_PROD_SUFRAMA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROD_SUFRAMA = "BBBBBBBBBB";

    private static final Double DEFAULT_ID_DETALHE_SUFRAMA = 1D;
    private static final Double UPDATED_ID_DETALHE_SUFRAMA = 2D;
    private static final Double SMALLER_ID_DETALHE_SUFRAMA = 1D - 1D;

    private static final Double DEFAULT_VALOR_UNITARIO_REAL = 1D;
    private static final Double UPDATED_VALOR_UNITARIO_REAL = 2D;
    private static final Double SMALLER_VALOR_UNITARIO_REAL = 1D - 1D;

    private static final String DEFAULT_NECESSITA_REVISAO_PEXPAM = "AAAAAAAAAA";
    private static final String UPDATED_NECESSITA_REVISAO_PEXPAM = "BBBBBBBBBB";

    private static final Double DEFAULT_MODELO = 1D;
    private static final Double UPDATED_MODELO = 2D;
    private static final Double SMALLER_MODELO = 1D - 1D;

    private static final Double DEFAULT_PX_MODELO_PADRAO = 1D;
    private static final Double UPDATED_PX_MODELO_PADRAO = 2D;
    private static final Double SMALLER_PX_MODELO_PADRAO = 1D - 1D;

    private static final String DEFAULT_FLEX_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_6 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_7 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_7 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_8 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_8 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_9 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_9 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_10 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_10 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_11 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_11 = "BBBBBBBBBB";

    private static final String DEFAULT_PIS_COFINS_TIPO_APLIC = "AAAAAAAAAA";
    private static final String UPDATED_PIS_COFINS_TIPO_APLIC = "BBBBBBBBBB";

    private static final Double DEFAULT_PIS = 1D;
    private static final Double UPDATED_PIS = 2D;
    private static final Double SMALLER_PIS = 1D - 1D;

    private static final Double DEFAULT_COFINS = 1D;
    private static final Double UPDATED_COFINS = 2D;
    private static final Double SMALLER_COFINS = 1D - 1D;

    private static final Double DEFAULT_PIS_COFINS_RED_BASE = 1D;
    private static final Double UPDATED_PIS_COFINS_RED_BASE = 2D;
    private static final Double SMALLER_PIS_COFINS_RED_BASE = 1D - 1D;

    private static final String DEFAULT_MODELO_PROD_SUFRAMA = "AAAAAAAAAA";
    private static final String UPDATED_MODELO_PROD_SUFRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_SISCOMEX_UNIDADE_NCM = "AAAAAAAAAA";
    private static final String UPDATED_COD_SISCOMEX_UNIDADE_NCM = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER_CLIENTE = "BBBBBBBBBB";

    private static final Double DEFAULT_SUPERFICIE_UNITARIA = 1D;
    private static final Double UPDATED_SUPERFICIE_UNITARIA = 2D;
    private static final Double SMALLER_SUPERFICIE_UNITARIA = 1D - 1D;

    private static final String DEFAULT_LOCAL_ESTOQUE = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ESTOQUE = "BBBBBBBBBB";

    private static final String DEFAULT_COD_UNIDADE_SUPERFICIE = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_SUPERFICIE = "BBBBBBBBBB";

    private static final String DEFAULT_RATEIO_PRODUTO_ACABADO = "AAAAAAAAAA";
    private static final String UPDATED_RATEIO_PRODUTO_ACABADO = "BBBBBBBBBB";

    private static final String DEFAULT_PIS_COFINS_COD_UN_ESPEC = "AAAAAAAAAA";
    private static final String UPDATED_PIS_COFINS_COD_UN_ESPEC = "BBBBBBBBBB";

    private static final String DEFAULT_RECUPERA_IMPOSTOS = "AAAAAAAAAA";
    private static final String UPDATED_RECUPERA_IMPOSTOS = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG_NO_RAF = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_NO_RAF = "BBBBBBBBBB";

    private static final String DEFAULT_NOTA_COMPL_TIPI = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_COMPL_TIPI = "BBBBBBBBBB";

    private static final Double DEFAULT_IPI_REDUZIDO = 1D;
    private static final Double UPDATED_IPI_REDUZIDO = 2D;
    private static final Double SMALLER_IPI_REDUZIDO = 1D - 1D;

    private static final String DEFAULT_SUJEITO_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_SUJEITO_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_MARCA_COMERCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_EMBALAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_EMBALAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_LIBERACAO_BRASILIA = "AAAAAAAAAA";
    private static final String UPDATED_NUM_LIBERACAO_BRASILIA = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURA_CONSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURA_CONSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_UMIDADE = "AAAAAAAAAA";
    private static final String UPDATED_UMIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_LUMINOSIDADE = "AAAAAAAAAA";
    private static final String UPDATED_LUMINOSIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_EMBALAGEM_SECUNDARIA = "AAAAAAAAAA";
    private static final String UPDATED_EMBALAGEM_SECUNDARIA = "BBBBBBBBBB";

    private static final String DEFAULT_FORMA_FISICA = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_FISICA = "BBBBBBBBBB";

    private static final String DEFAULT_FINALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_FINALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_PRODUTIVO_RC = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PRODUTIVO_RC = "BBBBBBBBBB";

    private static final String DEFAULT_EMBALAGEM_PRIMARIA = "AAAAAAAAAA";
    private static final String UPDATED_EMBALAGEM_PRIMARIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_ANVISA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_ANVISA = "BBBBBBBBBB";

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;
    private static final Double SMALLER_VOLUME = 1D - 1D;

    private static final String DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO = "AAAAAAAAAA";
    private static final String UPDATED_COD_UNIDADE_MEDIDA_DIMENSAO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_COD_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ATIVO = "AAAAAAAAAA";
    private static final String UPDATED_ATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ADUANA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ADUANA = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSE_RISCO = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE_RISCO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_RISCO = "AAAAAAAAAA";
    private static final String UPDATED_COD_RISCO = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_12 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_12 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_13 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_13 = "BBBBBBBBBB";

    private static final Double DEFAULT_FLEX_FIELD_1_NUMBER = 1D;
    private static final Double UPDATED_FLEX_FIELD_1_NUMBER = 2D;
    private static final Double SMALLER_FLEX_FIELD_1_NUMBER = 1D - 1D;

    private static final Double DEFAULT_FLEX_FIELD_2_NUMBER = 1D;
    private static final Double UPDATED_FLEX_FIELD_2_NUMBER = 2D;
    private static final Double SMALLER_FLEX_FIELD_2_NUMBER = 1D - 1D;

    private static final Double DEFAULT_FLEX_FIELD_3_NUMBER = 1D;
    private static final Double UPDATED_FLEX_FIELD_3_NUMBER = 2D;
    private static final Double SMALLER_FLEX_FIELD_3_NUMBER = 1D - 1D;

    private static final Double DEFAULT_FLEX_FIELD_4_NUMBER = 1D;
    private static final Double UPDATED_FLEX_FIELD_4_NUMBER = 2D;
    private static final Double SMALLER_FLEX_FIELD_4_NUMBER = 1D - 1D;

    private static final Double DEFAULT_FLEX_FIELD_5_NUMBER = 1D;
    private static final Double UPDATED_FLEX_FIELD_5_NUMBER = 2D;
    private static final Double SMALLER_FLEX_FIELD_5_NUMBER = 1D - 1D;

    private static final String DEFAULT_STATUS_SCANSYS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_SCANSYS = "BBBBBBBBBB";

    private static final Double DEFAULT_COD_ESTRUTURA_ATUAL = 1D;
    private static final Double UPDATED_COD_ESTRUTURA_ATUAL = 2D;
    private static final Double SMALLER_COD_ESTRUTURA_ATUAL = 1D - 1D;

    private static final Integer DEFAULT_PERC_TOLERANCIA = 1;
    private static final Integer UPDATED_PERC_TOLERANCIA = 2;
    private static final Integer SMALLER_PERC_TOLERANCIA = 1 - 1;

    private static final String DEFAULT_PIS_ESPECIFICO = "AAAAAAAAAA";
    private static final String UPDATED_PIS_ESPECIFICO = "BBBBBBBBBB";

    private static final String DEFAULT_COFINS_ESPECIFICO = "AAAAAAAAAA";
    private static final String UPDATED_COFINS_ESPECIFICO = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_14 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_14 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_15 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_15 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_16 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_16 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_17 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_17 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_18 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_18 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_19 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_19 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_20 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_20 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_21 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_21 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_22 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_22 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_23 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_23 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_24 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_24 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_25 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_25 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_26 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_26 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_27 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_27 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_28 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_28 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_29 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_29 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_30 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_30 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_31 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_31 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_32 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_32 = "BBBBBBBBBB";

    private static final String DEFAULT_FLEX_FIELD_33 = "AAAAAAAAAA";
    private static final String UPDATED_FLEX_FIELD_33 = "BBBBBBBBBB";

    private static final String DEFAULT_S_COD_BARRAS_GTIN = "AAAAAAAAAA";
    private static final String UPDATED_S_COD_BARRAS_GTIN = "BBBBBBBBBB";

    private static final Double DEFAULT_N_VLR_UNIT_LIMITE_USD = 1D;
    private static final Double UPDATED_N_VLR_UNIT_LIMITE_USD = 2D;
    private static final Double SMALLER_N_VLR_UNIT_LIMITE_USD = 1D - 1D;

    private static final Double DEFAULT_N_COD_PROD_ANP = 1D;
    private static final Double UPDATED_N_COD_PROD_ANP = 2D;
    private static final Double SMALLER_N_COD_PROD_ANP = 1D - 1D;

    private static final Double DEFAULT_N_CUSTO_PRODUCAO = 1D;
    private static final Double UPDATED_N_CUSTO_PRODUCAO = 2D;
    private static final Double SMALLER_N_CUSTO_PRODUCAO = 1D - 1D;

    private static final String DEFAULT_S_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_S_DESTINO = "BBBBBBBBBB";

    private static final Double DEFAULT_N_PERCENTUAL_GLP = 1D;
    private static final Double UPDATED_N_PERCENTUAL_GLP = 2D;
    private static final Double SMALLER_N_PERCENTUAL_GLP = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_1 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_1 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_1 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_2 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_2 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_2 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_3 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_3 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_3 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_4 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_4 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_4 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_5 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_5 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_5 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_6 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_6 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_6 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_7 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_7 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_7 = 1D - 1D;

    private static final Double DEFAULT_N_LOC_FIELD_8 = 1D;
    private static final Double UPDATED_N_LOC_FIELD_8 = 2D;
    private static final Double SMALLER_N_LOC_FIELD_8 = 1D - 1D;

    private static final String DEFAULT_S_LOC_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_5 = "BBBBBBBBBB";

    private static final Double DEFAULT_N_ID_DOC_OCR = 1D;
    private static final Double UPDATED_N_ID_DOC_OCR = 2D;
    private static final Double SMALLER_N_ID_DOC_OCR = 1D - 1D;

    private static final String DEFAULT_S_LOC_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_6 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_7 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_7 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_8 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_8 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_9 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_9 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_10 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_10 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_11 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_11 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_12 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_12 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_13 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_13 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_14 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_14 = "BBBBBBBBBB";

    private static final String DEFAULT_S_LOC_FIELD_15 = "AAAAAAAAAA";
    private static final String UPDATED_S_LOC_FIELD_15 = "BBBBBBBBBB";

    private static final String DEFAULT_S_COD_PROD_ANVISA = "AAAAAAAAAA";
    private static final String UPDATED_S_COD_PROD_ANVISA = "BBBBBBBBBB";

    private static final String DEFAULT_S_DESC_PROD_ANP = "AAAAAAAAAA";
    private static final String UPDATED_S_DESC_PROD_ANP = "BBBBBBBBBB";

    private static final Double DEFAULT_N_PERC_GLP_NAC = 1D;
    private static final Double UPDATED_N_PERC_GLP_NAC = 2D;
    private static final Double SMALLER_N_PERC_GLP_NAC = 1D - 1D;

    private static final Double DEFAULT_N_PERC_GLP_IMP = 1D;
    private static final Double UPDATED_N_PERC_GLP_IMP = 2D;
    private static final Double SMALLER_N_PERC_GLP_IMP = 1D - 1D;

    private static final Double DEFAULT_N_VALOR_PARTIDA = 1D;
    private static final Double UPDATED_N_VALOR_PARTIDA = 2D;
    private static final Double SMALLER_N_VALOR_PARTIDA = 1D - 1D;

    private static final String DEFAULT_S_GTIN_UNID_TRIB = "AAAAAAAAAA";
    private static final String UPDATED_S_GTIN_UNID_TRIB = "BBBBBBBBBB";

    private static final String DEFAULT_S_CODIGO_MODALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_S_CODIGO_MODALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_S_CODIGOGPC = "AAAAAAAAAA";
    private static final String UPDATED_S_CODIGOGPC = "BBBBBBBBBB";

    private static final String DEFAULT_S_CODIGOGPCBRICK = "AAAAAAAAAA";
    private static final String UPDATED_S_CODIGOGPCBRICK = "BBBBBBBBBB";

    private static final String DEFAULT_S_CODIGOUNSPSC = "AAAAAAAAAA";
    private static final String UPDATED_S_CODIGOUNSPSC = "BBBBBBBBBB";

    private static final String DEFAULT_S_SITUACAO = "AAAAAAAAAA";
    private static final String UPDATED_S_SITUACAO = "BBBBBBBBBB";

    private static final String DEFAULT_S_ENVIADO = "AAAAAAAAAA";
    private static final String UPDATED_S_ENVIADO = "BBBBBBBBBB";

    private static final String DEFAULT_S_MOTIVO_ISENCAO_ANVISA = "AAAAAAAAAA";
    private static final String UPDATED_S_MOTIVO_ISENCAO_ANVISA = "BBBBBBBBBB";

    private static final String DEFAULT_S_IC_PRONTO_PARA_ENVIO = "AAAAAAAAAA";
    private static final String UPDATED_S_IC_PRONTO_PARA_ENVIO = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    /**
     * This repository is mocked in the com.tcs.product.repository.search test package.
     *
     * @see com.tcs.product.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductMockMvc;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productRepository, mockProductSearchRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .idProduto(DEFAULT_ID_PRODUTO)
            .codRecipiente(DEFAULT_COD_RECIPIENTE)
            .partNumber(DEFAULT_PART_NUMBER)
            .tpEmbalagem(DEFAULT_TP_EMBALAGEM)
            .cnpj(DEFAULT_CNPJ)
            .idDispositivo(DEFAULT_ID_DISPOSITIVO)
            .unidadeEstoque(DEFAULT_UNIDADE_ESTOQUE)
            .naladincca(DEFAULT_NALADINCCA)
            .ncm(DEFAULT_NCM)
            .naladish(DEFAULT_NALADISH)
            .linhaProduto(DEFAULT_LINHA_PRODUTO)
            .pesoBruto(DEFAULT_PESO_BRUTO)
            .pesoLiquido(DEFAULT_PESO_LIQUIDO)
            .registroMs(DEFAULT_REGISTRO_MS)
            .validade(DEFAULT_VALIDADE)
            .necessitaLi(DEFAULT_NECESSITA_LI)
            .recof(DEFAULT_RECOF)
            .reducaoIcms(DEFAULT_REDUCAO_ICMS)
            .codOnu(DEFAULT_COD_ONU)
            .seqSuframa(DEFAULT_SEQ_SUFRAMA)
            .naoTributavel(DEFAULT_NAO_TRIBUTAVEL)
            .ipiEspecifico(DEFAULT_IPI_ESPECIFICO)
            .iiEspecifico(DEFAULT_II_ESPECIFICO)
            .ii(DEFAULT_II)
            .ipi(DEFAULT_IPI)
            .valorUnitaria(DEFAULT_VALOR_UNITARIA)
            .capacidadeUnitaria(DEFAULT_CAPACIDADE_UNITARIA)
            .fatorConversao(DEFAULT_FATOR_CONVERSAO)
            .descricaoResumida(DEFAULT_DESCRICAO_RESUMIDA)
            .atualizacao(DEFAULT_ATUALIZACAO)
            .status(DEFAULT_STATUS)
            .unidadePeso(DEFAULT_UNIDADE_PESO)
            .codUnidadeQtde(DEFAULT_COD_UNIDADE_QTDE)
            .codUnidadeComercializada(DEFAULT_COD_UNIDADE_COMERCIALIZADA)
            .codUnidadeUnitaria(DEFAULT_COD_UNIDADE_UNITARIA)
            .pesoQuilo(DEFAULT_PESO_QUILO)
            .pesoUnidComercializada(DEFAULT_PESO_UNID_COMERCIALIZADA)
            .codExternoGip(DEFAULT_COD_EXTERNO_GIP)
            .ultimoInformante(DEFAULT_ULTIMO_INFORMANTE)
            .tsp(DEFAULT_TSP)
            .tipoRecof(DEFAULT_TIPO_RECOF)
            .obs(DEFAULT_OBS)
            .pesoRateavel(DEFAULT_PESO_RATEAVEL)
            .necessitaRevisao(DEFAULT_NECESSITA_REVISAO)
            .tipoProduto(DEFAULT_TIPO_PRODUTO)
            .procedencia(DEFAULT_PROCEDENCIA)
            .chassi(DEFAULT_CHASSI)
            .especificacaoTecnica(DEFAULT_ESPECIFICACAO_TECNICA)
            .materiaPrimaBasica(DEFAULT_MATERIA_PRIMA_BASICA)
            .automatico(DEFAULT_AUTOMATICO)
            .codOrigem(DEFAULT_COD_ORIGEM)
            .materialGenerico(DEFAULT_MATERIAL_GENERICO)
            .cargaPerigosa(DEFAULT_CARGA_PERIGOSA)
            .codUnidadeVenda(DEFAULT_COD_UNIDADE_VENDA)
            .flexField1(DEFAULT_FLEX_FIELD_1)
            .flexField2(DEFAULT_FLEX_FIELD_2)
            .flexField3(DEFAULT_FLEX_FIELD_3)
            .descricaoDetalhada(DEFAULT_DESCRICAO_DETALHADA)
            .idOrganizacao(DEFAULT_ID_ORGANIZACAO)
            .codPaisOrigem(DEFAULT_COD_PAIS_ORIGEM)
            .cicloProdutivo(DEFAULT_CICLO_PRODUTIVO)
            .partNumberFornecedor(DEFAULT_PART_NUMBER_FORNECEDOR)
            .flagAtualizaIcms(DEFAULT_FLAG_ATUALIZA_ICMS)
            .idDispositivoIpi(DEFAULT_ID_DISPOSITIVO_IPI)
            .codigoMoeda(DEFAULT_CODIGO_MOEDA)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .codProd(DEFAULT_COD_PROD)
            .codProducao(DEFAULT_COD_PRODUCAO)
            .procedenciaExp(DEFAULT_PROCEDENCIA_EXP)
            .idAnuencia(DEFAULT_ID_ANUENCIA)
            .pesoMetroCubico(DEFAULT_PESO_METRO_CUBICO)
            .hts(DEFAULT_HTS)
            .nomeComercial(DEFAULT_NOME_COMERCIAL)
            .idModelo(DEFAULT_ID_MODELO)
            .unidadeFracionada(DEFAULT_UNIDADE_FRACIONADA)
            .difPesoEmb(DEFAULT_DIF_PESO_EMB)
            .classProdRecof(DEFAULT_CLASS_PROD_RECOF)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .dataInsertMov(DEFAULT_DATA_INSERT_MOV)
            .idCorporativo(DEFAULT_ID_CORPORATIVO)
            .dataGerLeg(DEFAULT_DATA_GER_LEG)
            .procedenciaInfo(DEFAULT_PROCEDENCIA_INFO)
            .codProdSuframa(DEFAULT_COD_PROD_SUFRAMA)
            .pxExpTipoins(DEFAULT_PX_EXP_TIPOINS)
            .tipoProdSuframa(DEFAULT_TIPO_PROD_SUFRAMA)
            .idDetalheSuframa(DEFAULT_ID_DETALHE_SUFRAMA)
            .valorUnitarioReal(DEFAULT_VALOR_UNITARIO_REAL)
            .necessitaRevisaoPexpam(DEFAULT_NECESSITA_REVISAO_PEXPAM)
            .modelo(DEFAULT_MODELO)
            .pxModeloPadrao(DEFAULT_PX_MODELO_PADRAO)
            .flexField4(DEFAULT_FLEX_FIELD_4)
            .flexField5(DEFAULT_FLEX_FIELD_5)
            .flexField6(DEFAULT_FLEX_FIELD_6)
            .flexField7(DEFAULT_FLEX_FIELD_7)
            .flexField8(DEFAULT_FLEX_FIELD_8)
            .flexField9(DEFAULT_FLEX_FIELD_9)
            .flexField10(DEFAULT_FLEX_FIELD_10)
            .flexField11(DEFAULT_FLEX_FIELD_11)
            .pisCofinsTipoAplic(DEFAULT_PIS_COFINS_TIPO_APLIC)
            .pis(DEFAULT_PIS)
            .cofins(DEFAULT_COFINS)
            .pisCofinsRedBase(DEFAULT_PIS_COFINS_RED_BASE)
            .modeloProdSuframa(DEFAULT_MODELO_PROD_SUFRAMA)
            .codSiscomexUnidadeNcm(DEFAULT_COD_SISCOMEX_UNIDADE_NCM)
            .partNumberCliente(DEFAULT_PART_NUMBER_CLIENTE)
            .superficieUnitaria(DEFAULT_SUPERFICIE_UNITARIA)
            .localEstoque(DEFAULT_LOCAL_ESTOQUE)
            .codUnidadeSuperficie(DEFAULT_COD_UNIDADE_SUPERFICIE)
            .rateioProdutoAcabado(DEFAULT_RATEIO_PRODUTO_ACABADO)
            .pisCofinsCodUnEspec(DEFAULT_PIS_COFINS_COD_UN_ESPEC)
            .recuperaImpostos(DEFAULT_RECUPERA_IMPOSTOS)
            .flagNoRaf(DEFAULT_FLAG_NO_RAF)
            .notaComplTipi(DEFAULT_NOTA_COMPL_TIPI)
            .ipiReduzido(DEFAULT_IPI_REDUZIDO)
            .sujeitoLote(DEFAULT_SUJEITO_LOTE)
            .marcaComercial(DEFAULT_MARCA_COMERCIAL)
            .tipoEmbalagem(DEFAULT_TIPO_EMBALAGEM)
            .numLiberacaoBrasilia(DEFAULT_NUM_LIBERACAO_BRASILIA)
            .temperaturaConservacao(DEFAULT_TEMPERATURA_CONSERVACAO)
            .umidade(DEFAULT_UMIDADE)
            .luminosidade(DEFAULT_LUMINOSIDADE)
            .embalagemSecundaria(DEFAULT_EMBALAGEM_SECUNDARIA)
            .formaFisica(DEFAULT_FORMA_FISICA)
            .finalidade(DEFAULT_FINALIDADE)
            .itemProdutivoRc(DEFAULT_ITEM_PRODUTIVO_RC)
            .embalagemPrimaria(DEFAULT_EMBALAGEM_PRIMARIA)
            .descricaoAnvisa(DEFAULT_DESCRICAO_ANVISA)
            .volume(DEFAULT_VOLUME)
            .codUnidadeMedidaDimensao(DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO)
            .codMaterial(DEFAULT_COD_MATERIAL)
            .ativo(DEFAULT_ATIVO)
            .codigoAduana(DEFAULT_CODIGO_ADUANA)
            .classeRisco(DEFAULT_CLASSE_RISCO)
            .codRisco(DEFAULT_COD_RISCO)
            .flexField12(DEFAULT_FLEX_FIELD_12)
            .flexField13(DEFAULT_FLEX_FIELD_13)
            .flexField1Number(DEFAULT_FLEX_FIELD_1_NUMBER)
            .flexField2Number(DEFAULT_FLEX_FIELD_2_NUMBER)
            .flexField3Number(DEFAULT_FLEX_FIELD_3_NUMBER)
            .flexField4Number(DEFAULT_FLEX_FIELD_4_NUMBER)
            .flexField5Number(DEFAULT_FLEX_FIELD_5_NUMBER)
            .statusScansys(DEFAULT_STATUS_SCANSYS)
            .codEstruturaAtual(DEFAULT_COD_ESTRUTURA_ATUAL)
            .percTolerancia(DEFAULT_PERC_TOLERANCIA)
            .pisEspecifico(DEFAULT_PIS_ESPECIFICO)
            .cofinsEspecifico(DEFAULT_COFINS_ESPECIFICO)
            .flexField14(DEFAULT_FLEX_FIELD_14)
            .flexField15(DEFAULT_FLEX_FIELD_15)
            .flexField16(DEFAULT_FLEX_FIELD_16)
            .flexField17(DEFAULT_FLEX_FIELD_17)
            .flexField18(DEFAULT_FLEX_FIELD_18)
            .flexField19(DEFAULT_FLEX_FIELD_19)
            .flexField20(DEFAULT_FLEX_FIELD_20)
            .flexField21(DEFAULT_FLEX_FIELD_21)
            .flexField22(DEFAULT_FLEX_FIELD_22)
            .flexField23(DEFAULT_FLEX_FIELD_23)
            .flexField24(DEFAULT_FLEX_FIELD_24)
            .flexField25(DEFAULT_FLEX_FIELD_25)
            .flexField26(DEFAULT_FLEX_FIELD_26)
            .flexField27(DEFAULT_FLEX_FIELD_27)
            .flexField28(DEFAULT_FLEX_FIELD_28)
            .flexField29(DEFAULT_FLEX_FIELD_29)
            .flexField30(DEFAULT_FLEX_FIELD_30)
            .flexField31(DEFAULT_FLEX_FIELD_31)
            .flexField32(DEFAULT_FLEX_FIELD_32)
            .flexField33(DEFAULT_FLEX_FIELD_33)
            .sCodBarrasGtin(DEFAULT_S_COD_BARRAS_GTIN)
            .nVlrUnitLimiteUsd(DEFAULT_N_VLR_UNIT_LIMITE_USD)
            .nCodProdAnp(DEFAULT_N_COD_PROD_ANP)
            .nCustoProducao(DEFAULT_N_CUSTO_PRODUCAO)
            .sDestino(DEFAULT_S_DESTINO)
            .nPercentualGlp(DEFAULT_N_PERCENTUAL_GLP)
            .nLocField1(DEFAULT_N_LOC_FIELD_1)
            .nLocField2(DEFAULT_N_LOC_FIELD_2)
            .nLocField3(DEFAULT_N_LOC_FIELD_3)
            .nLocField4(DEFAULT_N_LOC_FIELD_4)
            .nLocField5(DEFAULT_N_LOC_FIELD_5)
            .nLocField6(DEFAULT_N_LOC_FIELD_6)
            .nLocField7(DEFAULT_N_LOC_FIELD_7)
            .nLocField8(DEFAULT_N_LOC_FIELD_8)
            .sLocField1(DEFAULT_S_LOC_FIELD_1)
            .sLocField2(DEFAULT_S_LOC_FIELD_2)
            .sLocField3(DEFAULT_S_LOC_FIELD_3)
            .sLocField4(DEFAULT_S_LOC_FIELD_4)
            .sLocField5(DEFAULT_S_LOC_FIELD_5)
            .nIdDocOcr(DEFAULT_N_ID_DOC_OCR)
            .sLocField6(DEFAULT_S_LOC_FIELD_6)
            .sLocField7(DEFAULT_S_LOC_FIELD_7)
            .sLocField8(DEFAULT_S_LOC_FIELD_8)
            .sLocField9(DEFAULT_S_LOC_FIELD_9)
            .sLocField10(DEFAULT_S_LOC_FIELD_10)
            .sLocField11(DEFAULT_S_LOC_FIELD_11)
            .sLocField12(DEFAULT_S_LOC_FIELD_12)
            .sLocField13(DEFAULT_S_LOC_FIELD_13)
            .sLocField14(DEFAULT_S_LOC_FIELD_14)
            .sLocField15(DEFAULT_S_LOC_FIELD_15)
            .sCodProdAnvisa(DEFAULT_S_COD_PROD_ANVISA)
            .sDescProdAnp(DEFAULT_S_DESC_PROD_ANP)
            .nPercGlpNac(DEFAULT_N_PERC_GLP_NAC)
            .nPercGlpImp(DEFAULT_N_PERC_GLP_IMP)
            .nValorPartida(DEFAULT_N_VALOR_PARTIDA)
            .sGtinUnidTrib(DEFAULT_S_GTIN_UNID_TRIB)
            .sCodigoModalidade(DEFAULT_S_CODIGO_MODALIDADE)
            .sCodigogpc(DEFAULT_S_CODIGOGPC)
            .sCodigogpcbrick(DEFAULT_S_CODIGOGPCBRICK)
            .sCodigounspsc(DEFAULT_S_CODIGOUNSPSC)
            .sSituacao(DEFAULT_S_SITUACAO)
            .sEnviado(DEFAULT_S_ENVIADO)
            .sMotivoIsencaoAnvisa(DEFAULT_S_MOTIVO_ISENCAO_ANVISA)
            .sIcProntoParaEnvio(DEFAULT_S_IC_PRONTO_PARA_ENVIO);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .idProduto(UPDATED_ID_PRODUTO)
            .codRecipiente(UPDATED_COD_RECIPIENTE)
            .partNumber(UPDATED_PART_NUMBER)
            .tpEmbalagem(UPDATED_TP_EMBALAGEM)
            .cnpj(UPDATED_CNPJ)
            .idDispositivo(UPDATED_ID_DISPOSITIVO)
            .unidadeEstoque(UPDATED_UNIDADE_ESTOQUE)
            .naladincca(UPDATED_NALADINCCA)
            .ncm(UPDATED_NCM)
            .naladish(UPDATED_NALADISH)
            .linhaProduto(UPDATED_LINHA_PRODUTO)
            .pesoBruto(UPDATED_PESO_BRUTO)
            .pesoLiquido(UPDATED_PESO_LIQUIDO)
            .registroMs(UPDATED_REGISTRO_MS)
            .validade(UPDATED_VALIDADE)
            .necessitaLi(UPDATED_NECESSITA_LI)
            .recof(UPDATED_RECOF)
            .reducaoIcms(UPDATED_REDUCAO_ICMS)
            .codOnu(UPDATED_COD_ONU)
            .seqSuframa(UPDATED_SEQ_SUFRAMA)
            .naoTributavel(UPDATED_NAO_TRIBUTAVEL)
            .ipiEspecifico(UPDATED_IPI_ESPECIFICO)
            .iiEspecifico(UPDATED_II_ESPECIFICO)
            .ii(UPDATED_II)
            .ipi(UPDATED_IPI)
            .valorUnitaria(UPDATED_VALOR_UNITARIA)
            .capacidadeUnitaria(UPDATED_CAPACIDADE_UNITARIA)
            .fatorConversao(UPDATED_FATOR_CONVERSAO)
            .descricaoResumida(UPDATED_DESCRICAO_RESUMIDA)
            .atualizacao(UPDATED_ATUALIZACAO)
            .status(UPDATED_STATUS)
            .unidadePeso(UPDATED_UNIDADE_PESO)
            .codUnidadeQtde(UPDATED_COD_UNIDADE_QTDE)
            .codUnidadeComercializada(UPDATED_COD_UNIDADE_COMERCIALIZADA)
            .codUnidadeUnitaria(UPDATED_COD_UNIDADE_UNITARIA)
            .pesoQuilo(UPDATED_PESO_QUILO)
            .pesoUnidComercializada(UPDATED_PESO_UNID_COMERCIALIZADA)
            .codExternoGip(UPDATED_COD_EXTERNO_GIP)
            .ultimoInformante(UPDATED_ULTIMO_INFORMANTE)
            .tsp(UPDATED_TSP)
            .tipoRecof(UPDATED_TIPO_RECOF)
            .obs(UPDATED_OBS)
            .pesoRateavel(UPDATED_PESO_RATEAVEL)
            .necessitaRevisao(UPDATED_NECESSITA_REVISAO)
            .tipoProduto(UPDATED_TIPO_PRODUTO)
            .procedencia(UPDATED_PROCEDENCIA)
            .chassi(UPDATED_CHASSI)
            .especificacaoTecnica(UPDATED_ESPECIFICACAO_TECNICA)
            .materiaPrimaBasica(UPDATED_MATERIA_PRIMA_BASICA)
            .automatico(UPDATED_AUTOMATICO)
            .codOrigem(UPDATED_COD_ORIGEM)
            .materialGenerico(UPDATED_MATERIAL_GENERICO)
            .cargaPerigosa(UPDATED_CARGA_PERIGOSA)
            .codUnidadeVenda(UPDATED_COD_UNIDADE_VENDA)
            .flexField1(UPDATED_FLEX_FIELD_1)
            .flexField2(UPDATED_FLEX_FIELD_2)
            .flexField3(UPDATED_FLEX_FIELD_3)
            .descricaoDetalhada(UPDATED_DESCRICAO_DETALHADA)
            .idOrganizacao(UPDATED_ID_ORGANIZACAO)
            .codPaisOrigem(UPDATED_COD_PAIS_ORIGEM)
            .cicloProdutivo(UPDATED_CICLO_PRODUTIVO)
            .partNumberFornecedor(UPDATED_PART_NUMBER_FORNECEDOR)
            .flagAtualizaIcms(UPDATED_FLAG_ATUALIZA_ICMS)
            .idDispositivoIpi(UPDATED_ID_DISPOSITIVO_IPI)
            .codigoMoeda(UPDATED_CODIGO_MOEDA)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .codProd(UPDATED_COD_PROD)
            .codProducao(UPDATED_COD_PRODUCAO)
            .procedenciaExp(UPDATED_PROCEDENCIA_EXP)
            .idAnuencia(UPDATED_ID_ANUENCIA)
            .pesoMetroCubico(UPDATED_PESO_METRO_CUBICO)
            .hts(UPDATED_HTS)
            .nomeComercial(UPDATED_NOME_COMERCIAL)
            .idModelo(UPDATED_ID_MODELO)
            .unidadeFracionada(UPDATED_UNIDADE_FRACIONADA)
            .difPesoEmb(UPDATED_DIF_PESO_EMB)
            .classProdRecof(UPDATED_CLASS_PROD_RECOF)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .dataInsertMov(UPDATED_DATA_INSERT_MOV)
            .idCorporativo(UPDATED_ID_CORPORATIVO)
            .dataGerLeg(UPDATED_DATA_GER_LEG)
            .procedenciaInfo(UPDATED_PROCEDENCIA_INFO)
            .codProdSuframa(UPDATED_COD_PROD_SUFRAMA)
            .pxExpTipoins(UPDATED_PX_EXP_TIPOINS)
            .tipoProdSuframa(UPDATED_TIPO_PROD_SUFRAMA)
            .idDetalheSuframa(UPDATED_ID_DETALHE_SUFRAMA)
            .valorUnitarioReal(UPDATED_VALOR_UNITARIO_REAL)
            .necessitaRevisaoPexpam(UPDATED_NECESSITA_REVISAO_PEXPAM)
            .modelo(UPDATED_MODELO)
            .pxModeloPadrao(UPDATED_PX_MODELO_PADRAO)
            .flexField4(UPDATED_FLEX_FIELD_4)
            .flexField5(UPDATED_FLEX_FIELD_5)
            .flexField6(UPDATED_FLEX_FIELD_6)
            .flexField7(UPDATED_FLEX_FIELD_7)
            .flexField8(UPDATED_FLEX_FIELD_8)
            .flexField9(UPDATED_FLEX_FIELD_9)
            .flexField10(UPDATED_FLEX_FIELD_10)
            .flexField11(UPDATED_FLEX_FIELD_11)
            .pisCofinsTipoAplic(UPDATED_PIS_COFINS_TIPO_APLIC)
            .pis(UPDATED_PIS)
            .cofins(UPDATED_COFINS)
            .pisCofinsRedBase(UPDATED_PIS_COFINS_RED_BASE)
            .modeloProdSuframa(UPDATED_MODELO_PROD_SUFRAMA)
            .codSiscomexUnidadeNcm(UPDATED_COD_SISCOMEX_UNIDADE_NCM)
            .partNumberCliente(UPDATED_PART_NUMBER_CLIENTE)
            .superficieUnitaria(UPDATED_SUPERFICIE_UNITARIA)
            .localEstoque(UPDATED_LOCAL_ESTOQUE)
            .codUnidadeSuperficie(UPDATED_COD_UNIDADE_SUPERFICIE)
            .rateioProdutoAcabado(UPDATED_RATEIO_PRODUTO_ACABADO)
            .pisCofinsCodUnEspec(UPDATED_PIS_COFINS_COD_UN_ESPEC)
            .recuperaImpostos(UPDATED_RECUPERA_IMPOSTOS)
            .flagNoRaf(UPDATED_FLAG_NO_RAF)
            .notaComplTipi(UPDATED_NOTA_COMPL_TIPI)
            .ipiReduzido(UPDATED_IPI_REDUZIDO)
            .sujeitoLote(UPDATED_SUJEITO_LOTE)
            .marcaComercial(UPDATED_MARCA_COMERCIAL)
            .tipoEmbalagem(UPDATED_TIPO_EMBALAGEM)
            .numLiberacaoBrasilia(UPDATED_NUM_LIBERACAO_BRASILIA)
            .temperaturaConservacao(UPDATED_TEMPERATURA_CONSERVACAO)
            .umidade(UPDATED_UMIDADE)
            .luminosidade(UPDATED_LUMINOSIDADE)
            .embalagemSecundaria(UPDATED_EMBALAGEM_SECUNDARIA)
            .formaFisica(UPDATED_FORMA_FISICA)
            .finalidade(UPDATED_FINALIDADE)
            .itemProdutivoRc(UPDATED_ITEM_PRODUTIVO_RC)
            .embalagemPrimaria(UPDATED_EMBALAGEM_PRIMARIA)
            .descricaoAnvisa(UPDATED_DESCRICAO_ANVISA)
            .volume(UPDATED_VOLUME)
            .codUnidadeMedidaDimensao(UPDATED_COD_UNIDADE_MEDIDA_DIMENSAO)
            .codMaterial(UPDATED_COD_MATERIAL)
            .ativo(UPDATED_ATIVO)
            .codigoAduana(UPDATED_CODIGO_ADUANA)
            .classeRisco(UPDATED_CLASSE_RISCO)
            .codRisco(UPDATED_COD_RISCO)
            .flexField12(UPDATED_FLEX_FIELD_12)
            .flexField13(UPDATED_FLEX_FIELD_13)
            .flexField1Number(UPDATED_FLEX_FIELD_1_NUMBER)
            .flexField2Number(UPDATED_FLEX_FIELD_2_NUMBER)
            .flexField3Number(UPDATED_FLEX_FIELD_3_NUMBER)
            .flexField4Number(UPDATED_FLEX_FIELD_4_NUMBER)
            .flexField5Number(UPDATED_FLEX_FIELD_5_NUMBER)
            .statusScansys(UPDATED_STATUS_SCANSYS)
            .codEstruturaAtual(UPDATED_COD_ESTRUTURA_ATUAL)
            .percTolerancia(UPDATED_PERC_TOLERANCIA)
            .pisEspecifico(UPDATED_PIS_ESPECIFICO)
            .cofinsEspecifico(UPDATED_COFINS_ESPECIFICO)
            .flexField14(UPDATED_FLEX_FIELD_14)
            .flexField15(UPDATED_FLEX_FIELD_15)
            .flexField16(UPDATED_FLEX_FIELD_16)
            .flexField17(UPDATED_FLEX_FIELD_17)
            .flexField18(UPDATED_FLEX_FIELD_18)
            .flexField19(UPDATED_FLEX_FIELD_19)
            .flexField20(UPDATED_FLEX_FIELD_20)
            .flexField21(UPDATED_FLEX_FIELD_21)
            .flexField22(UPDATED_FLEX_FIELD_22)
            .flexField23(UPDATED_FLEX_FIELD_23)
            .flexField24(UPDATED_FLEX_FIELD_24)
            .flexField25(UPDATED_FLEX_FIELD_25)
            .flexField26(UPDATED_FLEX_FIELD_26)
            .flexField27(UPDATED_FLEX_FIELD_27)
            .flexField28(UPDATED_FLEX_FIELD_28)
            .flexField29(UPDATED_FLEX_FIELD_29)
            .flexField30(UPDATED_FLEX_FIELD_30)
            .flexField31(UPDATED_FLEX_FIELD_31)
            .flexField32(UPDATED_FLEX_FIELD_32)
            .flexField33(UPDATED_FLEX_FIELD_33)
            .sCodBarrasGtin(UPDATED_S_COD_BARRAS_GTIN)
            .nVlrUnitLimiteUsd(UPDATED_N_VLR_UNIT_LIMITE_USD)
            .nCodProdAnp(UPDATED_N_COD_PROD_ANP)
            .nCustoProducao(UPDATED_N_CUSTO_PRODUCAO)
            .sDestino(UPDATED_S_DESTINO)
            .nPercentualGlp(UPDATED_N_PERCENTUAL_GLP)
            .nLocField1(UPDATED_N_LOC_FIELD_1)
            .nLocField2(UPDATED_N_LOC_FIELD_2)
            .nLocField3(UPDATED_N_LOC_FIELD_3)
            .nLocField4(UPDATED_N_LOC_FIELD_4)
            .nLocField5(UPDATED_N_LOC_FIELD_5)
            .nLocField6(UPDATED_N_LOC_FIELD_6)
            .nLocField7(UPDATED_N_LOC_FIELD_7)
            .nLocField8(UPDATED_N_LOC_FIELD_8)
            .sLocField1(UPDATED_S_LOC_FIELD_1)
            .sLocField2(UPDATED_S_LOC_FIELD_2)
            .sLocField3(UPDATED_S_LOC_FIELD_3)
            .sLocField4(UPDATED_S_LOC_FIELD_4)
            .sLocField5(UPDATED_S_LOC_FIELD_5)
            .nIdDocOcr(UPDATED_N_ID_DOC_OCR)
            .sLocField6(UPDATED_S_LOC_FIELD_6)
            .sLocField7(UPDATED_S_LOC_FIELD_7)
            .sLocField8(UPDATED_S_LOC_FIELD_8)
            .sLocField9(UPDATED_S_LOC_FIELD_9)
            .sLocField10(UPDATED_S_LOC_FIELD_10)
            .sLocField11(UPDATED_S_LOC_FIELD_11)
            .sLocField12(UPDATED_S_LOC_FIELD_12)
            .sLocField13(UPDATED_S_LOC_FIELD_13)
            .sLocField14(UPDATED_S_LOC_FIELD_14)
            .sLocField15(UPDATED_S_LOC_FIELD_15)
            .sCodProdAnvisa(UPDATED_S_COD_PROD_ANVISA)
            .sDescProdAnp(UPDATED_S_DESC_PROD_ANP)
            .nPercGlpNac(UPDATED_N_PERC_GLP_NAC)
            .nPercGlpImp(UPDATED_N_PERC_GLP_IMP)
            .nValorPartida(UPDATED_N_VALOR_PARTIDA)
            .sGtinUnidTrib(UPDATED_S_GTIN_UNID_TRIB)
            .sCodigoModalidade(UPDATED_S_CODIGO_MODALIDADE)
            .sCodigogpc(UPDATED_S_CODIGOGPC)
            .sCodigogpcbrick(UPDATED_S_CODIGOGPCBRICK)
            .sCodigounspsc(UPDATED_S_CODIGOUNSPSC)
            .sSituacao(UPDATED_S_SITUACAO)
            .sEnviado(UPDATED_S_ENVIADO)
            .sMotivoIsencaoAnvisa(UPDATED_S_MOTIVO_ISENCAO_ANVISA)
            .sIcProntoParaEnvio(UPDATED_S_IC_PRONTO_PARA_ENVIO);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getIdProduto()).isEqualTo(DEFAULT_ID_PRODUTO);
        assertThat(testProduct.getCodRecipiente()).isEqualTo(DEFAULT_COD_RECIPIENTE);
        assertThat(testProduct.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);
        assertThat(testProduct.getTpEmbalagem()).isEqualTo(DEFAULT_TP_EMBALAGEM);
        assertThat(testProduct.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testProduct.getIdDispositivo()).isEqualTo(DEFAULT_ID_DISPOSITIVO);
        assertThat(testProduct.getUnidadeEstoque()).isEqualTo(DEFAULT_UNIDADE_ESTOQUE);
        assertThat(testProduct.getNaladincca()).isEqualTo(DEFAULT_NALADINCCA);
        assertThat(testProduct.getNcm()).isEqualTo(DEFAULT_NCM);
        assertThat(testProduct.getNaladish()).isEqualTo(DEFAULT_NALADISH);
        assertThat(testProduct.getLinhaProduto()).isEqualTo(DEFAULT_LINHA_PRODUTO);
        assertThat(testProduct.getPesoBruto()).isEqualTo(DEFAULT_PESO_BRUTO);
        assertThat(testProduct.getPesoLiquido()).isEqualTo(DEFAULT_PESO_LIQUIDO);
        assertThat(testProduct.getRegistroMs()).isEqualTo(DEFAULT_REGISTRO_MS);
        assertThat(testProduct.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testProduct.getNecessitaLi()).isEqualTo(DEFAULT_NECESSITA_LI);
        assertThat(testProduct.getRecof()).isEqualTo(DEFAULT_RECOF);
        assertThat(testProduct.getReducaoIcms()).isEqualTo(DEFAULT_REDUCAO_ICMS);
        assertThat(testProduct.getCodOnu()).isEqualTo(DEFAULT_COD_ONU);
        assertThat(testProduct.getSeqSuframa()).isEqualTo(DEFAULT_SEQ_SUFRAMA);
        assertThat(testProduct.getNaoTributavel()).isEqualTo(DEFAULT_NAO_TRIBUTAVEL);
        assertThat(testProduct.getIpiEspecifico()).isEqualTo(DEFAULT_IPI_ESPECIFICO);
        assertThat(testProduct.getIiEspecifico()).isEqualTo(DEFAULT_II_ESPECIFICO);
        assertThat(testProduct.getIi()).isEqualTo(DEFAULT_II);
        assertThat(testProduct.getIpi()).isEqualTo(DEFAULT_IPI);
        assertThat(testProduct.getValorUnitaria()).isEqualTo(DEFAULT_VALOR_UNITARIA);
        assertThat(testProduct.getCapacidadeUnitaria()).isEqualTo(DEFAULT_CAPACIDADE_UNITARIA);
        assertThat(testProduct.getFatorConversao()).isEqualTo(DEFAULT_FATOR_CONVERSAO);
        assertThat(testProduct.getDescricaoResumida()).isEqualTo(DEFAULT_DESCRICAO_RESUMIDA);
        assertThat(testProduct.getAtualizacao()).isEqualTo(DEFAULT_ATUALIZACAO);
        assertThat(testProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProduct.getUnidadePeso()).isEqualTo(DEFAULT_UNIDADE_PESO);
        assertThat(testProduct.getCodUnidadeQtde()).isEqualTo(DEFAULT_COD_UNIDADE_QTDE);
        assertThat(testProduct.getCodUnidadeComercializada()).isEqualTo(DEFAULT_COD_UNIDADE_COMERCIALIZADA);
        assertThat(testProduct.getCodUnidadeUnitaria()).isEqualTo(DEFAULT_COD_UNIDADE_UNITARIA);
        assertThat(testProduct.getPesoQuilo()).isEqualTo(DEFAULT_PESO_QUILO);
        assertThat(testProduct.getPesoUnidComercializada()).isEqualTo(DEFAULT_PESO_UNID_COMERCIALIZADA);
        assertThat(testProduct.getCodExternoGip()).isEqualTo(DEFAULT_COD_EXTERNO_GIP);
        assertThat(testProduct.getUltimoInformante()).isEqualTo(DEFAULT_ULTIMO_INFORMANTE);
        assertThat(testProduct.getTsp()).isEqualTo(DEFAULT_TSP);
        assertThat(testProduct.getTipoRecof()).isEqualTo(DEFAULT_TIPO_RECOF);
        assertThat(testProduct.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testProduct.getPesoRateavel()).isEqualTo(DEFAULT_PESO_RATEAVEL);
        assertThat(testProduct.getNecessitaRevisao()).isEqualTo(DEFAULT_NECESSITA_REVISAO);
        assertThat(testProduct.getTipoProduto()).isEqualTo(DEFAULT_TIPO_PRODUTO);
        assertThat(testProduct.getProcedencia()).isEqualTo(DEFAULT_PROCEDENCIA);
        assertThat(testProduct.getChassi()).isEqualTo(DEFAULT_CHASSI);
        assertThat(testProduct.getEspecificacaoTecnica()).isEqualTo(DEFAULT_ESPECIFICACAO_TECNICA);
        assertThat(testProduct.getMateriaPrimaBasica()).isEqualTo(DEFAULT_MATERIA_PRIMA_BASICA);
        assertThat(testProduct.getAutomatico()).isEqualTo(DEFAULT_AUTOMATICO);
        assertThat(testProduct.getCodOrigem()).isEqualTo(DEFAULT_COD_ORIGEM);
        assertThat(testProduct.getMaterialGenerico()).isEqualTo(DEFAULT_MATERIAL_GENERICO);
        assertThat(testProduct.getCargaPerigosa()).isEqualTo(DEFAULT_CARGA_PERIGOSA);
        assertThat(testProduct.getCodUnidadeVenda()).isEqualTo(DEFAULT_COD_UNIDADE_VENDA);
        assertThat(testProduct.getFlexField1()).isEqualTo(DEFAULT_FLEX_FIELD_1);
        assertThat(testProduct.getFlexField2()).isEqualTo(DEFAULT_FLEX_FIELD_2);
        assertThat(testProduct.getFlexField3()).isEqualTo(DEFAULT_FLEX_FIELD_3);
        assertThat(testProduct.getDescricaoDetalhada()).isEqualTo(DEFAULT_DESCRICAO_DETALHADA);
        assertThat(testProduct.getIdOrganizacao()).isEqualTo(DEFAULT_ID_ORGANIZACAO);
        assertThat(testProduct.getCodPaisOrigem()).isEqualTo(DEFAULT_COD_PAIS_ORIGEM);
        assertThat(testProduct.getCicloProdutivo()).isEqualTo(DEFAULT_CICLO_PRODUTIVO);
        assertThat(testProduct.getPartNumberFornecedor()).isEqualTo(DEFAULT_PART_NUMBER_FORNECEDOR);
        assertThat(testProduct.getFlagAtualizaIcms()).isEqualTo(DEFAULT_FLAG_ATUALIZA_ICMS);
        assertThat(testProduct.getIdDispositivoIpi()).isEqualTo(DEFAULT_ID_DISPOSITIVO_IPI);
        assertThat(testProduct.getCodigoMoeda()).isEqualTo(DEFAULT_CODIGO_MOEDA);
        assertThat(testProduct.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testProduct.getCodProd()).isEqualTo(DEFAULT_COD_PROD);
        assertThat(testProduct.getCodProducao()).isEqualTo(DEFAULT_COD_PRODUCAO);
        assertThat(testProduct.getProcedenciaExp()).isEqualTo(DEFAULT_PROCEDENCIA_EXP);
        assertThat(testProduct.getIdAnuencia()).isEqualTo(DEFAULT_ID_ANUENCIA);
        assertThat(testProduct.getPesoMetroCubico()).isEqualTo(DEFAULT_PESO_METRO_CUBICO);
        assertThat(testProduct.getHts()).isEqualTo(DEFAULT_HTS);
        assertThat(testProduct.getNomeComercial()).isEqualTo(DEFAULT_NOME_COMERCIAL);
        assertThat(testProduct.getIdModelo()).isEqualTo(DEFAULT_ID_MODELO);
        assertThat(testProduct.getUnidadeFracionada()).isEqualTo(DEFAULT_UNIDADE_FRACIONADA);
        assertThat(testProduct.getDifPesoEmb()).isEqualTo(DEFAULT_DIF_PESO_EMB);
        assertThat(testProduct.getClassProdRecof()).isEqualTo(DEFAULT_CLASS_PROD_RECOF);
        assertThat(testProduct.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testProduct.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testProduct.getDataInsertMov()).isEqualTo(DEFAULT_DATA_INSERT_MOV);
        assertThat(testProduct.getIdCorporativo()).isEqualTo(DEFAULT_ID_CORPORATIVO);
        assertThat(testProduct.getDataGerLeg()).isEqualTo(DEFAULT_DATA_GER_LEG);
        assertThat(testProduct.getProcedenciaInfo()).isEqualTo(DEFAULT_PROCEDENCIA_INFO);
        assertThat(testProduct.getCodProdSuframa()).isEqualTo(DEFAULT_COD_PROD_SUFRAMA);
        assertThat(testProduct.getPxExpTipoins()).isEqualTo(DEFAULT_PX_EXP_TIPOINS);
        assertThat(testProduct.getTipoProdSuframa()).isEqualTo(DEFAULT_TIPO_PROD_SUFRAMA);
        assertThat(testProduct.getIdDetalheSuframa()).isEqualTo(DEFAULT_ID_DETALHE_SUFRAMA);
        assertThat(testProduct.getValorUnitarioReal()).isEqualTo(DEFAULT_VALOR_UNITARIO_REAL);
        assertThat(testProduct.getNecessitaRevisaoPexpam()).isEqualTo(DEFAULT_NECESSITA_REVISAO_PEXPAM);
        assertThat(testProduct.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testProduct.getPxModeloPadrao()).isEqualTo(DEFAULT_PX_MODELO_PADRAO);
        assertThat(testProduct.getFlexField4()).isEqualTo(DEFAULT_FLEX_FIELD_4);
        assertThat(testProduct.getFlexField5()).isEqualTo(DEFAULT_FLEX_FIELD_5);
        assertThat(testProduct.getFlexField6()).isEqualTo(DEFAULT_FLEX_FIELD_6);
        assertThat(testProduct.getFlexField7()).isEqualTo(DEFAULT_FLEX_FIELD_7);
        assertThat(testProduct.getFlexField8()).isEqualTo(DEFAULT_FLEX_FIELD_8);
        assertThat(testProduct.getFlexField9()).isEqualTo(DEFAULT_FLEX_FIELD_9);
        assertThat(testProduct.getFlexField10()).isEqualTo(DEFAULT_FLEX_FIELD_10);
        assertThat(testProduct.getFlexField11()).isEqualTo(DEFAULT_FLEX_FIELD_11);
        assertThat(testProduct.getPisCofinsTipoAplic()).isEqualTo(DEFAULT_PIS_COFINS_TIPO_APLIC);
        assertThat(testProduct.getPis()).isEqualTo(DEFAULT_PIS);
        assertThat(testProduct.getCofins()).isEqualTo(DEFAULT_COFINS);
        assertThat(testProduct.getPisCofinsRedBase()).isEqualTo(DEFAULT_PIS_COFINS_RED_BASE);
        assertThat(testProduct.getModeloProdSuframa()).isEqualTo(DEFAULT_MODELO_PROD_SUFRAMA);
        assertThat(testProduct.getCodSiscomexUnidadeNcm()).isEqualTo(DEFAULT_COD_SISCOMEX_UNIDADE_NCM);
        assertThat(testProduct.getPartNumberCliente()).isEqualTo(DEFAULT_PART_NUMBER_CLIENTE);
        assertThat(testProduct.getSuperficieUnitaria()).isEqualTo(DEFAULT_SUPERFICIE_UNITARIA);
        assertThat(testProduct.getLocalEstoque()).isEqualTo(DEFAULT_LOCAL_ESTOQUE);
        assertThat(testProduct.getCodUnidadeSuperficie()).isEqualTo(DEFAULT_COD_UNIDADE_SUPERFICIE);
        assertThat(testProduct.getRateioProdutoAcabado()).isEqualTo(DEFAULT_RATEIO_PRODUTO_ACABADO);
        assertThat(testProduct.getPisCofinsCodUnEspec()).isEqualTo(DEFAULT_PIS_COFINS_COD_UN_ESPEC);
        assertThat(testProduct.getRecuperaImpostos()).isEqualTo(DEFAULT_RECUPERA_IMPOSTOS);
        assertThat(testProduct.getFlagNoRaf()).isEqualTo(DEFAULT_FLAG_NO_RAF);
        assertThat(testProduct.getNotaComplTipi()).isEqualTo(DEFAULT_NOTA_COMPL_TIPI);
        assertThat(testProduct.getIpiReduzido()).isEqualTo(DEFAULT_IPI_REDUZIDO);
        assertThat(testProduct.getSujeitoLote()).isEqualTo(DEFAULT_SUJEITO_LOTE);
        assertThat(testProduct.getMarcaComercial()).isEqualTo(DEFAULT_MARCA_COMERCIAL);
        assertThat(testProduct.getTipoEmbalagem()).isEqualTo(DEFAULT_TIPO_EMBALAGEM);
        assertThat(testProduct.getNumLiberacaoBrasilia()).isEqualTo(DEFAULT_NUM_LIBERACAO_BRASILIA);
        assertThat(testProduct.getTemperaturaConservacao()).isEqualTo(DEFAULT_TEMPERATURA_CONSERVACAO);
        assertThat(testProduct.getUmidade()).isEqualTo(DEFAULT_UMIDADE);
        assertThat(testProduct.getLuminosidade()).isEqualTo(DEFAULT_LUMINOSIDADE);
        assertThat(testProduct.getEmbalagemSecundaria()).isEqualTo(DEFAULT_EMBALAGEM_SECUNDARIA);
        assertThat(testProduct.getFormaFisica()).isEqualTo(DEFAULT_FORMA_FISICA);
        assertThat(testProduct.getFinalidade()).isEqualTo(DEFAULT_FINALIDADE);
        assertThat(testProduct.getItemProdutivoRc()).isEqualTo(DEFAULT_ITEM_PRODUTIVO_RC);
        assertThat(testProduct.getEmbalagemPrimaria()).isEqualTo(DEFAULT_EMBALAGEM_PRIMARIA);
        assertThat(testProduct.getDescricaoAnvisa()).isEqualTo(DEFAULT_DESCRICAO_ANVISA);
        assertThat(testProduct.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testProduct.getCodUnidadeMedidaDimensao()).isEqualTo(DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO);
        assertThat(testProduct.getCodMaterial()).isEqualTo(DEFAULT_COD_MATERIAL);
        assertThat(testProduct.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testProduct.getCodigoAduana()).isEqualTo(DEFAULT_CODIGO_ADUANA);
        assertThat(testProduct.getClasseRisco()).isEqualTo(DEFAULT_CLASSE_RISCO);
        assertThat(testProduct.getCodRisco()).isEqualTo(DEFAULT_COD_RISCO);
        assertThat(testProduct.getFlexField12()).isEqualTo(DEFAULT_FLEX_FIELD_12);
        assertThat(testProduct.getFlexField13()).isEqualTo(DEFAULT_FLEX_FIELD_13);
        assertThat(testProduct.getFlexField1Number()).isEqualTo(DEFAULT_FLEX_FIELD_1_NUMBER);
        assertThat(testProduct.getFlexField2Number()).isEqualTo(DEFAULT_FLEX_FIELD_2_NUMBER);
        assertThat(testProduct.getFlexField3Number()).isEqualTo(DEFAULT_FLEX_FIELD_3_NUMBER);
        assertThat(testProduct.getFlexField4Number()).isEqualTo(DEFAULT_FLEX_FIELD_4_NUMBER);
        assertThat(testProduct.getFlexField5Number()).isEqualTo(DEFAULT_FLEX_FIELD_5_NUMBER);
        assertThat(testProduct.getStatusScansys()).isEqualTo(DEFAULT_STATUS_SCANSYS);
        assertThat(testProduct.getCodEstruturaAtual()).isEqualTo(DEFAULT_COD_ESTRUTURA_ATUAL);
        assertThat(testProduct.getPercTolerancia()).isEqualTo(DEFAULT_PERC_TOLERANCIA);
        assertThat(testProduct.getPisEspecifico()).isEqualTo(DEFAULT_PIS_ESPECIFICO);
        assertThat(testProduct.getCofinsEspecifico()).isEqualTo(DEFAULT_COFINS_ESPECIFICO);
        assertThat(testProduct.getFlexField14()).isEqualTo(DEFAULT_FLEX_FIELD_14);
        assertThat(testProduct.getFlexField15()).isEqualTo(DEFAULT_FLEX_FIELD_15);
        assertThat(testProduct.getFlexField16()).isEqualTo(DEFAULT_FLEX_FIELD_16);
        assertThat(testProduct.getFlexField17()).isEqualTo(DEFAULT_FLEX_FIELD_17);
        assertThat(testProduct.getFlexField18()).isEqualTo(DEFAULT_FLEX_FIELD_18);
        assertThat(testProduct.getFlexField19()).isEqualTo(DEFAULT_FLEX_FIELD_19);
        assertThat(testProduct.getFlexField20()).isEqualTo(DEFAULT_FLEX_FIELD_20);
        assertThat(testProduct.getFlexField21()).isEqualTo(DEFAULT_FLEX_FIELD_21);
        assertThat(testProduct.getFlexField22()).isEqualTo(DEFAULT_FLEX_FIELD_22);
        assertThat(testProduct.getFlexField23()).isEqualTo(DEFAULT_FLEX_FIELD_23);
        assertThat(testProduct.getFlexField24()).isEqualTo(DEFAULT_FLEX_FIELD_24);
        assertThat(testProduct.getFlexField25()).isEqualTo(DEFAULT_FLEX_FIELD_25);
        assertThat(testProduct.getFlexField26()).isEqualTo(DEFAULT_FLEX_FIELD_26);
        assertThat(testProduct.getFlexField27()).isEqualTo(DEFAULT_FLEX_FIELD_27);
        assertThat(testProduct.getFlexField28()).isEqualTo(DEFAULT_FLEX_FIELD_28);
        assertThat(testProduct.getFlexField29()).isEqualTo(DEFAULT_FLEX_FIELD_29);
        assertThat(testProduct.getFlexField30()).isEqualTo(DEFAULT_FLEX_FIELD_30);
        assertThat(testProduct.getFlexField31()).isEqualTo(DEFAULT_FLEX_FIELD_31);
        assertThat(testProduct.getFlexField32()).isEqualTo(DEFAULT_FLEX_FIELD_32);
        assertThat(testProduct.getFlexField33()).isEqualTo(DEFAULT_FLEX_FIELD_33);
        assertThat(testProduct.getsCodBarrasGtin()).isEqualTo(DEFAULT_S_COD_BARRAS_GTIN);
        assertThat(testProduct.getnVlrUnitLimiteUsd()).isEqualTo(DEFAULT_N_VLR_UNIT_LIMITE_USD);
        assertThat(testProduct.getnCodProdAnp()).isEqualTo(DEFAULT_N_COD_PROD_ANP);
        assertThat(testProduct.getnCustoProducao()).isEqualTo(DEFAULT_N_CUSTO_PRODUCAO);
        assertThat(testProduct.getsDestino()).isEqualTo(DEFAULT_S_DESTINO);
        assertThat(testProduct.getnPercentualGlp()).isEqualTo(DEFAULT_N_PERCENTUAL_GLP);
        assertThat(testProduct.getnLocField1()).isEqualTo(DEFAULT_N_LOC_FIELD_1);
        assertThat(testProduct.getnLocField2()).isEqualTo(DEFAULT_N_LOC_FIELD_2);
        assertThat(testProduct.getnLocField3()).isEqualTo(DEFAULT_N_LOC_FIELD_3);
        assertThat(testProduct.getnLocField4()).isEqualTo(DEFAULT_N_LOC_FIELD_4);
        assertThat(testProduct.getnLocField5()).isEqualTo(DEFAULT_N_LOC_FIELD_5);
        assertThat(testProduct.getnLocField6()).isEqualTo(DEFAULT_N_LOC_FIELD_6);
        assertThat(testProduct.getnLocField7()).isEqualTo(DEFAULT_N_LOC_FIELD_7);
        assertThat(testProduct.getnLocField8()).isEqualTo(DEFAULT_N_LOC_FIELD_8);
        assertThat(testProduct.getsLocField1()).isEqualTo(DEFAULT_S_LOC_FIELD_1);
        assertThat(testProduct.getsLocField2()).isEqualTo(DEFAULT_S_LOC_FIELD_2);
        assertThat(testProduct.getsLocField3()).isEqualTo(DEFAULT_S_LOC_FIELD_3);
        assertThat(testProduct.getsLocField4()).isEqualTo(DEFAULT_S_LOC_FIELD_4);
        assertThat(testProduct.getsLocField5()).isEqualTo(DEFAULT_S_LOC_FIELD_5);
        assertThat(testProduct.getnIdDocOcr()).isEqualTo(DEFAULT_N_ID_DOC_OCR);
        assertThat(testProduct.getsLocField6()).isEqualTo(DEFAULT_S_LOC_FIELD_6);
        assertThat(testProduct.getsLocField7()).isEqualTo(DEFAULT_S_LOC_FIELD_7);
        assertThat(testProduct.getsLocField8()).isEqualTo(DEFAULT_S_LOC_FIELD_8);
        assertThat(testProduct.getsLocField9()).isEqualTo(DEFAULT_S_LOC_FIELD_9);
        assertThat(testProduct.getsLocField10()).isEqualTo(DEFAULT_S_LOC_FIELD_10);
        assertThat(testProduct.getsLocField11()).isEqualTo(DEFAULT_S_LOC_FIELD_11);
        assertThat(testProduct.getsLocField12()).isEqualTo(DEFAULT_S_LOC_FIELD_12);
        assertThat(testProduct.getsLocField13()).isEqualTo(DEFAULT_S_LOC_FIELD_13);
        assertThat(testProduct.getsLocField14()).isEqualTo(DEFAULT_S_LOC_FIELD_14);
        assertThat(testProduct.getsLocField15()).isEqualTo(DEFAULT_S_LOC_FIELD_15);
        assertThat(testProduct.getsCodProdAnvisa()).isEqualTo(DEFAULT_S_COD_PROD_ANVISA);
        assertThat(testProduct.getsDescProdAnp()).isEqualTo(DEFAULT_S_DESC_PROD_ANP);
        assertThat(testProduct.getnPercGlpNac()).isEqualTo(DEFAULT_N_PERC_GLP_NAC);
        assertThat(testProduct.getnPercGlpImp()).isEqualTo(DEFAULT_N_PERC_GLP_IMP);
        assertThat(testProduct.getnValorPartida()).isEqualTo(DEFAULT_N_VALOR_PARTIDA);
        assertThat(testProduct.getsGtinUnidTrib()).isEqualTo(DEFAULT_S_GTIN_UNID_TRIB);
        assertThat(testProduct.getsCodigoModalidade()).isEqualTo(DEFAULT_S_CODIGO_MODALIDADE);
        assertThat(testProduct.getsCodigogpc()).isEqualTo(DEFAULT_S_CODIGOGPC);
        assertThat(testProduct.getsCodigogpcbrick()).isEqualTo(DEFAULT_S_CODIGOGPCBRICK);
        assertThat(testProduct.getsCodigounspsc()).isEqualTo(DEFAULT_S_CODIGOUNSPSC);
        assertThat(testProduct.getsSituacao()).isEqualTo(DEFAULT_S_SITUACAO);
        assertThat(testProduct.getsEnviado()).isEqualTo(DEFAULT_S_ENVIADO);
        assertThat(testProduct.getsMotivoIsencaoAnvisa()).isEqualTo(DEFAULT_S_MOTIVO_ISENCAO_ANVISA);
        assertThat(testProduct.getsIcProntoParaEnvio()).isEqualTo(DEFAULT_S_IC_PRONTO_PARA_ENVIO);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }


    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduto").value(hasItem(DEFAULT_ID_PRODUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].codRecipiente").value(hasItem(DEFAULT_COD_RECIPIENTE.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tpEmbalagem").value(hasItem(DEFAULT_TP_EMBALAGEM.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].idDispositivo").value(hasItem(DEFAULT_ID_DISPOSITIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeEstoque").value(hasItem(DEFAULT_UNIDADE_ESTOQUE.toString())))
            .andExpect(jsonPath("$.[*].naladincca").value(hasItem(DEFAULT_NALADINCCA.toString())))
            .andExpect(jsonPath("$.[*].ncm").value(hasItem(DEFAULT_NCM.toString())))
            .andExpect(jsonPath("$.[*].naladish").value(hasItem(DEFAULT_NALADISH.toString())))
            .andExpect(jsonPath("$.[*].linhaProduto").value(hasItem(DEFAULT_LINHA_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].pesoBruto").value(hasItem(DEFAULT_PESO_BRUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoLiquido").value(hasItem(DEFAULT_PESO_LIQUIDO.doubleValue())))
            .andExpect(jsonPath("$.[*].registroMs").value(hasItem(DEFAULT_REGISTRO_MS.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(sameInstant(DEFAULT_VALIDADE))))
            .andExpect(jsonPath("$.[*].necessitaLi").value(hasItem(DEFAULT_NECESSITA_LI.toString())))
            .andExpect(jsonPath("$.[*].recof").value(hasItem(DEFAULT_RECOF.toString())))
            .andExpect(jsonPath("$.[*].reducaoIcms").value(hasItem(DEFAULT_REDUCAO_ICMS.doubleValue())))
            .andExpect(jsonPath("$.[*].codOnu").value(hasItem(DEFAULT_COD_ONU.toString())))
            .andExpect(jsonPath("$.[*].seqSuframa").value(hasItem(DEFAULT_SEQ_SUFRAMA.toString())))
            .andExpect(jsonPath("$.[*].naoTributavel").value(hasItem(DEFAULT_NAO_TRIBUTAVEL.toString())))
            .andExpect(jsonPath("$.[*].ipiEspecifico").value(hasItem(DEFAULT_IPI_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].iiEspecifico").value(hasItem(DEFAULT_II_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].ii").value(hasItem(DEFAULT_II.doubleValue())))
            .andExpect(jsonPath("$.[*].ipi").value(hasItem(DEFAULT_IPI.doubleValue())))
            .andExpect(jsonPath("$.[*].valorUnitaria").value(hasItem(DEFAULT_VALOR_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].capacidadeUnitaria").value(hasItem(DEFAULT_CAPACIDADE_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].fatorConversao").value(hasItem(DEFAULT_FATOR_CONVERSAO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricaoResumida").value(hasItem(DEFAULT_DESCRICAO_RESUMIDA.toString())))
            .andExpect(jsonPath("$.[*].atualizacao").value(hasItem(sameInstant(DEFAULT_ATUALIZACAO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].unidadePeso").value(hasItem(DEFAULT_UNIDADE_PESO.toString())))
            .andExpect(jsonPath("$.[*].codUnidadeQtde").value(hasItem(DEFAULT_COD_UNIDADE_QTDE.toString())))
            .andExpect(jsonPath("$.[*].codUnidadeComercializada").value(hasItem(DEFAULT_COD_UNIDADE_COMERCIALIZADA.toString())))
            .andExpect(jsonPath("$.[*].codUnidadeUnitaria").value(hasItem(DEFAULT_COD_UNIDADE_UNITARIA.toString())))
            .andExpect(jsonPath("$.[*].pesoQuilo").value(hasItem(DEFAULT_PESO_QUILO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoUnidComercializada").value(hasItem(DEFAULT_PESO_UNID_COMERCIALIZADA.doubleValue())))
            .andExpect(jsonPath("$.[*].codExternoGip").value(hasItem(DEFAULT_COD_EXTERNO_GIP.toString())))
            .andExpect(jsonPath("$.[*].ultimoInformante").value(hasItem(DEFAULT_ULTIMO_INFORMANTE.doubleValue())))
            .andExpect(jsonPath("$.[*].tsp").value(hasItem(DEFAULT_TSP.toString())))
            .andExpect(jsonPath("$.[*].tipoRecof").value(hasItem(DEFAULT_TIPO_RECOF.toString())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS.toString())))
            .andExpect(jsonPath("$.[*].pesoRateavel").value(hasItem(DEFAULT_PESO_RATEAVEL.toString())))
            .andExpect(jsonPath("$.[*].necessitaRevisao").value(hasItem(DEFAULT_NECESSITA_REVISAO.toString())))
            .andExpect(jsonPath("$.[*].tipoProduto").value(hasItem(DEFAULT_TIPO_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].procedencia").value(hasItem(DEFAULT_PROCEDENCIA.toString())))
            .andExpect(jsonPath("$.[*].chassi").value(hasItem(DEFAULT_CHASSI.toString())))
            .andExpect(jsonPath("$.[*].especificacaoTecnica").value(hasItem(DEFAULT_ESPECIFICACAO_TECNICA.toString())))
            .andExpect(jsonPath("$.[*].materiaPrimaBasica").value(hasItem(DEFAULT_MATERIA_PRIMA_BASICA.toString())))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.toString())))
            .andExpect(jsonPath("$.[*].codOrigem").value(hasItem(DEFAULT_COD_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].materialGenerico").value(hasItem(DEFAULT_MATERIAL_GENERICO.toString())))
            .andExpect(jsonPath("$.[*].cargaPerigosa").value(hasItem(DEFAULT_CARGA_PERIGOSA.toString())))
            .andExpect(jsonPath("$.[*].codUnidadeVenda").value(hasItem(DEFAULT_COD_UNIDADE_VENDA.toString())))
            .andExpect(jsonPath("$.[*].flexField1").value(hasItem(DEFAULT_FLEX_FIELD_1.toString())))
            .andExpect(jsonPath("$.[*].flexField2").value(hasItem(DEFAULT_FLEX_FIELD_2.toString())))
            .andExpect(jsonPath("$.[*].flexField3").value(hasItem(DEFAULT_FLEX_FIELD_3.toString())))
            .andExpect(jsonPath("$.[*].descricaoDetalhada").value(hasItem(DEFAULT_DESCRICAO_DETALHADA.toString())))
            .andExpect(jsonPath("$.[*].idOrganizacao").value(hasItem(DEFAULT_ID_ORGANIZACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].codPaisOrigem").value(hasItem(DEFAULT_COD_PAIS_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].cicloProdutivo").value(hasItem(DEFAULT_CICLO_PRODUTIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].partNumberFornecedor").value(hasItem(DEFAULT_PART_NUMBER_FORNECEDOR.toString())))
            .andExpect(jsonPath("$.[*].flagAtualizaIcms").value(hasItem(DEFAULT_FLAG_ATUALIZA_ICMS.toString())))
            .andExpect(jsonPath("$.[*].idDispositivoIpi").value(hasItem(DEFAULT_ID_DISPOSITIVO_IPI.doubleValue())))
            .andExpect(jsonPath("$.[*].codigoMoeda").value(hasItem(DEFAULT_CODIGO_MOEDA.toString())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].codProd").value(hasItem(DEFAULT_COD_PROD.toString())))
            .andExpect(jsonPath("$.[*].codProducao").value(hasItem(DEFAULT_COD_PRODUCAO.toString())))
            .andExpect(jsonPath("$.[*].procedenciaExp").value(hasItem(DEFAULT_PROCEDENCIA_EXP.toString())))
            .andExpect(jsonPath("$.[*].idAnuencia").value(hasItem(DEFAULT_ID_ANUENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMetroCubico").value(hasItem(DEFAULT_PESO_METRO_CUBICO.doubleValue())))
            .andExpect(jsonPath("$.[*].hts").value(hasItem(DEFAULT_HTS.toString())))
            .andExpect(jsonPath("$.[*].nomeComercial").value(hasItem(DEFAULT_NOME_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].idModelo").value(hasItem(DEFAULT_ID_MODELO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeFracionada").value(hasItem(DEFAULT_UNIDADE_FRACIONADA.toString())))
            .andExpect(jsonPath("$.[*].difPesoEmb").value(hasItem(DEFAULT_DIF_PESO_EMB.doubleValue())))
            .andExpect(jsonPath("$.[*].classProdRecof").value(hasItem(DEFAULT_CLASS_PROD_RECOF.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(sameInstant(DEFAULT_DATA_INICIO))))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(sameInstant(DEFAULT_DATA_FIM))))
            .andExpect(jsonPath("$.[*].dataInsertMov").value(hasItem(sameInstant(DEFAULT_DATA_INSERT_MOV))))
            .andExpect(jsonPath("$.[*].idCorporativo").value(hasItem(DEFAULT_ID_CORPORATIVO.toString())))
            .andExpect(jsonPath("$.[*].dataGerLeg").value(hasItem(sameInstant(DEFAULT_DATA_GER_LEG))))
            .andExpect(jsonPath("$.[*].procedenciaInfo").value(hasItem(DEFAULT_PROCEDENCIA_INFO.toString())))
            .andExpect(jsonPath("$.[*].codProdSuframa").value(hasItem(DEFAULT_COD_PROD_SUFRAMA.toString())))
            .andExpect(jsonPath("$.[*].pxExpTipoins").value(hasItem(DEFAULT_PX_EXP_TIPOINS.toString())))
            .andExpect(jsonPath("$.[*].tipoProdSuframa").value(hasItem(DEFAULT_TIPO_PROD_SUFRAMA.toString())))
            .andExpect(jsonPath("$.[*].idDetalheSuframa").value(hasItem(DEFAULT_ID_DETALHE_SUFRAMA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorUnitarioReal").value(hasItem(DEFAULT_VALOR_UNITARIO_REAL.doubleValue())))
            .andExpect(jsonPath("$.[*].necessitaRevisaoPexpam").value(hasItem(DEFAULT_NECESSITA_REVISAO_PEXPAM.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.doubleValue())))
            .andExpect(jsonPath("$.[*].pxModeloPadrao").value(hasItem(DEFAULT_PX_MODELO_PADRAO.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField4").value(hasItem(DEFAULT_FLEX_FIELD_4.toString())))
            .andExpect(jsonPath("$.[*].flexField5").value(hasItem(DEFAULT_FLEX_FIELD_5.toString())))
            .andExpect(jsonPath("$.[*].flexField6").value(hasItem(DEFAULT_FLEX_FIELD_6.toString())))
            .andExpect(jsonPath("$.[*].flexField7").value(hasItem(DEFAULT_FLEX_FIELD_7.toString())))
            .andExpect(jsonPath("$.[*].flexField8").value(hasItem(DEFAULT_FLEX_FIELD_8.toString())))
            .andExpect(jsonPath("$.[*].flexField9").value(hasItem(DEFAULT_FLEX_FIELD_9.toString())))
            .andExpect(jsonPath("$.[*].flexField10").value(hasItem(DEFAULT_FLEX_FIELD_10.toString())))
            .andExpect(jsonPath("$.[*].flexField11").value(hasItem(DEFAULT_FLEX_FIELD_11.toString())))
            .andExpect(jsonPath("$.[*].pisCofinsTipoAplic").value(hasItem(DEFAULT_PIS_COFINS_TIPO_APLIC.toString())))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS.doubleValue())))
            .andExpect(jsonPath("$.[*].cofins").value(hasItem(DEFAULT_COFINS.doubleValue())))
            .andExpect(jsonPath("$.[*].pisCofinsRedBase").value(hasItem(DEFAULT_PIS_COFINS_RED_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].modeloProdSuframa").value(hasItem(DEFAULT_MODELO_PROD_SUFRAMA.toString())))
            .andExpect(jsonPath("$.[*].codSiscomexUnidadeNcm").value(hasItem(DEFAULT_COD_SISCOMEX_UNIDADE_NCM.toString())))
            .andExpect(jsonPath("$.[*].partNumberCliente").value(hasItem(DEFAULT_PART_NUMBER_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].superficieUnitaria").value(hasItem(DEFAULT_SUPERFICIE_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].localEstoque").value(hasItem(DEFAULT_LOCAL_ESTOQUE.toString())))
            .andExpect(jsonPath("$.[*].codUnidadeSuperficie").value(hasItem(DEFAULT_COD_UNIDADE_SUPERFICIE.toString())))
            .andExpect(jsonPath("$.[*].rateioProdutoAcabado").value(hasItem(DEFAULT_RATEIO_PRODUTO_ACABADO.toString())))
            .andExpect(jsonPath("$.[*].pisCofinsCodUnEspec").value(hasItem(DEFAULT_PIS_COFINS_COD_UN_ESPEC.toString())))
            .andExpect(jsonPath("$.[*].recuperaImpostos").value(hasItem(DEFAULT_RECUPERA_IMPOSTOS.toString())))
            .andExpect(jsonPath("$.[*].flagNoRaf").value(hasItem(DEFAULT_FLAG_NO_RAF.toString())))
            .andExpect(jsonPath("$.[*].notaComplTipi").value(hasItem(DEFAULT_NOTA_COMPL_TIPI.toString())))
            .andExpect(jsonPath("$.[*].ipiReduzido").value(hasItem(DEFAULT_IPI_REDUZIDO.doubleValue())))
            .andExpect(jsonPath("$.[*].sujeitoLote").value(hasItem(DEFAULT_SUJEITO_LOTE.toString())))
            .andExpect(jsonPath("$.[*].marcaComercial").value(hasItem(DEFAULT_MARCA_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].tipoEmbalagem").value(hasItem(DEFAULT_TIPO_EMBALAGEM.toString())))
            .andExpect(jsonPath("$.[*].numLiberacaoBrasilia").value(hasItem(DEFAULT_NUM_LIBERACAO_BRASILIA.toString())))
            .andExpect(jsonPath("$.[*].temperaturaConservacao").value(hasItem(DEFAULT_TEMPERATURA_CONSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].umidade").value(hasItem(DEFAULT_UMIDADE.toString())))
            .andExpect(jsonPath("$.[*].luminosidade").value(hasItem(DEFAULT_LUMINOSIDADE.toString())))
            .andExpect(jsonPath("$.[*].embalagemSecundaria").value(hasItem(DEFAULT_EMBALAGEM_SECUNDARIA.toString())))
            .andExpect(jsonPath("$.[*].formaFisica").value(hasItem(DEFAULT_FORMA_FISICA.toString())))
            .andExpect(jsonPath("$.[*].finalidade").value(hasItem(DEFAULT_FINALIDADE.toString())))
            .andExpect(jsonPath("$.[*].itemProdutivoRc").value(hasItem(DEFAULT_ITEM_PRODUTIVO_RC.toString())))
            .andExpect(jsonPath("$.[*].embalagemPrimaria").value(hasItem(DEFAULT_EMBALAGEM_PRIMARIA.toString())))
            .andExpect(jsonPath("$.[*].descricaoAnvisa").value(hasItem(DEFAULT_DESCRICAO_ANVISA.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].codUnidadeMedidaDimensao").value(hasItem(DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO.toString())))
            .andExpect(jsonPath("$.[*].codMaterial").value(hasItem(DEFAULT_COD_MATERIAL.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.toString())))
            .andExpect(jsonPath("$.[*].codigoAduana").value(hasItem(DEFAULT_CODIGO_ADUANA.toString())))
            .andExpect(jsonPath("$.[*].classeRisco").value(hasItem(DEFAULT_CLASSE_RISCO.toString())))
            .andExpect(jsonPath("$.[*].codRisco").value(hasItem(DEFAULT_COD_RISCO.toString())))
            .andExpect(jsonPath("$.[*].flexField12").value(hasItem(DEFAULT_FLEX_FIELD_12.toString())))
            .andExpect(jsonPath("$.[*].flexField13").value(hasItem(DEFAULT_FLEX_FIELD_13.toString())))
            .andExpect(jsonPath("$.[*].flexField1Number").value(hasItem(DEFAULT_FLEX_FIELD_1_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField2Number").value(hasItem(DEFAULT_FLEX_FIELD_2_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField3Number").value(hasItem(DEFAULT_FLEX_FIELD_3_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField4Number").value(hasItem(DEFAULT_FLEX_FIELD_4_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField5Number").value(hasItem(DEFAULT_FLEX_FIELD_5_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].statusScansys").value(hasItem(DEFAULT_STATUS_SCANSYS.toString())))
            .andExpect(jsonPath("$.[*].codEstruturaAtual").value(hasItem(DEFAULT_COD_ESTRUTURA_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].percTolerancia").value(hasItem(DEFAULT_PERC_TOLERANCIA)))
            .andExpect(jsonPath("$.[*].pisEspecifico").value(hasItem(DEFAULT_PIS_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].cofinsEspecifico").value(hasItem(DEFAULT_COFINS_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].flexField14").value(hasItem(DEFAULT_FLEX_FIELD_14.toString())))
            .andExpect(jsonPath("$.[*].flexField15").value(hasItem(DEFAULT_FLEX_FIELD_15.toString())))
            .andExpect(jsonPath("$.[*].flexField16").value(hasItem(DEFAULT_FLEX_FIELD_16.toString())))
            .andExpect(jsonPath("$.[*].flexField17").value(hasItem(DEFAULT_FLEX_FIELD_17.toString())))
            .andExpect(jsonPath("$.[*].flexField18").value(hasItem(DEFAULT_FLEX_FIELD_18.toString())))
            .andExpect(jsonPath("$.[*].flexField19").value(hasItem(DEFAULT_FLEX_FIELD_19.toString())))
            .andExpect(jsonPath("$.[*].flexField20").value(hasItem(DEFAULT_FLEX_FIELD_20.toString())))
            .andExpect(jsonPath("$.[*].flexField21").value(hasItem(DEFAULT_FLEX_FIELD_21.toString())))
            .andExpect(jsonPath("$.[*].flexField22").value(hasItem(DEFAULT_FLEX_FIELD_22.toString())))
            .andExpect(jsonPath("$.[*].flexField23").value(hasItem(DEFAULT_FLEX_FIELD_23.toString())))
            .andExpect(jsonPath("$.[*].flexField24").value(hasItem(DEFAULT_FLEX_FIELD_24.toString())))
            .andExpect(jsonPath("$.[*].flexField25").value(hasItem(DEFAULT_FLEX_FIELD_25.toString())))
            .andExpect(jsonPath("$.[*].flexField26").value(hasItem(DEFAULT_FLEX_FIELD_26.toString())))
            .andExpect(jsonPath("$.[*].flexField27").value(hasItem(DEFAULT_FLEX_FIELD_27.toString())))
            .andExpect(jsonPath("$.[*].flexField28").value(hasItem(DEFAULT_FLEX_FIELD_28.toString())))
            .andExpect(jsonPath("$.[*].flexField29").value(hasItem(DEFAULT_FLEX_FIELD_29.toString())))
            .andExpect(jsonPath("$.[*].flexField30").value(hasItem(DEFAULT_FLEX_FIELD_30.toString())))
            .andExpect(jsonPath("$.[*].flexField31").value(hasItem(DEFAULT_FLEX_FIELD_31.toString())))
            .andExpect(jsonPath("$.[*].flexField32").value(hasItem(DEFAULT_FLEX_FIELD_32.toString())))
            .andExpect(jsonPath("$.[*].flexField33").value(hasItem(DEFAULT_FLEX_FIELD_33.toString())))
            .andExpect(jsonPath("$.[*].sCodBarrasGtin").value(hasItem(DEFAULT_S_COD_BARRAS_GTIN.toString())))
            .andExpect(jsonPath("$.[*].nVlrUnitLimiteUsd").value(hasItem(DEFAULT_N_VLR_UNIT_LIMITE_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].nCodProdAnp").value(hasItem(DEFAULT_N_COD_PROD_ANP.doubleValue())))
            .andExpect(jsonPath("$.[*].nCustoProducao").value(hasItem(DEFAULT_N_CUSTO_PRODUCAO.doubleValue())))
            .andExpect(jsonPath("$.[*].sDestino").value(hasItem(DEFAULT_S_DESTINO.toString())))
            .andExpect(jsonPath("$.[*].nPercentualGlp").value(hasItem(DEFAULT_N_PERCENTUAL_GLP.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField1").value(hasItem(DEFAULT_N_LOC_FIELD_1.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField2").value(hasItem(DEFAULT_N_LOC_FIELD_2.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField3").value(hasItem(DEFAULT_N_LOC_FIELD_3.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField4").value(hasItem(DEFAULT_N_LOC_FIELD_4.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField5").value(hasItem(DEFAULT_N_LOC_FIELD_5.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField6").value(hasItem(DEFAULT_N_LOC_FIELD_6.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField7").value(hasItem(DEFAULT_N_LOC_FIELD_7.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField8").value(hasItem(DEFAULT_N_LOC_FIELD_8.doubleValue())))
            .andExpect(jsonPath("$.[*].sLocField1").value(hasItem(DEFAULT_S_LOC_FIELD_1.toString())))
            .andExpect(jsonPath("$.[*].sLocField2").value(hasItem(DEFAULT_S_LOC_FIELD_2.toString())))
            .andExpect(jsonPath("$.[*].sLocField3").value(hasItem(DEFAULT_S_LOC_FIELD_3.toString())))
            .andExpect(jsonPath("$.[*].sLocField4").value(hasItem(DEFAULT_S_LOC_FIELD_4.toString())))
            .andExpect(jsonPath("$.[*].sLocField5").value(hasItem(DEFAULT_S_LOC_FIELD_5.toString())))
            .andExpect(jsonPath("$.[*].nIdDocOcr").value(hasItem(DEFAULT_N_ID_DOC_OCR.doubleValue())))
            .andExpect(jsonPath("$.[*].sLocField6").value(hasItem(DEFAULT_S_LOC_FIELD_6.toString())))
            .andExpect(jsonPath("$.[*].sLocField7").value(hasItem(DEFAULT_S_LOC_FIELD_7.toString())))
            .andExpect(jsonPath("$.[*].sLocField8").value(hasItem(DEFAULT_S_LOC_FIELD_8.toString())))
            .andExpect(jsonPath("$.[*].sLocField9").value(hasItem(DEFAULT_S_LOC_FIELD_9.toString())))
            .andExpect(jsonPath("$.[*].sLocField10").value(hasItem(DEFAULT_S_LOC_FIELD_10.toString())))
            .andExpect(jsonPath("$.[*].sLocField11").value(hasItem(DEFAULT_S_LOC_FIELD_11.toString())))
            .andExpect(jsonPath("$.[*].sLocField12").value(hasItem(DEFAULT_S_LOC_FIELD_12.toString())))
            .andExpect(jsonPath("$.[*].sLocField13").value(hasItem(DEFAULT_S_LOC_FIELD_13.toString())))
            .andExpect(jsonPath("$.[*].sLocField14").value(hasItem(DEFAULT_S_LOC_FIELD_14.toString())))
            .andExpect(jsonPath("$.[*].sLocField15").value(hasItem(DEFAULT_S_LOC_FIELD_15.toString())))
            .andExpect(jsonPath("$.[*].sCodProdAnvisa").value(hasItem(DEFAULT_S_COD_PROD_ANVISA.toString())))
            .andExpect(jsonPath("$.[*].sDescProdAnp").value(hasItem(DEFAULT_S_DESC_PROD_ANP.toString())))
            .andExpect(jsonPath("$.[*].nPercGlpNac").value(hasItem(DEFAULT_N_PERC_GLP_NAC.doubleValue())))
            .andExpect(jsonPath("$.[*].nPercGlpImp").value(hasItem(DEFAULT_N_PERC_GLP_IMP.doubleValue())))
            .andExpect(jsonPath("$.[*].nValorPartida").value(hasItem(DEFAULT_N_VALOR_PARTIDA.doubleValue())))
            .andExpect(jsonPath("$.[*].sGtinUnidTrib").value(hasItem(DEFAULT_S_GTIN_UNID_TRIB.toString())))
            .andExpect(jsonPath("$.[*].sCodigoModalidade").value(hasItem(DEFAULT_S_CODIGO_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].sCodigogpc").value(hasItem(DEFAULT_S_CODIGOGPC.toString())))
            .andExpect(jsonPath("$.[*].sCodigogpcbrick").value(hasItem(DEFAULT_S_CODIGOGPCBRICK.toString())))
            .andExpect(jsonPath("$.[*].sCodigounspsc").value(hasItem(DEFAULT_S_CODIGOUNSPSC.toString())))
            .andExpect(jsonPath("$.[*].sSituacao").value(hasItem(DEFAULT_S_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].sEnviado").value(hasItem(DEFAULT_S_ENVIADO.toString())))
            .andExpect(jsonPath("$.[*].sMotivoIsencaoAnvisa").value(hasItem(DEFAULT_S_MOTIVO_ISENCAO_ANVISA.toString())))
            .andExpect(jsonPath("$.[*].sIcProntoParaEnvio").value(hasItem(DEFAULT_S_IC_PRONTO_PARA_ENVIO.toString())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.idProduto").value(DEFAULT_ID_PRODUTO.doubleValue()))
            .andExpect(jsonPath("$.codRecipiente").value(DEFAULT_COD_RECIPIENTE.toString()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.toString()))
            .andExpect(jsonPath("$.tpEmbalagem").value(DEFAULT_TP_EMBALAGEM.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.idDispositivo").value(DEFAULT_ID_DISPOSITIVO.doubleValue()))
            .andExpect(jsonPath("$.unidadeEstoque").value(DEFAULT_UNIDADE_ESTOQUE.toString()))
            .andExpect(jsonPath("$.naladincca").value(DEFAULT_NALADINCCA.toString()))
            .andExpect(jsonPath("$.ncm").value(DEFAULT_NCM.toString()))
            .andExpect(jsonPath("$.naladish").value(DEFAULT_NALADISH.toString()))
            .andExpect(jsonPath("$.linhaProduto").value(DEFAULT_LINHA_PRODUTO.toString()))
            .andExpect(jsonPath("$.pesoBruto").value(DEFAULT_PESO_BRUTO.doubleValue()))
            .andExpect(jsonPath("$.pesoLiquido").value(DEFAULT_PESO_LIQUIDO.doubleValue()))
            .andExpect(jsonPath("$.registroMs").value(DEFAULT_REGISTRO_MS.toString()))
            .andExpect(jsonPath("$.validade").value(sameInstant(DEFAULT_VALIDADE)))
            .andExpect(jsonPath("$.necessitaLi").value(DEFAULT_NECESSITA_LI.toString()))
            .andExpect(jsonPath("$.recof").value(DEFAULT_RECOF.toString()))
            .andExpect(jsonPath("$.reducaoIcms").value(DEFAULT_REDUCAO_ICMS.doubleValue()))
            .andExpect(jsonPath("$.codOnu").value(DEFAULT_COD_ONU.toString()))
            .andExpect(jsonPath("$.seqSuframa").value(DEFAULT_SEQ_SUFRAMA.toString()))
            .andExpect(jsonPath("$.naoTributavel").value(DEFAULT_NAO_TRIBUTAVEL.toString()))
            .andExpect(jsonPath("$.ipiEspecifico").value(DEFAULT_IPI_ESPECIFICO.toString()))
            .andExpect(jsonPath("$.iiEspecifico").value(DEFAULT_II_ESPECIFICO.toString()))
            .andExpect(jsonPath("$.ii").value(DEFAULT_II.doubleValue()))
            .andExpect(jsonPath("$.ipi").value(DEFAULT_IPI.doubleValue()))
            .andExpect(jsonPath("$.valorUnitaria").value(DEFAULT_VALOR_UNITARIA.doubleValue()))
            .andExpect(jsonPath("$.capacidadeUnitaria").value(DEFAULT_CAPACIDADE_UNITARIA.doubleValue()))
            .andExpect(jsonPath("$.fatorConversao").value(DEFAULT_FATOR_CONVERSAO.doubleValue()))
            .andExpect(jsonPath("$.descricaoResumida").value(DEFAULT_DESCRICAO_RESUMIDA.toString()))
            .andExpect(jsonPath("$.atualizacao").value(sameInstant(DEFAULT_ATUALIZACAO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.unidadePeso").value(DEFAULT_UNIDADE_PESO.toString()))
            .andExpect(jsonPath("$.codUnidadeQtde").value(DEFAULT_COD_UNIDADE_QTDE.toString()))
            .andExpect(jsonPath("$.codUnidadeComercializada").value(DEFAULT_COD_UNIDADE_COMERCIALIZADA.toString()))
            .andExpect(jsonPath("$.codUnidadeUnitaria").value(DEFAULT_COD_UNIDADE_UNITARIA.toString()))
            .andExpect(jsonPath("$.pesoQuilo").value(DEFAULT_PESO_QUILO.doubleValue()))
            .andExpect(jsonPath("$.pesoUnidComercializada").value(DEFAULT_PESO_UNID_COMERCIALIZADA.doubleValue()))
            .andExpect(jsonPath("$.codExternoGip").value(DEFAULT_COD_EXTERNO_GIP.toString()))
            .andExpect(jsonPath("$.ultimoInformante").value(DEFAULT_ULTIMO_INFORMANTE.doubleValue()))
            .andExpect(jsonPath("$.tsp").value(DEFAULT_TSP.toString()))
            .andExpect(jsonPath("$.tipoRecof").value(DEFAULT_TIPO_RECOF.toString()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS.toString()))
            .andExpect(jsonPath("$.pesoRateavel").value(DEFAULT_PESO_RATEAVEL.toString()))
            .andExpect(jsonPath("$.necessitaRevisao").value(DEFAULT_NECESSITA_REVISAO.toString()))
            .andExpect(jsonPath("$.tipoProduto").value(DEFAULT_TIPO_PRODUTO.toString()))
            .andExpect(jsonPath("$.procedencia").value(DEFAULT_PROCEDENCIA.toString()))
            .andExpect(jsonPath("$.chassi").value(DEFAULT_CHASSI.toString()))
            .andExpect(jsonPath("$.especificacaoTecnica").value(DEFAULT_ESPECIFICACAO_TECNICA.toString()))
            .andExpect(jsonPath("$.materiaPrimaBasica").value(DEFAULT_MATERIA_PRIMA_BASICA.toString()))
            .andExpect(jsonPath("$.automatico").value(DEFAULT_AUTOMATICO.toString()))
            .andExpect(jsonPath("$.codOrigem").value(DEFAULT_COD_ORIGEM.toString()))
            .andExpect(jsonPath("$.materialGenerico").value(DEFAULT_MATERIAL_GENERICO.toString()))
            .andExpect(jsonPath("$.cargaPerigosa").value(DEFAULT_CARGA_PERIGOSA.toString()))
            .andExpect(jsonPath("$.codUnidadeVenda").value(DEFAULT_COD_UNIDADE_VENDA.toString()))
            .andExpect(jsonPath("$.flexField1").value(DEFAULT_FLEX_FIELD_1.toString()))
            .andExpect(jsonPath("$.flexField2").value(DEFAULT_FLEX_FIELD_2.toString()))
            .andExpect(jsonPath("$.flexField3").value(DEFAULT_FLEX_FIELD_3.toString()))
            .andExpect(jsonPath("$.descricaoDetalhada").value(DEFAULT_DESCRICAO_DETALHADA.toString()))
            .andExpect(jsonPath("$.idOrganizacao").value(DEFAULT_ID_ORGANIZACAO.doubleValue()))
            .andExpect(jsonPath("$.codPaisOrigem").value(DEFAULT_COD_PAIS_ORIGEM.toString()))
            .andExpect(jsonPath("$.cicloProdutivo").value(DEFAULT_CICLO_PRODUTIVO.doubleValue()))
            .andExpect(jsonPath("$.partNumberFornecedor").value(DEFAULT_PART_NUMBER_FORNECEDOR.toString()))
            .andExpect(jsonPath("$.flagAtualizaIcms").value(DEFAULT_FLAG_ATUALIZA_ICMS.toString()))
            .andExpect(jsonPath("$.idDispositivoIpi").value(DEFAULT_ID_DISPOSITIVO_IPI.doubleValue()))
            .andExpect(jsonPath("$.codigoMoeda").value(DEFAULT_CODIGO_MOEDA.toString()))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.doubleValue()))
            .andExpect(jsonPath("$.codProd").value(DEFAULT_COD_PROD.toString()))
            .andExpect(jsonPath("$.codProducao").value(DEFAULT_COD_PRODUCAO.toString()))
            .andExpect(jsonPath("$.procedenciaExp").value(DEFAULT_PROCEDENCIA_EXP.toString()))
            .andExpect(jsonPath("$.idAnuencia").value(DEFAULT_ID_ANUENCIA.doubleValue()))
            .andExpect(jsonPath("$.pesoMetroCubico").value(DEFAULT_PESO_METRO_CUBICO.doubleValue()))
            .andExpect(jsonPath("$.hts").value(DEFAULT_HTS.toString()))
            .andExpect(jsonPath("$.nomeComercial").value(DEFAULT_NOME_COMERCIAL.toString()))
            .andExpect(jsonPath("$.idModelo").value(DEFAULT_ID_MODELO.doubleValue()))
            .andExpect(jsonPath("$.unidadeFracionada").value(DEFAULT_UNIDADE_FRACIONADA.toString()))
            .andExpect(jsonPath("$.difPesoEmb").value(DEFAULT_DIF_PESO_EMB.doubleValue()))
            .andExpect(jsonPath("$.classProdRecof").value(DEFAULT_CLASS_PROD_RECOF.toString()))
            .andExpect(jsonPath("$.dataInicio").value(sameInstant(DEFAULT_DATA_INICIO)))
            .andExpect(jsonPath("$.dataFim").value(sameInstant(DEFAULT_DATA_FIM)))
            .andExpect(jsonPath("$.dataInsertMov").value(sameInstant(DEFAULT_DATA_INSERT_MOV)))
            .andExpect(jsonPath("$.idCorporativo").value(DEFAULT_ID_CORPORATIVO.toString()))
            .andExpect(jsonPath("$.dataGerLeg").value(sameInstant(DEFAULT_DATA_GER_LEG)))
            .andExpect(jsonPath("$.procedenciaInfo").value(DEFAULT_PROCEDENCIA_INFO.toString()))
            .andExpect(jsonPath("$.codProdSuframa").value(DEFAULT_COD_PROD_SUFRAMA.toString()))
            .andExpect(jsonPath("$.pxExpTipoins").value(DEFAULT_PX_EXP_TIPOINS.toString()))
            .andExpect(jsonPath("$.tipoProdSuframa").value(DEFAULT_TIPO_PROD_SUFRAMA.toString()))
            .andExpect(jsonPath("$.idDetalheSuframa").value(DEFAULT_ID_DETALHE_SUFRAMA.doubleValue()))
            .andExpect(jsonPath("$.valorUnitarioReal").value(DEFAULT_VALOR_UNITARIO_REAL.doubleValue()))
            .andExpect(jsonPath("$.necessitaRevisaoPexpam").value(DEFAULT_NECESSITA_REVISAO_PEXPAM.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.doubleValue()))
            .andExpect(jsonPath("$.pxModeloPadrao").value(DEFAULT_PX_MODELO_PADRAO.doubleValue()))
            .andExpect(jsonPath("$.flexField4").value(DEFAULT_FLEX_FIELD_4.toString()))
            .andExpect(jsonPath("$.flexField5").value(DEFAULT_FLEX_FIELD_5.toString()))
            .andExpect(jsonPath("$.flexField6").value(DEFAULT_FLEX_FIELD_6.toString()))
            .andExpect(jsonPath("$.flexField7").value(DEFAULT_FLEX_FIELD_7.toString()))
            .andExpect(jsonPath("$.flexField8").value(DEFAULT_FLEX_FIELD_8.toString()))
            .andExpect(jsonPath("$.flexField9").value(DEFAULT_FLEX_FIELD_9.toString()))
            .andExpect(jsonPath("$.flexField10").value(DEFAULT_FLEX_FIELD_10.toString()))
            .andExpect(jsonPath("$.flexField11").value(DEFAULT_FLEX_FIELD_11.toString()))
            .andExpect(jsonPath("$.pisCofinsTipoAplic").value(DEFAULT_PIS_COFINS_TIPO_APLIC.toString()))
            .andExpect(jsonPath("$.pis").value(DEFAULT_PIS.doubleValue()))
            .andExpect(jsonPath("$.cofins").value(DEFAULT_COFINS.doubleValue()))
            .andExpect(jsonPath("$.pisCofinsRedBase").value(DEFAULT_PIS_COFINS_RED_BASE.doubleValue()))
            .andExpect(jsonPath("$.modeloProdSuframa").value(DEFAULT_MODELO_PROD_SUFRAMA.toString()))
            .andExpect(jsonPath("$.codSiscomexUnidadeNcm").value(DEFAULT_COD_SISCOMEX_UNIDADE_NCM.toString()))
            .andExpect(jsonPath("$.partNumberCliente").value(DEFAULT_PART_NUMBER_CLIENTE.toString()))
            .andExpect(jsonPath("$.superficieUnitaria").value(DEFAULT_SUPERFICIE_UNITARIA.doubleValue()))
            .andExpect(jsonPath("$.localEstoque").value(DEFAULT_LOCAL_ESTOQUE.toString()))
            .andExpect(jsonPath("$.codUnidadeSuperficie").value(DEFAULT_COD_UNIDADE_SUPERFICIE.toString()))
            .andExpect(jsonPath("$.rateioProdutoAcabado").value(DEFAULT_RATEIO_PRODUTO_ACABADO.toString()))
            .andExpect(jsonPath("$.pisCofinsCodUnEspec").value(DEFAULT_PIS_COFINS_COD_UN_ESPEC.toString()))
            .andExpect(jsonPath("$.recuperaImpostos").value(DEFAULT_RECUPERA_IMPOSTOS.toString()))
            .andExpect(jsonPath("$.flagNoRaf").value(DEFAULT_FLAG_NO_RAF.toString()))
            .andExpect(jsonPath("$.notaComplTipi").value(DEFAULT_NOTA_COMPL_TIPI.toString()))
            .andExpect(jsonPath("$.ipiReduzido").value(DEFAULT_IPI_REDUZIDO.doubleValue()))
            .andExpect(jsonPath("$.sujeitoLote").value(DEFAULT_SUJEITO_LOTE.toString()))
            .andExpect(jsonPath("$.marcaComercial").value(DEFAULT_MARCA_COMERCIAL.toString()))
            .andExpect(jsonPath("$.tipoEmbalagem").value(DEFAULT_TIPO_EMBALAGEM.toString()))
            .andExpect(jsonPath("$.numLiberacaoBrasilia").value(DEFAULT_NUM_LIBERACAO_BRASILIA.toString()))
            .andExpect(jsonPath("$.temperaturaConservacao").value(DEFAULT_TEMPERATURA_CONSERVACAO.toString()))
            .andExpect(jsonPath("$.umidade").value(DEFAULT_UMIDADE.toString()))
            .andExpect(jsonPath("$.luminosidade").value(DEFAULT_LUMINOSIDADE.toString()))
            .andExpect(jsonPath("$.embalagemSecundaria").value(DEFAULT_EMBALAGEM_SECUNDARIA.toString()))
            .andExpect(jsonPath("$.formaFisica").value(DEFAULT_FORMA_FISICA.toString()))
            .andExpect(jsonPath("$.finalidade").value(DEFAULT_FINALIDADE.toString()))
            .andExpect(jsonPath("$.itemProdutivoRc").value(DEFAULT_ITEM_PRODUTIVO_RC.toString()))
            .andExpect(jsonPath("$.embalagemPrimaria").value(DEFAULT_EMBALAGEM_PRIMARIA.toString()))
            .andExpect(jsonPath("$.descricaoAnvisa").value(DEFAULT_DESCRICAO_ANVISA.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.codUnidadeMedidaDimensao").value(DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO.toString()))
            .andExpect(jsonPath("$.codMaterial").value(DEFAULT_COD_MATERIAL.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.toString()))
            .andExpect(jsonPath("$.codigoAduana").value(DEFAULT_CODIGO_ADUANA.toString()))
            .andExpect(jsonPath("$.classeRisco").value(DEFAULT_CLASSE_RISCO.toString()))
            .andExpect(jsonPath("$.codRisco").value(DEFAULT_COD_RISCO.toString()))
            .andExpect(jsonPath("$.flexField12").value(DEFAULT_FLEX_FIELD_12.toString()))
            .andExpect(jsonPath("$.flexField13").value(DEFAULT_FLEX_FIELD_13.toString()))
            .andExpect(jsonPath("$.flexField1Number").value(DEFAULT_FLEX_FIELD_1_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.flexField2Number").value(DEFAULT_FLEX_FIELD_2_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.flexField3Number").value(DEFAULT_FLEX_FIELD_3_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.flexField4Number").value(DEFAULT_FLEX_FIELD_4_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.flexField5Number").value(DEFAULT_FLEX_FIELD_5_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.statusScansys").value(DEFAULT_STATUS_SCANSYS.toString()))
            .andExpect(jsonPath("$.codEstruturaAtual").value(DEFAULT_COD_ESTRUTURA_ATUAL.doubleValue()))
            .andExpect(jsonPath("$.percTolerancia").value(DEFAULT_PERC_TOLERANCIA))
            .andExpect(jsonPath("$.pisEspecifico").value(DEFAULT_PIS_ESPECIFICO.toString()))
            .andExpect(jsonPath("$.cofinsEspecifico").value(DEFAULT_COFINS_ESPECIFICO.toString()))
            .andExpect(jsonPath("$.flexField14").value(DEFAULT_FLEX_FIELD_14.toString()))
            .andExpect(jsonPath("$.flexField15").value(DEFAULT_FLEX_FIELD_15.toString()))
            .andExpect(jsonPath("$.flexField16").value(DEFAULT_FLEX_FIELD_16.toString()))
            .andExpect(jsonPath("$.flexField17").value(DEFAULT_FLEX_FIELD_17.toString()))
            .andExpect(jsonPath("$.flexField18").value(DEFAULT_FLEX_FIELD_18.toString()))
            .andExpect(jsonPath("$.flexField19").value(DEFAULT_FLEX_FIELD_19.toString()))
            .andExpect(jsonPath("$.flexField20").value(DEFAULT_FLEX_FIELD_20.toString()))
            .andExpect(jsonPath("$.flexField21").value(DEFAULT_FLEX_FIELD_21.toString()))
            .andExpect(jsonPath("$.flexField22").value(DEFAULT_FLEX_FIELD_22.toString()))
            .andExpect(jsonPath("$.flexField23").value(DEFAULT_FLEX_FIELD_23.toString()))
            .andExpect(jsonPath("$.flexField24").value(DEFAULT_FLEX_FIELD_24.toString()))
            .andExpect(jsonPath("$.flexField25").value(DEFAULT_FLEX_FIELD_25.toString()))
            .andExpect(jsonPath("$.flexField26").value(DEFAULT_FLEX_FIELD_26.toString()))
            .andExpect(jsonPath("$.flexField27").value(DEFAULT_FLEX_FIELD_27.toString()))
            .andExpect(jsonPath("$.flexField28").value(DEFAULT_FLEX_FIELD_28.toString()))
            .andExpect(jsonPath("$.flexField29").value(DEFAULT_FLEX_FIELD_29.toString()))
            .andExpect(jsonPath("$.flexField30").value(DEFAULT_FLEX_FIELD_30.toString()))
            .andExpect(jsonPath("$.flexField31").value(DEFAULT_FLEX_FIELD_31.toString()))
            .andExpect(jsonPath("$.flexField32").value(DEFAULT_FLEX_FIELD_32.toString()))
            .andExpect(jsonPath("$.flexField33").value(DEFAULT_FLEX_FIELD_33.toString()))
            .andExpect(jsonPath("$.sCodBarrasGtin").value(DEFAULT_S_COD_BARRAS_GTIN.toString()))
            .andExpect(jsonPath("$.nVlrUnitLimiteUsd").value(DEFAULT_N_VLR_UNIT_LIMITE_USD.doubleValue()))
            .andExpect(jsonPath("$.nCodProdAnp").value(DEFAULT_N_COD_PROD_ANP.doubleValue()))
            .andExpect(jsonPath("$.nCustoProducao").value(DEFAULT_N_CUSTO_PRODUCAO.doubleValue()))
            .andExpect(jsonPath("$.sDestino").value(DEFAULT_S_DESTINO.toString()))
            .andExpect(jsonPath("$.nPercentualGlp").value(DEFAULT_N_PERCENTUAL_GLP.doubleValue()))
            .andExpect(jsonPath("$.nLocField1").value(DEFAULT_N_LOC_FIELD_1.doubleValue()))
            .andExpect(jsonPath("$.nLocField2").value(DEFAULT_N_LOC_FIELD_2.doubleValue()))
            .andExpect(jsonPath("$.nLocField3").value(DEFAULT_N_LOC_FIELD_3.doubleValue()))
            .andExpect(jsonPath("$.nLocField4").value(DEFAULT_N_LOC_FIELD_4.doubleValue()))
            .andExpect(jsonPath("$.nLocField5").value(DEFAULT_N_LOC_FIELD_5.doubleValue()))
            .andExpect(jsonPath("$.nLocField6").value(DEFAULT_N_LOC_FIELD_6.doubleValue()))
            .andExpect(jsonPath("$.nLocField7").value(DEFAULT_N_LOC_FIELD_7.doubleValue()))
            .andExpect(jsonPath("$.nLocField8").value(DEFAULT_N_LOC_FIELD_8.doubleValue()))
            .andExpect(jsonPath("$.sLocField1").value(DEFAULT_S_LOC_FIELD_1.toString()))
            .andExpect(jsonPath("$.sLocField2").value(DEFAULT_S_LOC_FIELD_2.toString()))
            .andExpect(jsonPath("$.sLocField3").value(DEFAULT_S_LOC_FIELD_3.toString()))
            .andExpect(jsonPath("$.sLocField4").value(DEFAULT_S_LOC_FIELD_4.toString()))
            .andExpect(jsonPath("$.sLocField5").value(DEFAULT_S_LOC_FIELD_5.toString()))
            .andExpect(jsonPath("$.nIdDocOcr").value(DEFAULT_N_ID_DOC_OCR.doubleValue()))
            .andExpect(jsonPath("$.sLocField6").value(DEFAULT_S_LOC_FIELD_6.toString()))
            .andExpect(jsonPath("$.sLocField7").value(DEFAULT_S_LOC_FIELD_7.toString()))
            .andExpect(jsonPath("$.sLocField8").value(DEFAULT_S_LOC_FIELD_8.toString()))
            .andExpect(jsonPath("$.sLocField9").value(DEFAULT_S_LOC_FIELD_9.toString()))
            .andExpect(jsonPath("$.sLocField10").value(DEFAULT_S_LOC_FIELD_10.toString()))
            .andExpect(jsonPath("$.sLocField11").value(DEFAULT_S_LOC_FIELD_11.toString()))
            .andExpect(jsonPath("$.sLocField12").value(DEFAULT_S_LOC_FIELD_12.toString()))
            .andExpect(jsonPath("$.sLocField13").value(DEFAULT_S_LOC_FIELD_13.toString()))
            .andExpect(jsonPath("$.sLocField14").value(DEFAULT_S_LOC_FIELD_14.toString()))
            .andExpect(jsonPath("$.sLocField15").value(DEFAULT_S_LOC_FIELD_15.toString()))
            .andExpect(jsonPath("$.sCodProdAnvisa").value(DEFAULT_S_COD_PROD_ANVISA.toString()))
            .andExpect(jsonPath("$.sDescProdAnp").value(DEFAULT_S_DESC_PROD_ANP.toString()))
            .andExpect(jsonPath("$.nPercGlpNac").value(DEFAULT_N_PERC_GLP_NAC.doubleValue()))
            .andExpect(jsonPath("$.nPercGlpImp").value(DEFAULT_N_PERC_GLP_IMP.doubleValue()))
            .andExpect(jsonPath("$.nValorPartida").value(DEFAULT_N_VALOR_PARTIDA.doubleValue()))
            .andExpect(jsonPath("$.sGtinUnidTrib").value(DEFAULT_S_GTIN_UNID_TRIB.toString()))
            .andExpect(jsonPath("$.sCodigoModalidade").value(DEFAULT_S_CODIGO_MODALIDADE.toString()))
            .andExpect(jsonPath("$.sCodigogpc").value(DEFAULT_S_CODIGOGPC.toString()))
            .andExpect(jsonPath("$.sCodigogpcbrick").value(DEFAULT_S_CODIGOGPCBRICK.toString()))
            .andExpect(jsonPath("$.sCodigounspsc").value(DEFAULT_S_CODIGOUNSPSC.toString()))
            .andExpect(jsonPath("$.sSituacao").value(DEFAULT_S_SITUACAO.toString()))
            .andExpect(jsonPath("$.sEnviado").value(DEFAULT_S_ENVIADO.toString()))
            .andExpect(jsonPath("$.sMotivoIsencaoAnvisa").value(DEFAULT_S_MOTIVO_ISENCAO_ANVISA.toString()))
            .andExpect(jsonPath("$.sIcProntoParaEnvio").value(DEFAULT_S_IC_PRONTO_PARA_ENVIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .idProduto(UPDATED_ID_PRODUTO)
            .codRecipiente(UPDATED_COD_RECIPIENTE)
            .partNumber(UPDATED_PART_NUMBER)
            .tpEmbalagem(UPDATED_TP_EMBALAGEM)
            .cnpj(UPDATED_CNPJ)
            .idDispositivo(UPDATED_ID_DISPOSITIVO)
            .unidadeEstoque(UPDATED_UNIDADE_ESTOQUE)
            .naladincca(UPDATED_NALADINCCA)
            .ncm(UPDATED_NCM)
            .naladish(UPDATED_NALADISH)
            .linhaProduto(UPDATED_LINHA_PRODUTO)
            .pesoBruto(UPDATED_PESO_BRUTO)
            .pesoLiquido(UPDATED_PESO_LIQUIDO)
            .registroMs(UPDATED_REGISTRO_MS)
            .validade(UPDATED_VALIDADE)
            .necessitaLi(UPDATED_NECESSITA_LI)
            .recof(UPDATED_RECOF)
            .reducaoIcms(UPDATED_REDUCAO_ICMS)
            .codOnu(UPDATED_COD_ONU)
            .seqSuframa(UPDATED_SEQ_SUFRAMA)
            .naoTributavel(UPDATED_NAO_TRIBUTAVEL)
            .ipiEspecifico(UPDATED_IPI_ESPECIFICO)
            .iiEspecifico(UPDATED_II_ESPECIFICO)
            .ii(UPDATED_II)
            .ipi(UPDATED_IPI)
            .valorUnitaria(UPDATED_VALOR_UNITARIA)
            .capacidadeUnitaria(UPDATED_CAPACIDADE_UNITARIA)
            .fatorConversao(UPDATED_FATOR_CONVERSAO)
            .descricaoResumida(UPDATED_DESCRICAO_RESUMIDA)
            .atualizacao(UPDATED_ATUALIZACAO)
            .status(UPDATED_STATUS)
            .unidadePeso(UPDATED_UNIDADE_PESO)
            .codUnidadeQtde(UPDATED_COD_UNIDADE_QTDE)
            .codUnidadeComercializada(UPDATED_COD_UNIDADE_COMERCIALIZADA)
            .codUnidadeUnitaria(UPDATED_COD_UNIDADE_UNITARIA)
            .pesoQuilo(UPDATED_PESO_QUILO)
            .pesoUnidComercializada(UPDATED_PESO_UNID_COMERCIALIZADA)
            .codExternoGip(UPDATED_COD_EXTERNO_GIP)
            .ultimoInformante(UPDATED_ULTIMO_INFORMANTE)
            .tsp(UPDATED_TSP)
            .tipoRecof(UPDATED_TIPO_RECOF)
            .obs(UPDATED_OBS)
            .pesoRateavel(UPDATED_PESO_RATEAVEL)
            .necessitaRevisao(UPDATED_NECESSITA_REVISAO)
            .tipoProduto(UPDATED_TIPO_PRODUTO)
            .procedencia(UPDATED_PROCEDENCIA)
            .chassi(UPDATED_CHASSI)
            .especificacaoTecnica(UPDATED_ESPECIFICACAO_TECNICA)
            .materiaPrimaBasica(UPDATED_MATERIA_PRIMA_BASICA)
            .automatico(UPDATED_AUTOMATICO)
            .codOrigem(UPDATED_COD_ORIGEM)
            .materialGenerico(UPDATED_MATERIAL_GENERICO)
            .cargaPerigosa(UPDATED_CARGA_PERIGOSA)
            .codUnidadeVenda(UPDATED_COD_UNIDADE_VENDA)
            .flexField1(UPDATED_FLEX_FIELD_1)
            .flexField2(UPDATED_FLEX_FIELD_2)
            .flexField3(UPDATED_FLEX_FIELD_3)
            .descricaoDetalhada(UPDATED_DESCRICAO_DETALHADA)
            .idOrganizacao(UPDATED_ID_ORGANIZACAO)
            .codPaisOrigem(UPDATED_COD_PAIS_ORIGEM)
            .cicloProdutivo(UPDATED_CICLO_PRODUTIVO)
            .partNumberFornecedor(UPDATED_PART_NUMBER_FORNECEDOR)
            .flagAtualizaIcms(UPDATED_FLAG_ATUALIZA_ICMS)
            .idDispositivoIpi(UPDATED_ID_DISPOSITIVO_IPI)
            .codigoMoeda(UPDATED_CODIGO_MOEDA)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .codProd(UPDATED_COD_PROD)
            .codProducao(UPDATED_COD_PRODUCAO)
            .procedenciaExp(UPDATED_PROCEDENCIA_EXP)
            .idAnuencia(UPDATED_ID_ANUENCIA)
            .pesoMetroCubico(UPDATED_PESO_METRO_CUBICO)
            .hts(UPDATED_HTS)
            .nomeComercial(UPDATED_NOME_COMERCIAL)
            .idModelo(UPDATED_ID_MODELO)
            .unidadeFracionada(UPDATED_UNIDADE_FRACIONADA)
            .difPesoEmb(UPDATED_DIF_PESO_EMB)
            .classProdRecof(UPDATED_CLASS_PROD_RECOF)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .dataInsertMov(UPDATED_DATA_INSERT_MOV)
            .idCorporativo(UPDATED_ID_CORPORATIVO)
            .dataGerLeg(UPDATED_DATA_GER_LEG)
            .procedenciaInfo(UPDATED_PROCEDENCIA_INFO)
            .codProdSuframa(UPDATED_COD_PROD_SUFRAMA)
            .pxExpTipoins(UPDATED_PX_EXP_TIPOINS)
            .tipoProdSuframa(UPDATED_TIPO_PROD_SUFRAMA)
            .idDetalheSuframa(UPDATED_ID_DETALHE_SUFRAMA)
            .valorUnitarioReal(UPDATED_VALOR_UNITARIO_REAL)
            .necessitaRevisaoPexpam(UPDATED_NECESSITA_REVISAO_PEXPAM)
            .modelo(UPDATED_MODELO)
            .pxModeloPadrao(UPDATED_PX_MODELO_PADRAO)
            .flexField4(UPDATED_FLEX_FIELD_4)
            .flexField5(UPDATED_FLEX_FIELD_5)
            .flexField6(UPDATED_FLEX_FIELD_6)
            .flexField7(UPDATED_FLEX_FIELD_7)
            .flexField8(UPDATED_FLEX_FIELD_8)
            .flexField9(UPDATED_FLEX_FIELD_9)
            .flexField10(UPDATED_FLEX_FIELD_10)
            .flexField11(UPDATED_FLEX_FIELD_11)
            .pisCofinsTipoAplic(UPDATED_PIS_COFINS_TIPO_APLIC)
            .pis(UPDATED_PIS)
            .cofins(UPDATED_COFINS)
            .pisCofinsRedBase(UPDATED_PIS_COFINS_RED_BASE)
            .modeloProdSuframa(UPDATED_MODELO_PROD_SUFRAMA)
            .codSiscomexUnidadeNcm(UPDATED_COD_SISCOMEX_UNIDADE_NCM)
            .partNumberCliente(UPDATED_PART_NUMBER_CLIENTE)
            .superficieUnitaria(UPDATED_SUPERFICIE_UNITARIA)
            .localEstoque(UPDATED_LOCAL_ESTOQUE)
            .codUnidadeSuperficie(UPDATED_COD_UNIDADE_SUPERFICIE)
            .rateioProdutoAcabado(UPDATED_RATEIO_PRODUTO_ACABADO)
            .pisCofinsCodUnEspec(UPDATED_PIS_COFINS_COD_UN_ESPEC)
            .recuperaImpostos(UPDATED_RECUPERA_IMPOSTOS)
            .flagNoRaf(UPDATED_FLAG_NO_RAF)
            .notaComplTipi(UPDATED_NOTA_COMPL_TIPI)
            .ipiReduzido(UPDATED_IPI_REDUZIDO)
            .sujeitoLote(UPDATED_SUJEITO_LOTE)
            .marcaComercial(UPDATED_MARCA_COMERCIAL)
            .tipoEmbalagem(UPDATED_TIPO_EMBALAGEM)
            .numLiberacaoBrasilia(UPDATED_NUM_LIBERACAO_BRASILIA)
            .temperaturaConservacao(UPDATED_TEMPERATURA_CONSERVACAO)
            .umidade(UPDATED_UMIDADE)
            .luminosidade(UPDATED_LUMINOSIDADE)
            .embalagemSecundaria(UPDATED_EMBALAGEM_SECUNDARIA)
            .formaFisica(UPDATED_FORMA_FISICA)
            .finalidade(UPDATED_FINALIDADE)
            .itemProdutivoRc(UPDATED_ITEM_PRODUTIVO_RC)
            .embalagemPrimaria(UPDATED_EMBALAGEM_PRIMARIA)
            .descricaoAnvisa(UPDATED_DESCRICAO_ANVISA)
            .volume(UPDATED_VOLUME)
            .codUnidadeMedidaDimensao(UPDATED_COD_UNIDADE_MEDIDA_DIMENSAO)
            .codMaterial(UPDATED_COD_MATERIAL)
            .ativo(UPDATED_ATIVO)
            .codigoAduana(UPDATED_CODIGO_ADUANA)
            .classeRisco(UPDATED_CLASSE_RISCO)
            .codRisco(UPDATED_COD_RISCO)
            .flexField12(UPDATED_FLEX_FIELD_12)
            .flexField13(UPDATED_FLEX_FIELD_13)
            .flexField1Number(UPDATED_FLEX_FIELD_1_NUMBER)
            .flexField2Number(UPDATED_FLEX_FIELD_2_NUMBER)
            .flexField3Number(UPDATED_FLEX_FIELD_3_NUMBER)
            .flexField4Number(UPDATED_FLEX_FIELD_4_NUMBER)
            .flexField5Number(UPDATED_FLEX_FIELD_5_NUMBER)
            .statusScansys(UPDATED_STATUS_SCANSYS)
            .codEstruturaAtual(UPDATED_COD_ESTRUTURA_ATUAL)
            .percTolerancia(UPDATED_PERC_TOLERANCIA)
            .pisEspecifico(UPDATED_PIS_ESPECIFICO)
            .cofinsEspecifico(UPDATED_COFINS_ESPECIFICO)
            .flexField14(UPDATED_FLEX_FIELD_14)
            .flexField15(UPDATED_FLEX_FIELD_15)
            .flexField16(UPDATED_FLEX_FIELD_16)
            .flexField17(UPDATED_FLEX_FIELD_17)
            .flexField18(UPDATED_FLEX_FIELD_18)
            .flexField19(UPDATED_FLEX_FIELD_19)
            .flexField20(UPDATED_FLEX_FIELD_20)
            .flexField21(UPDATED_FLEX_FIELD_21)
            .flexField22(UPDATED_FLEX_FIELD_22)
            .flexField23(UPDATED_FLEX_FIELD_23)
            .flexField24(UPDATED_FLEX_FIELD_24)
            .flexField25(UPDATED_FLEX_FIELD_25)
            .flexField26(UPDATED_FLEX_FIELD_26)
            .flexField27(UPDATED_FLEX_FIELD_27)
            .flexField28(UPDATED_FLEX_FIELD_28)
            .flexField29(UPDATED_FLEX_FIELD_29)
            .flexField30(UPDATED_FLEX_FIELD_30)
            .flexField31(UPDATED_FLEX_FIELD_31)
            .flexField32(UPDATED_FLEX_FIELD_32)
            .flexField33(UPDATED_FLEX_FIELD_33)
            .sCodBarrasGtin(UPDATED_S_COD_BARRAS_GTIN)
            .nVlrUnitLimiteUsd(UPDATED_N_VLR_UNIT_LIMITE_USD)
            .nCodProdAnp(UPDATED_N_COD_PROD_ANP)
            .nCustoProducao(UPDATED_N_CUSTO_PRODUCAO)
            .sDestino(UPDATED_S_DESTINO)
            .nPercentualGlp(UPDATED_N_PERCENTUAL_GLP)
            .nLocField1(UPDATED_N_LOC_FIELD_1)
            .nLocField2(UPDATED_N_LOC_FIELD_2)
            .nLocField3(UPDATED_N_LOC_FIELD_3)
            .nLocField4(UPDATED_N_LOC_FIELD_4)
            .nLocField5(UPDATED_N_LOC_FIELD_5)
            .nLocField6(UPDATED_N_LOC_FIELD_6)
            .nLocField7(UPDATED_N_LOC_FIELD_7)
            .nLocField8(UPDATED_N_LOC_FIELD_8)
            .sLocField1(UPDATED_S_LOC_FIELD_1)
            .sLocField2(UPDATED_S_LOC_FIELD_2)
            .sLocField3(UPDATED_S_LOC_FIELD_3)
            .sLocField4(UPDATED_S_LOC_FIELD_4)
            .sLocField5(UPDATED_S_LOC_FIELD_5)
            .nIdDocOcr(UPDATED_N_ID_DOC_OCR)
            .sLocField6(UPDATED_S_LOC_FIELD_6)
            .sLocField7(UPDATED_S_LOC_FIELD_7)
            .sLocField8(UPDATED_S_LOC_FIELD_8)
            .sLocField9(UPDATED_S_LOC_FIELD_9)
            .sLocField10(UPDATED_S_LOC_FIELD_10)
            .sLocField11(UPDATED_S_LOC_FIELD_11)
            .sLocField12(UPDATED_S_LOC_FIELD_12)
            .sLocField13(UPDATED_S_LOC_FIELD_13)
            .sLocField14(UPDATED_S_LOC_FIELD_14)
            .sLocField15(UPDATED_S_LOC_FIELD_15)
            .sCodProdAnvisa(UPDATED_S_COD_PROD_ANVISA)
            .sDescProdAnp(UPDATED_S_DESC_PROD_ANP)
            .nPercGlpNac(UPDATED_N_PERC_GLP_NAC)
            .nPercGlpImp(UPDATED_N_PERC_GLP_IMP)
            .nValorPartida(UPDATED_N_VALOR_PARTIDA)
            .sGtinUnidTrib(UPDATED_S_GTIN_UNID_TRIB)
            .sCodigoModalidade(UPDATED_S_CODIGO_MODALIDADE)
            .sCodigogpc(UPDATED_S_CODIGOGPC)
            .sCodigogpcbrick(UPDATED_S_CODIGOGPCBRICK)
            .sCodigounspsc(UPDATED_S_CODIGOUNSPSC)
            .sSituacao(UPDATED_S_SITUACAO)
            .sEnviado(UPDATED_S_ENVIADO)
            .sMotivoIsencaoAnvisa(UPDATED_S_MOTIVO_ISENCAO_ANVISA)
            .sIcProntoParaEnvio(UPDATED_S_IC_PRONTO_PARA_ENVIO);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getIdProduto()).isEqualTo(UPDATED_ID_PRODUTO);
        assertThat(testProduct.getCodRecipiente()).isEqualTo(UPDATED_COD_RECIPIENTE);
        assertThat(testProduct.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);
        assertThat(testProduct.getTpEmbalagem()).isEqualTo(UPDATED_TP_EMBALAGEM);
        assertThat(testProduct.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testProduct.getIdDispositivo()).isEqualTo(UPDATED_ID_DISPOSITIVO);
        assertThat(testProduct.getUnidadeEstoque()).isEqualTo(UPDATED_UNIDADE_ESTOQUE);
        assertThat(testProduct.getNaladincca()).isEqualTo(UPDATED_NALADINCCA);
        assertThat(testProduct.getNcm()).isEqualTo(UPDATED_NCM);
        assertThat(testProduct.getNaladish()).isEqualTo(UPDATED_NALADISH);
        assertThat(testProduct.getLinhaProduto()).isEqualTo(UPDATED_LINHA_PRODUTO);
        assertThat(testProduct.getPesoBruto()).isEqualTo(UPDATED_PESO_BRUTO);
        assertThat(testProduct.getPesoLiquido()).isEqualTo(UPDATED_PESO_LIQUIDO);
        assertThat(testProduct.getRegistroMs()).isEqualTo(UPDATED_REGISTRO_MS);
        assertThat(testProduct.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testProduct.getNecessitaLi()).isEqualTo(UPDATED_NECESSITA_LI);
        assertThat(testProduct.getRecof()).isEqualTo(UPDATED_RECOF);
        assertThat(testProduct.getReducaoIcms()).isEqualTo(UPDATED_REDUCAO_ICMS);
        assertThat(testProduct.getCodOnu()).isEqualTo(UPDATED_COD_ONU);
        assertThat(testProduct.getSeqSuframa()).isEqualTo(UPDATED_SEQ_SUFRAMA);
        assertThat(testProduct.getNaoTributavel()).isEqualTo(UPDATED_NAO_TRIBUTAVEL);
        assertThat(testProduct.getIpiEspecifico()).isEqualTo(UPDATED_IPI_ESPECIFICO);
        assertThat(testProduct.getIiEspecifico()).isEqualTo(UPDATED_II_ESPECIFICO);
        assertThat(testProduct.getIi()).isEqualTo(UPDATED_II);
        assertThat(testProduct.getIpi()).isEqualTo(UPDATED_IPI);
        assertThat(testProduct.getValorUnitaria()).isEqualTo(UPDATED_VALOR_UNITARIA);
        assertThat(testProduct.getCapacidadeUnitaria()).isEqualTo(UPDATED_CAPACIDADE_UNITARIA);
        assertThat(testProduct.getFatorConversao()).isEqualTo(UPDATED_FATOR_CONVERSAO);
        assertThat(testProduct.getDescricaoResumida()).isEqualTo(UPDATED_DESCRICAO_RESUMIDA);
        assertThat(testProduct.getAtualizacao()).isEqualTo(UPDATED_ATUALIZACAO);
        assertThat(testProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduct.getUnidadePeso()).isEqualTo(UPDATED_UNIDADE_PESO);
        assertThat(testProduct.getCodUnidadeQtde()).isEqualTo(UPDATED_COD_UNIDADE_QTDE);
        assertThat(testProduct.getCodUnidadeComercializada()).isEqualTo(UPDATED_COD_UNIDADE_COMERCIALIZADA);
        assertThat(testProduct.getCodUnidadeUnitaria()).isEqualTo(UPDATED_COD_UNIDADE_UNITARIA);
        assertThat(testProduct.getPesoQuilo()).isEqualTo(UPDATED_PESO_QUILO);
        assertThat(testProduct.getPesoUnidComercializada()).isEqualTo(UPDATED_PESO_UNID_COMERCIALIZADA);
        assertThat(testProduct.getCodExternoGip()).isEqualTo(UPDATED_COD_EXTERNO_GIP);
        assertThat(testProduct.getUltimoInformante()).isEqualTo(UPDATED_ULTIMO_INFORMANTE);
        assertThat(testProduct.getTsp()).isEqualTo(UPDATED_TSP);
        assertThat(testProduct.getTipoRecof()).isEqualTo(UPDATED_TIPO_RECOF);
        assertThat(testProduct.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testProduct.getPesoRateavel()).isEqualTo(UPDATED_PESO_RATEAVEL);
        assertThat(testProduct.getNecessitaRevisao()).isEqualTo(UPDATED_NECESSITA_REVISAO);
        assertThat(testProduct.getTipoProduto()).isEqualTo(UPDATED_TIPO_PRODUTO);
        assertThat(testProduct.getProcedencia()).isEqualTo(UPDATED_PROCEDENCIA);
        assertThat(testProduct.getChassi()).isEqualTo(UPDATED_CHASSI);
        assertThat(testProduct.getEspecificacaoTecnica()).isEqualTo(UPDATED_ESPECIFICACAO_TECNICA);
        assertThat(testProduct.getMateriaPrimaBasica()).isEqualTo(UPDATED_MATERIA_PRIMA_BASICA);
        assertThat(testProduct.getAutomatico()).isEqualTo(UPDATED_AUTOMATICO);
        assertThat(testProduct.getCodOrigem()).isEqualTo(UPDATED_COD_ORIGEM);
        assertThat(testProduct.getMaterialGenerico()).isEqualTo(UPDATED_MATERIAL_GENERICO);
        assertThat(testProduct.getCargaPerigosa()).isEqualTo(UPDATED_CARGA_PERIGOSA);
        assertThat(testProduct.getCodUnidadeVenda()).isEqualTo(UPDATED_COD_UNIDADE_VENDA);
        assertThat(testProduct.getFlexField1()).isEqualTo(UPDATED_FLEX_FIELD_1);
        assertThat(testProduct.getFlexField2()).isEqualTo(UPDATED_FLEX_FIELD_2);
        assertThat(testProduct.getFlexField3()).isEqualTo(UPDATED_FLEX_FIELD_3);
        assertThat(testProduct.getDescricaoDetalhada()).isEqualTo(UPDATED_DESCRICAO_DETALHADA);
        assertThat(testProduct.getIdOrganizacao()).isEqualTo(UPDATED_ID_ORGANIZACAO);
        assertThat(testProduct.getCodPaisOrigem()).isEqualTo(UPDATED_COD_PAIS_ORIGEM);
        assertThat(testProduct.getCicloProdutivo()).isEqualTo(UPDATED_CICLO_PRODUTIVO);
        assertThat(testProduct.getPartNumberFornecedor()).isEqualTo(UPDATED_PART_NUMBER_FORNECEDOR);
        assertThat(testProduct.getFlagAtualizaIcms()).isEqualTo(UPDATED_FLAG_ATUALIZA_ICMS);
        assertThat(testProduct.getIdDispositivoIpi()).isEqualTo(UPDATED_ID_DISPOSITIVO_IPI);
        assertThat(testProduct.getCodigoMoeda()).isEqualTo(UPDATED_CODIGO_MOEDA);
        assertThat(testProduct.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testProduct.getCodProd()).isEqualTo(UPDATED_COD_PROD);
        assertThat(testProduct.getCodProducao()).isEqualTo(UPDATED_COD_PRODUCAO);
        assertThat(testProduct.getProcedenciaExp()).isEqualTo(UPDATED_PROCEDENCIA_EXP);
        assertThat(testProduct.getIdAnuencia()).isEqualTo(UPDATED_ID_ANUENCIA);
        assertThat(testProduct.getPesoMetroCubico()).isEqualTo(UPDATED_PESO_METRO_CUBICO);
        assertThat(testProduct.getHts()).isEqualTo(UPDATED_HTS);
        assertThat(testProduct.getNomeComercial()).isEqualTo(UPDATED_NOME_COMERCIAL);
        assertThat(testProduct.getIdModelo()).isEqualTo(UPDATED_ID_MODELO);
        assertThat(testProduct.getUnidadeFracionada()).isEqualTo(UPDATED_UNIDADE_FRACIONADA);
        assertThat(testProduct.getDifPesoEmb()).isEqualTo(UPDATED_DIF_PESO_EMB);
        assertThat(testProduct.getClassProdRecof()).isEqualTo(UPDATED_CLASS_PROD_RECOF);
        assertThat(testProduct.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testProduct.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testProduct.getDataInsertMov()).isEqualTo(UPDATED_DATA_INSERT_MOV);
        assertThat(testProduct.getIdCorporativo()).isEqualTo(UPDATED_ID_CORPORATIVO);
        assertThat(testProduct.getDataGerLeg()).isEqualTo(UPDATED_DATA_GER_LEG);
        assertThat(testProduct.getProcedenciaInfo()).isEqualTo(UPDATED_PROCEDENCIA_INFO);
        assertThat(testProduct.getCodProdSuframa()).isEqualTo(UPDATED_COD_PROD_SUFRAMA);
        assertThat(testProduct.getPxExpTipoins()).isEqualTo(UPDATED_PX_EXP_TIPOINS);
        assertThat(testProduct.getTipoProdSuframa()).isEqualTo(UPDATED_TIPO_PROD_SUFRAMA);
        assertThat(testProduct.getIdDetalheSuframa()).isEqualTo(UPDATED_ID_DETALHE_SUFRAMA);
        assertThat(testProduct.getValorUnitarioReal()).isEqualTo(UPDATED_VALOR_UNITARIO_REAL);
        assertThat(testProduct.getNecessitaRevisaoPexpam()).isEqualTo(UPDATED_NECESSITA_REVISAO_PEXPAM);
        assertThat(testProduct.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testProduct.getPxModeloPadrao()).isEqualTo(UPDATED_PX_MODELO_PADRAO);
        assertThat(testProduct.getFlexField4()).isEqualTo(UPDATED_FLEX_FIELD_4);
        assertThat(testProduct.getFlexField5()).isEqualTo(UPDATED_FLEX_FIELD_5);
        assertThat(testProduct.getFlexField6()).isEqualTo(UPDATED_FLEX_FIELD_6);
        assertThat(testProduct.getFlexField7()).isEqualTo(UPDATED_FLEX_FIELD_7);
        assertThat(testProduct.getFlexField8()).isEqualTo(UPDATED_FLEX_FIELD_8);
        assertThat(testProduct.getFlexField9()).isEqualTo(UPDATED_FLEX_FIELD_9);
        assertThat(testProduct.getFlexField10()).isEqualTo(UPDATED_FLEX_FIELD_10);
        assertThat(testProduct.getFlexField11()).isEqualTo(UPDATED_FLEX_FIELD_11);
        assertThat(testProduct.getPisCofinsTipoAplic()).isEqualTo(UPDATED_PIS_COFINS_TIPO_APLIC);
        assertThat(testProduct.getPis()).isEqualTo(UPDATED_PIS);
        assertThat(testProduct.getCofins()).isEqualTo(UPDATED_COFINS);
        assertThat(testProduct.getPisCofinsRedBase()).isEqualTo(UPDATED_PIS_COFINS_RED_BASE);
        assertThat(testProduct.getModeloProdSuframa()).isEqualTo(UPDATED_MODELO_PROD_SUFRAMA);
        assertThat(testProduct.getCodSiscomexUnidadeNcm()).isEqualTo(UPDATED_COD_SISCOMEX_UNIDADE_NCM);
        assertThat(testProduct.getPartNumberCliente()).isEqualTo(UPDATED_PART_NUMBER_CLIENTE);
        assertThat(testProduct.getSuperficieUnitaria()).isEqualTo(UPDATED_SUPERFICIE_UNITARIA);
        assertThat(testProduct.getLocalEstoque()).isEqualTo(UPDATED_LOCAL_ESTOQUE);
        assertThat(testProduct.getCodUnidadeSuperficie()).isEqualTo(UPDATED_COD_UNIDADE_SUPERFICIE);
        assertThat(testProduct.getRateioProdutoAcabado()).isEqualTo(UPDATED_RATEIO_PRODUTO_ACABADO);
        assertThat(testProduct.getPisCofinsCodUnEspec()).isEqualTo(UPDATED_PIS_COFINS_COD_UN_ESPEC);
        assertThat(testProduct.getRecuperaImpostos()).isEqualTo(UPDATED_RECUPERA_IMPOSTOS);
        assertThat(testProduct.getFlagNoRaf()).isEqualTo(UPDATED_FLAG_NO_RAF);
        assertThat(testProduct.getNotaComplTipi()).isEqualTo(UPDATED_NOTA_COMPL_TIPI);
        assertThat(testProduct.getIpiReduzido()).isEqualTo(UPDATED_IPI_REDUZIDO);
        assertThat(testProduct.getSujeitoLote()).isEqualTo(UPDATED_SUJEITO_LOTE);
        assertThat(testProduct.getMarcaComercial()).isEqualTo(UPDATED_MARCA_COMERCIAL);
        assertThat(testProduct.getTipoEmbalagem()).isEqualTo(UPDATED_TIPO_EMBALAGEM);
        assertThat(testProduct.getNumLiberacaoBrasilia()).isEqualTo(UPDATED_NUM_LIBERACAO_BRASILIA);
        assertThat(testProduct.getTemperaturaConservacao()).isEqualTo(UPDATED_TEMPERATURA_CONSERVACAO);
        assertThat(testProduct.getUmidade()).isEqualTo(UPDATED_UMIDADE);
        assertThat(testProduct.getLuminosidade()).isEqualTo(UPDATED_LUMINOSIDADE);
        assertThat(testProduct.getEmbalagemSecundaria()).isEqualTo(UPDATED_EMBALAGEM_SECUNDARIA);
        assertThat(testProduct.getFormaFisica()).isEqualTo(UPDATED_FORMA_FISICA);
        assertThat(testProduct.getFinalidade()).isEqualTo(UPDATED_FINALIDADE);
        assertThat(testProduct.getItemProdutivoRc()).isEqualTo(UPDATED_ITEM_PRODUTIVO_RC);
        assertThat(testProduct.getEmbalagemPrimaria()).isEqualTo(UPDATED_EMBALAGEM_PRIMARIA);
        assertThat(testProduct.getDescricaoAnvisa()).isEqualTo(UPDATED_DESCRICAO_ANVISA);
        assertThat(testProduct.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testProduct.getCodUnidadeMedidaDimensao()).isEqualTo(UPDATED_COD_UNIDADE_MEDIDA_DIMENSAO);
        assertThat(testProduct.getCodMaterial()).isEqualTo(UPDATED_COD_MATERIAL);
        assertThat(testProduct.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testProduct.getCodigoAduana()).isEqualTo(UPDATED_CODIGO_ADUANA);
        assertThat(testProduct.getClasseRisco()).isEqualTo(UPDATED_CLASSE_RISCO);
        assertThat(testProduct.getCodRisco()).isEqualTo(UPDATED_COD_RISCO);
        assertThat(testProduct.getFlexField12()).isEqualTo(UPDATED_FLEX_FIELD_12);
        assertThat(testProduct.getFlexField13()).isEqualTo(UPDATED_FLEX_FIELD_13);
        assertThat(testProduct.getFlexField1Number()).isEqualTo(UPDATED_FLEX_FIELD_1_NUMBER);
        assertThat(testProduct.getFlexField2Number()).isEqualTo(UPDATED_FLEX_FIELD_2_NUMBER);
        assertThat(testProduct.getFlexField3Number()).isEqualTo(UPDATED_FLEX_FIELD_3_NUMBER);
        assertThat(testProduct.getFlexField4Number()).isEqualTo(UPDATED_FLEX_FIELD_4_NUMBER);
        assertThat(testProduct.getFlexField5Number()).isEqualTo(UPDATED_FLEX_FIELD_5_NUMBER);
        assertThat(testProduct.getStatusScansys()).isEqualTo(UPDATED_STATUS_SCANSYS);
        assertThat(testProduct.getCodEstruturaAtual()).isEqualTo(UPDATED_COD_ESTRUTURA_ATUAL);
        assertThat(testProduct.getPercTolerancia()).isEqualTo(UPDATED_PERC_TOLERANCIA);
        assertThat(testProduct.getPisEspecifico()).isEqualTo(UPDATED_PIS_ESPECIFICO);
        assertThat(testProduct.getCofinsEspecifico()).isEqualTo(UPDATED_COFINS_ESPECIFICO);
        assertThat(testProduct.getFlexField14()).isEqualTo(UPDATED_FLEX_FIELD_14);
        assertThat(testProduct.getFlexField15()).isEqualTo(UPDATED_FLEX_FIELD_15);
        assertThat(testProduct.getFlexField16()).isEqualTo(UPDATED_FLEX_FIELD_16);
        assertThat(testProduct.getFlexField17()).isEqualTo(UPDATED_FLEX_FIELD_17);
        assertThat(testProduct.getFlexField18()).isEqualTo(UPDATED_FLEX_FIELD_18);
        assertThat(testProduct.getFlexField19()).isEqualTo(UPDATED_FLEX_FIELD_19);
        assertThat(testProduct.getFlexField20()).isEqualTo(UPDATED_FLEX_FIELD_20);
        assertThat(testProduct.getFlexField21()).isEqualTo(UPDATED_FLEX_FIELD_21);
        assertThat(testProduct.getFlexField22()).isEqualTo(UPDATED_FLEX_FIELD_22);
        assertThat(testProduct.getFlexField23()).isEqualTo(UPDATED_FLEX_FIELD_23);
        assertThat(testProduct.getFlexField24()).isEqualTo(UPDATED_FLEX_FIELD_24);
        assertThat(testProduct.getFlexField25()).isEqualTo(UPDATED_FLEX_FIELD_25);
        assertThat(testProduct.getFlexField26()).isEqualTo(UPDATED_FLEX_FIELD_26);
        assertThat(testProduct.getFlexField27()).isEqualTo(UPDATED_FLEX_FIELD_27);
        assertThat(testProduct.getFlexField28()).isEqualTo(UPDATED_FLEX_FIELD_28);
        assertThat(testProduct.getFlexField29()).isEqualTo(UPDATED_FLEX_FIELD_29);
        assertThat(testProduct.getFlexField30()).isEqualTo(UPDATED_FLEX_FIELD_30);
        assertThat(testProduct.getFlexField31()).isEqualTo(UPDATED_FLEX_FIELD_31);
        assertThat(testProduct.getFlexField32()).isEqualTo(UPDATED_FLEX_FIELD_32);
        assertThat(testProduct.getFlexField33()).isEqualTo(UPDATED_FLEX_FIELD_33);
        assertThat(testProduct.getsCodBarrasGtin()).isEqualTo(UPDATED_S_COD_BARRAS_GTIN);
        assertThat(testProduct.getnVlrUnitLimiteUsd()).isEqualTo(UPDATED_N_VLR_UNIT_LIMITE_USD);
        assertThat(testProduct.getnCodProdAnp()).isEqualTo(UPDATED_N_COD_PROD_ANP);
        assertThat(testProduct.getnCustoProducao()).isEqualTo(UPDATED_N_CUSTO_PRODUCAO);
        assertThat(testProduct.getsDestino()).isEqualTo(UPDATED_S_DESTINO);
        assertThat(testProduct.getnPercentualGlp()).isEqualTo(UPDATED_N_PERCENTUAL_GLP);
        assertThat(testProduct.getnLocField1()).isEqualTo(UPDATED_N_LOC_FIELD_1);
        assertThat(testProduct.getnLocField2()).isEqualTo(UPDATED_N_LOC_FIELD_2);
        assertThat(testProduct.getnLocField3()).isEqualTo(UPDATED_N_LOC_FIELD_3);
        assertThat(testProduct.getnLocField4()).isEqualTo(UPDATED_N_LOC_FIELD_4);
        assertThat(testProduct.getnLocField5()).isEqualTo(UPDATED_N_LOC_FIELD_5);
        assertThat(testProduct.getnLocField6()).isEqualTo(UPDATED_N_LOC_FIELD_6);
        assertThat(testProduct.getnLocField7()).isEqualTo(UPDATED_N_LOC_FIELD_7);
        assertThat(testProduct.getnLocField8()).isEqualTo(UPDATED_N_LOC_FIELD_8);
        assertThat(testProduct.getsLocField1()).isEqualTo(UPDATED_S_LOC_FIELD_1);
        assertThat(testProduct.getsLocField2()).isEqualTo(UPDATED_S_LOC_FIELD_2);
        assertThat(testProduct.getsLocField3()).isEqualTo(UPDATED_S_LOC_FIELD_3);
        assertThat(testProduct.getsLocField4()).isEqualTo(UPDATED_S_LOC_FIELD_4);
        assertThat(testProduct.getsLocField5()).isEqualTo(UPDATED_S_LOC_FIELD_5);
        assertThat(testProduct.getnIdDocOcr()).isEqualTo(UPDATED_N_ID_DOC_OCR);
        assertThat(testProduct.getsLocField6()).isEqualTo(UPDATED_S_LOC_FIELD_6);
        assertThat(testProduct.getsLocField7()).isEqualTo(UPDATED_S_LOC_FIELD_7);
        assertThat(testProduct.getsLocField8()).isEqualTo(UPDATED_S_LOC_FIELD_8);
        assertThat(testProduct.getsLocField9()).isEqualTo(UPDATED_S_LOC_FIELD_9);
        assertThat(testProduct.getsLocField10()).isEqualTo(UPDATED_S_LOC_FIELD_10);
        assertThat(testProduct.getsLocField11()).isEqualTo(UPDATED_S_LOC_FIELD_11);
        assertThat(testProduct.getsLocField12()).isEqualTo(UPDATED_S_LOC_FIELD_12);
        assertThat(testProduct.getsLocField13()).isEqualTo(UPDATED_S_LOC_FIELD_13);
        assertThat(testProduct.getsLocField14()).isEqualTo(UPDATED_S_LOC_FIELD_14);
        assertThat(testProduct.getsLocField15()).isEqualTo(UPDATED_S_LOC_FIELD_15);
        assertThat(testProduct.getsCodProdAnvisa()).isEqualTo(UPDATED_S_COD_PROD_ANVISA);
        assertThat(testProduct.getsDescProdAnp()).isEqualTo(UPDATED_S_DESC_PROD_ANP);
        assertThat(testProduct.getnPercGlpNac()).isEqualTo(UPDATED_N_PERC_GLP_NAC);
        assertThat(testProduct.getnPercGlpImp()).isEqualTo(UPDATED_N_PERC_GLP_IMP);
        assertThat(testProduct.getnValorPartida()).isEqualTo(UPDATED_N_VALOR_PARTIDA);
        assertThat(testProduct.getsGtinUnidTrib()).isEqualTo(UPDATED_S_GTIN_UNID_TRIB);
        assertThat(testProduct.getsCodigoModalidade()).isEqualTo(UPDATED_S_CODIGO_MODALIDADE);
        assertThat(testProduct.getsCodigogpc()).isEqualTo(UPDATED_S_CODIGOGPC);
        assertThat(testProduct.getsCodigogpcbrick()).isEqualTo(UPDATED_S_CODIGOGPCBRICK);
        assertThat(testProduct.getsCodigounspsc()).isEqualTo(UPDATED_S_CODIGOUNSPSC);
        assertThat(testProduct.getsSituacao()).isEqualTo(UPDATED_S_SITUACAO);
        assertThat(testProduct.getsEnviado()).isEqualTo(UPDATED_S_ENVIADO);
        assertThat(testProduct.getsMotivoIsencaoAnvisa()).isEqualTo(UPDATED_S_MOTIVO_ISENCAO_ANVISA);
        assertThat(testProduct.getsIcProntoParaEnvio()).isEqualTo(UPDATED_S_IC_PRONTO_PARA_ENVIO);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));
        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduto").value(hasItem(DEFAULT_ID_PRODUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].codRecipiente").value(hasItem(DEFAULT_COD_RECIPIENTE)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].tpEmbalagem").value(hasItem(DEFAULT_TP_EMBALAGEM)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].idDispositivo").value(hasItem(DEFAULT_ID_DISPOSITIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeEstoque").value(hasItem(DEFAULT_UNIDADE_ESTOQUE)))
            .andExpect(jsonPath("$.[*].naladincca").value(hasItem(DEFAULT_NALADINCCA)))
            .andExpect(jsonPath("$.[*].ncm").value(hasItem(DEFAULT_NCM)))
            .andExpect(jsonPath("$.[*].naladish").value(hasItem(DEFAULT_NALADISH)))
            .andExpect(jsonPath("$.[*].linhaProduto").value(hasItem(DEFAULT_LINHA_PRODUTO)))
            .andExpect(jsonPath("$.[*].pesoBruto").value(hasItem(DEFAULT_PESO_BRUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoLiquido").value(hasItem(DEFAULT_PESO_LIQUIDO.doubleValue())))
            .andExpect(jsonPath("$.[*].registroMs").value(hasItem(DEFAULT_REGISTRO_MS)))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(sameInstant(DEFAULT_VALIDADE))))
            .andExpect(jsonPath("$.[*].necessitaLi").value(hasItem(DEFAULT_NECESSITA_LI)))
            .andExpect(jsonPath("$.[*].recof").value(hasItem(DEFAULT_RECOF)))
            .andExpect(jsonPath("$.[*].reducaoIcms").value(hasItem(DEFAULT_REDUCAO_ICMS.doubleValue())))
            .andExpect(jsonPath("$.[*].codOnu").value(hasItem(DEFAULT_COD_ONU)))
            .andExpect(jsonPath("$.[*].seqSuframa").value(hasItem(DEFAULT_SEQ_SUFRAMA)))
            .andExpect(jsonPath("$.[*].naoTributavel").value(hasItem(DEFAULT_NAO_TRIBUTAVEL)))
            .andExpect(jsonPath("$.[*].ipiEspecifico").value(hasItem(DEFAULT_IPI_ESPECIFICO)))
            .andExpect(jsonPath("$.[*].iiEspecifico").value(hasItem(DEFAULT_II_ESPECIFICO)))
            .andExpect(jsonPath("$.[*].ii").value(hasItem(DEFAULT_II.doubleValue())))
            .andExpect(jsonPath("$.[*].ipi").value(hasItem(DEFAULT_IPI.doubleValue())))
            .andExpect(jsonPath("$.[*].valorUnitaria").value(hasItem(DEFAULT_VALOR_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].capacidadeUnitaria").value(hasItem(DEFAULT_CAPACIDADE_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].fatorConversao").value(hasItem(DEFAULT_FATOR_CONVERSAO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricaoResumida").value(hasItem(DEFAULT_DESCRICAO_RESUMIDA)))
            .andExpect(jsonPath("$.[*].atualizacao").value(hasItem(sameInstant(DEFAULT_ATUALIZACAO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].unidadePeso").value(hasItem(DEFAULT_UNIDADE_PESO)))
            .andExpect(jsonPath("$.[*].codUnidadeQtde").value(hasItem(DEFAULT_COD_UNIDADE_QTDE)))
            .andExpect(jsonPath("$.[*].codUnidadeComercializada").value(hasItem(DEFAULT_COD_UNIDADE_COMERCIALIZADA)))
            .andExpect(jsonPath("$.[*].codUnidadeUnitaria").value(hasItem(DEFAULT_COD_UNIDADE_UNITARIA)))
            .andExpect(jsonPath("$.[*].pesoQuilo").value(hasItem(DEFAULT_PESO_QUILO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoUnidComercializada").value(hasItem(DEFAULT_PESO_UNID_COMERCIALIZADA.doubleValue())))
            .andExpect(jsonPath("$.[*].codExternoGip").value(hasItem(DEFAULT_COD_EXTERNO_GIP)))
            .andExpect(jsonPath("$.[*].ultimoInformante").value(hasItem(DEFAULT_ULTIMO_INFORMANTE.doubleValue())))
            .andExpect(jsonPath("$.[*].tsp").value(hasItem(DEFAULT_TSP)))
            .andExpect(jsonPath("$.[*].tipoRecof").value(hasItem(DEFAULT_TIPO_RECOF)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].pesoRateavel").value(hasItem(DEFAULT_PESO_RATEAVEL)))
            .andExpect(jsonPath("$.[*].necessitaRevisao").value(hasItem(DEFAULT_NECESSITA_REVISAO)))
            .andExpect(jsonPath("$.[*].tipoProduto").value(hasItem(DEFAULT_TIPO_PRODUTO)))
            .andExpect(jsonPath("$.[*].procedencia").value(hasItem(DEFAULT_PROCEDENCIA)))
            .andExpect(jsonPath("$.[*].chassi").value(hasItem(DEFAULT_CHASSI)))
            .andExpect(jsonPath("$.[*].especificacaoTecnica").value(hasItem(DEFAULT_ESPECIFICACAO_TECNICA)))
            .andExpect(jsonPath("$.[*].materiaPrimaBasica").value(hasItem(DEFAULT_MATERIA_PRIMA_BASICA)))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO)))
            .andExpect(jsonPath("$.[*].codOrigem").value(hasItem(DEFAULT_COD_ORIGEM)))
            .andExpect(jsonPath("$.[*].materialGenerico").value(hasItem(DEFAULT_MATERIAL_GENERICO)))
            .andExpect(jsonPath("$.[*].cargaPerigosa").value(hasItem(DEFAULT_CARGA_PERIGOSA)))
            .andExpect(jsonPath("$.[*].codUnidadeVenda").value(hasItem(DEFAULT_COD_UNIDADE_VENDA)))
            .andExpect(jsonPath("$.[*].flexField1").value(hasItem(DEFAULT_FLEX_FIELD_1)))
            .andExpect(jsonPath("$.[*].flexField2").value(hasItem(DEFAULT_FLEX_FIELD_2)))
            .andExpect(jsonPath("$.[*].flexField3").value(hasItem(DEFAULT_FLEX_FIELD_3)))
            .andExpect(jsonPath("$.[*].descricaoDetalhada").value(hasItem(DEFAULT_DESCRICAO_DETALHADA)))
            .andExpect(jsonPath("$.[*].idOrganizacao").value(hasItem(DEFAULT_ID_ORGANIZACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].codPaisOrigem").value(hasItem(DEFAULT_COD_PAIS_ORIGEM)))
            .andExpect(jsonPath("$.[*].cicloProdutivo").value(hasItem(DEFAULT_CICLO_PRODUTIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].partNumberFornecedor").value(hasItem(DEFAULT_PART_NUMBER_FORNECEDOR)))
            .andExpect(jsonPath("$.[*].flagAtualizaIcms").value(hasItem(DEFAULT_FLAG_ATUALIZA_ICMS)))
            .andExpect(jsonPath("$.[*].idDispositivoIpi").value(hasItem(DEFAULT_ID_DISPOSITIVO_IPI.doubleValue())))
            .andExpect(jsonPath("$.[*].codigoMoeda").value(hasItem(DEFAULT_CODIGO_MOEDA)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].codProd").value(hasItem(DEFAULT_COD_PROD)))
            .andExpect(jsonPath("$.[*].codProducao").value(hasItem(DEFAULT_COD_PRODUCAO)))
            .andExpect(jsonPath("$.[*].procedenciaExp").value(hasItem(DEFAULT_PROCEDENCIA_EXP)))
            .andExpect(jsonPath("$.[*].idAnuencia").value(hasItem(DEFAULT_ID_ANUENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoMetroCubico").value(hasItem(DEFAULT_PESO_METRO_CUBICO.doubleValue())))
            .andExpect(jsonPath("$.[*].hts").value(hasItem(DEFAULT_HTS)))
            .andExpect(jsonPath("$.[*].nomeComercial").value(hasItem(DEFAULT_NOME_COMERCIAL)))
            .andExpect(jsonPath("$.[*].idModelo").value(hasItem(DEFAULT_ID_MODELO.doubleValue())))
            .andExpect(jsonPath("$.[*].unidadeFracionada").value(hasItem(DEFAULT_UNIDADE_FRACIONADA)))
            .andExpect(jsonPath("$.[*].difPesoEmb").value(hasItem(DEFAULT_DIF_PESO_EMB.doubleValue())))
            .andExpect(jsonPath("$.[*].classProdRecof").value(hasItem(DEFAULT_CLASS_PROD_RECOF)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(sameInstant(DEFAULT_DATA_INICIO))))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(sameInstant(DEFAULT_DATA_FIM))))
            .andExpect(jsonPath("$.[*].dataInsertMov").value(hasItem(sameInstant(DEFAULT_DATA_INSERT_MOV))))
            .andExpect(jsonPath("$.[*].idCorporativo").value(hasItem(DEFAULT_ID_CORPORATIVO)))
            .andExpect(jsonPath("$.[*].dataGerLeg").value(hasItem(sameInstant(DEFAULT_DATA_GER_LEG))))
            .andExpect(jsonPath("$.[*].procedenciaInfo").value(hasItem(DEFAULT_PROCEDENCIA_INFO)))
            .andExpect(jsonPath("$.[*].codProdSuframa").value(hasItem(DEFAULT_COD_PROD_SUFRAMA)))
            .andExpect(jsonPath("$.[*].pxExpTipoins").value(hasItem(DEFAULT_PX_EXP_TIPOINS)))
            .andExpect(jsonPath("$.[*].tipoProdSuframa").value(hasItem(DEFAULT_TIPO_PROD_SUFRAMA)))
            .andExpect(jsonPath("$.[*].idDetalheSuframa").value(hasItem(DEFAULT_ID_DETALHE_SUFRAMA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorUnitarioReal").value(hasItem(DEFAULT_VALOR_UNITARIO_REAL.doubleValue())))
            .andExpect(jsonPath("$.[*].necessitaRevisaoPexpam").value(hasItem(DEFAULT_NECESSITA_REVISAO_PEXPAM)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.doubleValue())))
            .andExpect(jsonPath("$.[*].pxModeloPadrao").value(hasItem(DEFAULT_PX_MODELO_PADRAO.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField4").value(hasItem(DEFAULT_FLEX_FIELD_4)))
            .andExpect(jsonPath("$.[*].flexField5").value(hasItem(DEFAULT_FLEX_FIELD_5)))
            .andExpect(jsonPath("$.[*].flexField6").value(hasItem(DEFAULT_FLEX_FIELD_6)))
            .andExpect(jsonPath("$.[*].flexField7").value(hasItem(DEFAULT_FLEX_FIELD_7)))
            .andExpect(jsonPath("$.[*].flexField8").value(hasItem(DEFAULT_FLEX_FIELD_8)))
            .andExpect(jsonPath("$.[*].flexField9").value(hasItem(DEFAULT_FLEX_FIELD_9)))
            .andExpect(jsonPath("$.[*].flexField10").value(hasItem(DEFAULT_FLEX_FIELD_10)))
            .andExpect(jsonPath("$.[*].flexField11").value(hasItem(DEFAULT_FLEX_FIELD_11)))
            .andExpect(jsonPath("$.[*].pisCofinsTipoAplic").value(hasItem(DEFAULT_PIS_COFINS_TIPO_APLIC)))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS.doubleValue())))
            .andExpect(jsonPath("$.[*].cofins").value(hasItem(DEFAULT_COFINS.doubleValue())))
            .andExpect(jsonPath("$.[*].pisCofinsRedBase").value(hasItem(DEFAULT_PIS_COFINS_RED_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].modeloProdSuframa").value(hasItem(DEFAULT_MODELO_PROD_SUFRAMA)))
            .andExpect(jsonPath("$.[*].codSiscomexUnidadeNcm").value(hasItem(DEFAULT_COD_SISCOMEX_UNIDADE_NCM)))
            .andExpect(jsonPath("$.[*].partNumberCliente").value(hasItem(DEFAULT_PART_NUMBER_CLIENTE)))
            .andExpect(jsonPath("$.[*].superficieUnitaria").value(hasItem(DEFAULT_SUPERFICIE_UNITARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].localEstoque").value(hasItem(DEFAULT_LOCAL_ESTOQUE)))
            .andExpect(jsonPath("$.[*].codUnidadeSuperficie").value(hasItem(DEFAULT_COD_UNIDADE_SUPERFICIE)))
            .andExpect(jsonPath("$.[*].rateioProdutoAcabado").value(hasItem(DEFAULT_RATEIO_PRODUTO_ACABADO)))
            .andExpect(jsonPath("$.[*].pisCofinsCodUnEspec").value(hasItem(DEFAULT_PIS_COFINS_COD_UN_ESPEC)))
            .andExpect(jsonPath("$.[*].recuperaImpostos").value(hasItem(DEFAULT_RECUPERA_IMPOSTOS)))
            .andExpect(jsonPath("$.[*].flagNoRaf").value(hasItem(DEFAULT_FLAG_NO_RAF)))
            .andExpect(jsonPath("$.[*].notaComplTipi").value(hasItem(DEFAULT_NOTA_COMPL_TIPI)))
            .andExpect(jsonPath("$.[*].ipiReduzido").value(hasItem(DEFAULT_IPI_REDUZIDO.doubleValue())))
            .andExpect(jsonPath("$.[*].sujeitoLote").value(hasItem(DEFAULT_SUJEITO_LOTE)))
            .andExpect(jsonPath("$.[*].marcaComercial").value(hasItem(DEFAULT_MARCA_COMERCIAL)))
            .andExpect(jsonPath("$.[*].tipoEmbalagem").value(hasItem(DEFAULT_TIPO_EMBALAGEM)))
            .andExpect(jsonPath("$.[*].numLiberacaoBrasilia").value(hasItem(DEFAULT_NUM_LIBERACAO_BRASILIA)))
            .andExpect(jsonPath("$.[*].temperaturaConservacao").value(hasItem(DEFAULT_TEMPERATURA_CONSERVACAO)))
            .andExpect(jsonPath("$.[*].umidade").value(hasItem(DEFAULT_UMIDADE)))
            .andExpect(jsonPath("$.[*].luminosidade").value(hasItem(DEFAULT_LUMINOSIDADE)))
            .andExpect(jsonPath("$.[*].embalagemSecundaria").value(hasItem(DEFAULT_EMBALAGEM_SECUNDARIA)))
            .andExpect(jsonPath("$.[*].formaFisica").value(hasItem(DEFAULT_FORMA_FISICA)))
            .andExpect(jsonPath("$.[*].finalidade").value(hasItem(DEFAULT_FINALIDADE)))
            .andExpect(jsonPath("$.[*].itemProdutivoRc").value(hasItem(DEFAULT_ITEM_PRODUTIVO_RC)))
            .andExpect(jsonPath("$.[*].embalagemPrimaria").value(hasItem(DEFAULT_EMBALAGEM_PRIMARIA)))
            .andExpect(jsonPath("$.[*].descricaoAnvisa").value(hasItem(DEFAULT_DESCRICAO_ANVISA)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].codUnidadeMedidaDimensao").value(hasItem(DEFAULT_COD_UNIDADE_MEDIDA_DIMENSAO)))
            .andExpect(jsonPath("$.[*].codMaterial").value(hasItem(DEFAULT_COD_MATERIAL)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO)))
            .andExpect(jsonPath("$.[*].codigoAduana").value(hasItem(DEFAULT_CODIGO_ADUANA)))
            .andExpect(jsonPath("$.[*].classeRisco").value(hasItem(DEFAULT_CLASSE_RISCO)))
            .andExpect(jsonPath("$.[*].codRisco").value(hasItem(DEFAULT_COD_RISCO)))
            .andExpect(jsonPath("$.[*].flexField12").value(hasItem(DEFAULT_FLEX_FIELD_12)))
            .andExpect(jsonPath("$.[*].flexField13").value(hasItem(DEFAULT_FLEX_FIELD_13)))
            .andExpect(jsonPath("$.[*].flexField1Number").value(hasItem(DEFAULT_FLEX_FIELD_1_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField2Number").value(hasItem(DEFAULT_FLEX_FIELD_2_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField3Number").value(hasItem(DEFAULT_FLEX_FIELD_3_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField4Number").value(hasItem(DEFAULT_FLEX_FIELD_4_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexField5Number").value(hasItem(DEFAULT_FLEX_FIELD_5_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].statusScansys").value(hasItem(DEFAULT_STATUS_SCANSYS)))
            .andExpect(jsonPath("$.[*].codEstruturaAtual").value(hasItem(DEFAULT_COD_ESTRUTURA_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].percTolerancia").value(hasItem(DEFAULT_PERC_TOLERANCIA)))
            .andExpect(jsonPath("$.[*].pisEspecifico").value(hasItem(DEFAULT_PIS_ESPECIFICO)))
            .andExpect(jsonPath("$.[*].cofinsEspecifico").value(hasItem(DEFAULT_COFINS_ESPECIFICO)))
            .andExpect(jsonPath("$.[*].flexField14").value(hasItem(DEFAULT_FLEX_FIELD_14)))
            .andExpect(jsonPath("$.[*].flexField15").value(hasItem(DEFAULT_FLEX_FIELD_15)))
            .andExpect(jsonPath("$.[*].flexField16").value(hasItem(DEFAULT_FLEX_FIELD_16)))
            .andExpect(jsonPath("$.[*].flexField17").value(hasItem(DEFAULT_FLEX_FIELD_17)))
            .andExpect(jsonPath("$.[*].flexField18").value(hasItem(DEFAULT_FLEX_FIELD_18)))
            .andExpect(jsonPath("$.[*].flexField19").value(hasItem(DEFAULT_FLEX_FIELD_19)))
            .andExpect(jsonPath("$.[*].flexField20").value(hasItem(DEFAULT_FLEX_FIELD_20)))
            .andExpect(jsonPath("$.[*].flexField21").value(hasItem(DEFAULT_FLEX_FIELD_21)))
            .andExpect(jsonPath("$.[*].flexField22").value(hasItem(DEFAULT_FLEX_FIELD_22)))
            .andExpect(jsonPath("$.[*].flexField23").value(hasItem(DEFAULT_FLEX_FIELD_23)))
            .andExpect(jsonPath("$.[*].flexField24").value(hasItem(DEFAULT_FLEX_FIELD_24)))
            .andExpect(jsonPath("$.[*].flexField25").value(hasItem(DEFAULT_FLEX_FIELD_25)))
            .andExpect(jsonPath("$.[*].flexField26").value(hasItem(DEFAULT_FLEX_FIELD_26)))
            .andExpect(jsonPath("$.[*].flexField27").value(hasItem(DEFAULT_FLEX_FIELD_27)))
            .andExpect(jsonPath("$.[*].flexField28").value(hasItem(DEFAULT_FLEX_FIELD_28)))
            .andExpect(jsonPath("$.[*].flexField29").value(hasItem(DEFAULT_FLEX_FIELD_29)))
            .andExpect(jsonPath("$.[*].flexField30").value(hasItem(DEFAULT_FLEX_FIELD_30)))
            .andExpect(jsonPath("$.[*].flexField31").value(hasItem(DEFAULT_FLEX_FIELD_31)))
            .andExpect(jsonPath("$.[*].flexField32").value(hasItem(DEFAULT_FLEX_FIELD_32)))
            .andExpect(jsonPath("$.[*].flexField33").value(hasItem(DEFAULT_FLEX_FIELD_33)))
            .andExpect(jsonPath("$.[*].sCodBarrasGtin").value(hasItem(DEFAULT_S_COD_BARRAS_GTIN)))
            .andExpect(jsonPath("$.[*].nVlrUnitLimiteUsd").value(hasItem(DEFAULT_N_VLR_UNIT_LIMITE_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].nCodProdAnp").value(hasItem(DEFAULT_N_COD_PROD_ANP.doubleValue())))
            .andExpect(jsonPath("$.[*].nCustoProducao").value(hasItem(DEFAULT_N_CUSTO_PRODUCAO.doubleValue())))
            .andExpect(jsonPath("$.[*].sDestino").value(hasItem(DEFAULT_S_DESTINO)))
            .andExpect(jsonPath("$.[*].nPercentualGlp").value(hasItem(DEFAULT_N_PERCENTUAL_GLP.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField1").value(hasItem(DEFAULT_N_LOC_FIELD_1.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField2").value(hasItem(DEFAULT_N_LOC_FIELD_2.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField3").value(hasItem(DEFAULT_N_LOC_FIELD_3.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField4").value(hasItem(DEFAULT_N_LOC_FIELD_4.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField5").value(hasItem(DEFAULT_N_LOC_FIELD_5.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField6").value(hasItem(DEFAULT_N_LOC_FIELD_6.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField7").value(hasItem(DEFAULT_N_LOC_FIELD_7.doubleValue())))
            .andExpect(jsonPath("$.[*].nLocField8").value(hasItem(DEFAULT_N_LOC_FIELD_8.doubleValue())))
            .andExpect(jsonPath("$.[*].sLocField1").value(hasItem(DEFAULT_S_LOC_FIELD_1)))
            .andExpect(jsonPath("$.[*].sLocField2").value(hasItem(DEFAULT_S_LOC_FIELD_2)))
            .andExpect(jsonPath("$.[*].sLocField3").value(hasItem(DEFAULT_S_LOC_FIELD_3)))
            .andExpect(jsonPath("$.[*].sLocField4").value(hasItem(DEFAULT_S_LOC_FIELD_4)))
            .andExpect(jsonPath("$.[*].sLocField5").value(hasItem(DEFAULT_S_LOC_FIELD_5)))
            .andExpect(jsonPath("$.[*].nIdDocOcr").value(hasItem(DEFAULT_N_ID_DOC_OCR.doubleValue())))
            .andExpect(jsonPath("$.[*].sLocField6").value(hasItem(DEFAULT_S_LOC_FIELD_6)))
            .andExpect(jsonPath("$.[*].sLocField7").value(hasItem(DEFAULT_S_LOC_FIELD_7)))
            .andExpect(jsonPath("$.[*].sLocField8").value(hasItem(DEFAULT_S_LOC_FIELD_8)))
            .andExpect(jsonPath("$.[*].sLocField9").value(hasItem(DEFAULT_S_LOC_FIELD_9)))
            .andExpect(jsonPath("$.[*].sLocField10").value(hasItem(DEFAULT_S_LOC_FIELD_10)))
            .andExpect(jsonPath("$.[*].sLocField11").value(hasItem(DEFAULT_S_LOC_FIELD_11)))
            .andExpect(jsonPath("$.[*].sLocField12").value(hasItem(DEFAULT_S_LOC_FIELD_12)))
            .andExpect(jsonPath("$.[*].sLocField13").value(hasItem(DEFAULT_S_LOC_FIELD_13)))
            .andExpect(jsonPath("$.[*].sLocField14").value(hasItem(DEFAULT_S_LOC_FIELD_14)))
            .andExpect(jsonPath("$.[*].sLocField15").value(hasItem(DEFAULT_S_LOC_FIELD_15)))
            .andExpect(jsonPath("$.[*].sCodProdAnvisa").value(hasItem(DEFAULT_S_COD_PROD_ANVISA)))
            .andExpect(jsonPath("$.[*].sDescProdAnp").value(hasItem(DEFAULT_S_DESC_PROD_ANP)))
            .andExpect(jsonPath("$.[*].nPercGlpNac").value(hasItem(DEFAULT_N_PERC_GLP_NAC.doubleValue())))
            .andExpect(jsonPath("$.[*].nPercGlpImp").value(hasItem(DEFAULT_N_PERC_GLP_IMP.doubleValue())))
            .andExpect(jsonPath("$.[*].nValorPartida").value(hasItem(DEFAULT_N_VALOR_PARTIDA.doubleValue())))
            .andExpect(jsonPath("$.[*].sGtinUnidTrib").value(hasItem(DEFAULT_S_GTIN_UNID_TRIB)))
            .andExpect(jsonPath("$.[*].sCodigoModalidade").value(hasItem(DEFAULT_S_CODIGO_MODALIDADE)))
            .andExpect(jsonPath("$.[*].sCodigogpc").value(hasItem(DEFAULT_S_CODIGOGPC)))
            .andExpect(jsonPath("$.[*].sCodigogpcbrick").value(hasItem(DEFAULT_S_CODIGOGPCBRICK)))
            .andExpect(jsonPath("$.[*].sCodigounspsc").value(hasItem(DEFAULT_S_CODIGOUNSPSC)))
            .andExpect(jsonPath("$.[*].sSituacao").value(hasItem(DEFAULT_S_SITUACAO)))
            .andExpect(jsonPath("$.[*].sEnviado").value(hasItem(DEFAULT_S_ENVIADO)))
            .andExpect(jsonPath("$.[*].sMotivoIsencaoAnvisa").value(hasItem(DEFAULT_S_MOTIVO_ISENCAO_ANVISA)))
            .andExpect(jsonPath("$.[*].sIcProntoParaEnvio").value(hasItem(DEFAULT_S_IC_PRONTO_PARA_ENVIO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }
}
