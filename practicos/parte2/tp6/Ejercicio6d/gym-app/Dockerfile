FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace/app

# Copy project files and build the application
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package \
 && mv target/*.jar target/app.jar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /workspace/app/target/app.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
