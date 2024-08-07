from eclipse-temurin:17-jre-alpine
workdir /app
copy target/*.jar app.jar
arg db
arg user
arg password
env spring_datasource_url=jdbc:postgresql://postgres:5432/${db}
env spring_datasource_username=${user}
env spring_datasource_password=${password}
expose 8080
ENTRYPOINT ["java", "-jar", "app.jar"]