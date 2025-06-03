# Gunakan image Java 17 LTS
FROM eclipse-temurin:17-jdk

# Argumen untuk file JAR
ARG JAR_FILE=target/*.jar

# Salin JAR ke dalam container
COPY ${JAR_FILE} app.jar

# Jalankan Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]