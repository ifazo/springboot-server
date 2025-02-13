# Use a generic OpenJDK image and install Maven
FROM eclipse-temurin:23 AS build
WORKDIR /app

# Install Maven manually
RUN apt-get update && apt-get install -y maven

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a minimal JDK runtime for deployment
FROM eclipse-temurin:23-jre
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
