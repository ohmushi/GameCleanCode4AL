FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY pom.xml ./pom.xml
COPY src ./src
RUN mvn clean install


FROM openjdk:17-alpine as run
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


