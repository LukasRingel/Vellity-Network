FROM eclipse-temurin:17
LABEL authors="lvkaas"

# Set the working directory
WORKDIR /

# Copy the project files to the working directory
COPY components/friends-service/build/libs/friends-service-1.0-SNAPSHOT.jar /app/friends-service.jar

# Expose the port
EXPOSE 8080

# Set the entrypoint command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/friends-service.jar"]
