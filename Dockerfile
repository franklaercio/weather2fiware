FROM gradle:8.11-jdk21 AS builder
WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM eclipse-temurin:21 as jre-build
WORKDIR /app

EXPOSE 8080

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]