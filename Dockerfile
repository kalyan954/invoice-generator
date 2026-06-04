# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper and project descriptor first to cache dependency resolution
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make mvnw executable and resolve all dependencies (cached layer unless pom.xml changes)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source and compile
COPY src/ src/
RUN ./mvnw package -DskipTests -B

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre AS runtime

WORKDIR /app

COPY --from=build /app/target/invoice-generator-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

