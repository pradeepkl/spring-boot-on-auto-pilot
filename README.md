# Chapter 4 — The Web Layer

Companion snapshot for *Spring Boot on Auto Pilot*. This tree keeps the
Chapter 3 eKart domain and adds `OrderRestController`, validation DTOs, and
`GlobalExceptionHandler`.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-04-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then call `http://localhost:9090/v1/orders`.

## Topics covered

* `OrderRestController` mappings under `/v1/orders`
* `CreateOrderRequest` / `UpdateOrderRequest` with `@Valid`
* `spring-boot-starter-validation` (Hibernate Validator is not transitive from the web starter on Boot 4.1.0)
* `GlobalExceptionHandler` returning `ErrorResponse` for `404`
* TRACE mappings on `RequestMappingHandlerMapping`
* curl success and not-found paths on port 9090
