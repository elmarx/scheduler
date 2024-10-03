FROM gradle:8-jdk21 AS build

# Set the working directory inside the container
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

RUN --mount=type=cache,target=/root/.gradle gradle clean build --no-daemon --build-cache

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/scheduler-0.0.1-SNAPSHOT.jar scheduler.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/scheduler.jar"]
