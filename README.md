# Microservices Demo using Spring Boot 

Example of a microservices application using best practices:
- Service Discovery (Eureka)
- Config Server (Spring Cloud Config Server)
- Identity Server (Spring Security)
- Services communication using messaging (Spring boot AMQP)  
- Api Gateway (Spring Cloud Gateway)

## Instructions

To execute the application, you must have access to a RabbitMQ broker. If you don't have 
one, you can run use a docker image. For example:

docker run -d --name my-rabbit -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15672:15672 rabbitmq:3-management

All services are using the config server, so, you must have a repository for it. For testing
just follow the next steps:
- Create a folder
- Add an application.properties file with a custom property
- Convert the folder to a repository (git init)
- Add the file to the repository (git add ./application.properties)
- Commit the change (git commit -m "Initial commit")

Clone the repository and change the application properties of all projects 
according to your settings. By default, the services are using JPA with H2, so, you
don't need any extra configuration.

The first service to run, must be the Eureka server, then the config server, and then the 
rest of the services.

All services are secured, so, the first step must be the authentication. The authorization
uses a bearer token, that you must use on each call. For example:

curl --location --request POST 'http://localhost:8085/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "user",
"password": "user"
}'

The response will be something like:

{
"token": "my-jwt-token"
}

Then, a call to an endpoint using the gateway:

curl --location --request GET 'http://localhost:8084/api/v1/movies' \
--header 'Authorization: Bearer my-jwt-token'



#### TODO:
- Add a readme for each service
- Dockerize the project
