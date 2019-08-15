FROM openjdk:8u222-jdk
ADD ./target/product-management-0.0.1-SNAPSHOT.jar ./
ADD ./init.sh ./
RUN chmod 755 /init.sh
CMD ["./init.sh"]
