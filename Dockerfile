# Stage 1: Build the application
FROM maven:3.9.9 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Set environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=prod

# Create a non-root user for security
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

# Set the working directory
WORKDIR /app

# Create the data directory and set ownership
RUN mkdir -p /app/data && chown appuser:appgroup /app/data

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown appuser:appgroup app.jar

# Switch to the non-root user
USER appuser

# Expose port 8080
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
