# Hike Booking Service

## Scope
This service exposes endpoints to create, view, and delete the bookings to different hikes. 

  * Provides bookings summary day wise
  * Provides separate endpoint to view all the hikes available

## Assumptions
 * For now, the provided 3 hikes in the problem statement are inserted at the startup.
 * Separate endpoint is provided to get all the hikes available


## Technologies & Tools
Java 8, Spring Boot 2.4.0, H2, Maven

## Usage

```bash
mvn package
mvn spring-boot:run

```
## APIs Documentation
[SWAGGER-UI](http://localhost:8080/swagger-ui.html)

http://localhost:8080/swagger-ui.html

## Management API
http://localhost:8080/actuator

http://localhost:8080/h2-console

## TODO
* Exhaustive unit test cases are not provided, though using Integration test cases the whole scope is covered
* Resilience and logging can be improved