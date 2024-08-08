FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
ARG db
ARG user
ARG password
ENV spring_datasource_url=jdbc:postgresql://postgres:5432/${db}
ENV spring_datasource_username=${user}
ENV spring_datasource_password=${password}
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
