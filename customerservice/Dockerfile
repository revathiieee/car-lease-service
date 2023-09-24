FROM amazoncorretto:17.0.7-alpine
COPY . /build
RUN cd build/ && ./mvnw clean package

FROM amazoncorretto:17.0.7-alpine as customer-service
EXPOSE 8091
COPY --from=BUILDER build/target/customerservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]