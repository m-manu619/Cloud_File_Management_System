# Use official OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the host to the container
COPY target/cloud-file-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set environment variables for AWS credentials and region
# (These should be overridden at runtime for security)
ENV AWS_ACCESS_KEY_ID=
ENV AWS_SECRET_ACCESS_KEY=
ENV AWS_DEFAULT_REGION=

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
