FROM openjdk:15
COPY ./src /app
WORKDIR /app
RUN ./mvnw clean package -pl "$PROJECT" -am
ENTRYPOINT java -jar $JAR
