FROM openjdk:15
COPY ./src /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean package -pl "$PROJECT" -am
ENTRYPOINT java -jar $JAR
