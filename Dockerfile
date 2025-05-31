FROM gradle:8.11-jdk21 AS builder
WORKDIR /app

COPY . .

RUN gradle clean bootJar -x test

FROM eclipse-temurin:21-alpine as jre-build

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]