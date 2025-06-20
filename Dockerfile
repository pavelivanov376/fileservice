# Compile stage
FROM maven:sapmachine AS builder
COPY . /usr/src/build
WORKDIR /usr/src/build
RUN mvn verify -DskipTests -Dmaven.compiler.debug=true -Dmaven.compiler.debuglevel=lines,vars,source


# Run stage
FROM sapmachine:lts AS app
COPY --from=builder /usr/src/build/target/*.jar app.jar

# Expose application port and debug port
EXPOSE 80 5005

# Enable remote debugging
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app.jar"]