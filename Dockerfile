FROM openjdk:11.0.15-oracle AS MAVEN_BUILD

MAINTAINER NguyenThach

COPY pom.xml /build/
COPY src /build/src/
COPY mvnw /build/
COPY .mvn /build/.mvn/

WORKDIR /build/
RUN ./mvnw package

FROM openjdk:11.0.15-oracle
WORKDIR /app
VOLUME ["/logs"]
EXPOSE 8080
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]