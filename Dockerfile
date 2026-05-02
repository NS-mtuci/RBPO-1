FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY --chown=nonroot:nonroot ${JAR_FILE} /app/app.jar

EXPOSE 8080

USER nonroot

ENTRYPOINT ["java", "-jar", "/app/app.jar"]