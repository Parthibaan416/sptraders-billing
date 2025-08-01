# sptraders-billing/Dockerfile

# Use JDK base image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy JAR file into container
COPY sptraders-billing-1.0.0.jar app.jar

# Expose port
EXPOSE 7779

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]