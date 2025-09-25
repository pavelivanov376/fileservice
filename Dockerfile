# syntax=docker/dockerfile:1.3

### Compile stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /usr/src/app

# Copy pom first for dependency caching
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build JAR
RUN --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests -Dmaven.compiler.debug=true -Dmaven.compiler.debuglevel=lines,vars,source

### Run stage
FROM eclipse-temurin:21-jre-jammy AS app
WORKDIR /app
COPY --from=builder /usr/src/app/target/*.jar app.jar

EXPOSE 80 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
