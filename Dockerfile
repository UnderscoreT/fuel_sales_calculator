# Use OpenJDK 24 as base image
FROM eclipse-temurin:24-jdk

# Set working directory
WORKDIR /app

# Copy your built JAR into the container
COPY target/fuelsalesapp-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Optional: set JVM options
ENV JAVA_OPTS=""

# Start the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
