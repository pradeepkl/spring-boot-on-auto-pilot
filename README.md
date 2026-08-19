# Spring Boot on Auto Pilot — companion code

This repository is the chapter-by-chapter companion to *Spring Boot on Auto
Pilot*. `main` is the latest chapter's working tree, not an earlier chapter
snapshot. Check out the chapter tags below to view the code for that chapter.

## Chapter 1 — The Spring Boot Contract

To view the files for Chapter 1, check out the `chapter-01-code` tag (commit
`c1c6161`). That snapshot is a non-web Spring Boot 4.1.0 / Java 21 project with
`DemoApplication` and an `AppConfig` `CommandLineRunner` that dumps the bean
registry.

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-01-code
```

You can also check out the commit directly:

```bash
git checkout c1c616102f6d8e838a55c4d42003b61145a26f3b
```

Run the Chapter 1 application with JDK 21:

```bash
./mvnw test
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--debug
```

### Topics covered in Chapter 1

* Why Spring Boot exists and the infrastructure tax it removes
* Convention over configuration: declared capabilities imply infrastructure
* Opinionated defaults, and when a narrow change is enough
* Inspect-first working order versus configure-first Spring Framework habits
* Generated project layout, Maven wrapper, parent BOM, and starters
* `@SpringBootApplication` as configuration source, auto-configuration, and component scan
* The `ApplicationContext` as the runtime container
* Reading the bean registry with `CommandLineRunner`
* Reading the conditions evaluation report with `--debug`

## Chapter 2 — The Conditions Engine

To view the files for Chapter 2, check out the `chapter-02-code` tag (commit
`599132f`). That snapshot keeps `com.example.demo` and adds `Student`,
`StudentConfig`, web and JPA starters, H2, and `application.yaml`.

```bash
git checkout chapter-02-code
```

You can also check out the commit directly:

```bash
git checkout 599132fb1ff8d7cd40e0766a2dc0972fe5293de5
```

Run the Chapter 2 application with JDK 21:

```bash
./mvnw test
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--loadStudent=true
./mvnw spring-boot:run -Dspring-boot.run.arguments=--debug
./mvnw dependency:tree
```

### Topics covered in Chapter 2

* Autoconfiguration as a conditions engine, not magic
* `@ConditionalOnProperty`, `@ConditionalOnBean`, `@ConditionalOnMissingBean`, and `@ConditionalOnClass`
* Stacked conditions are always AND
* Filtering the bean registry to `student*` beans
* `loadStudent` and `server.port` in `application.yaml`
* `spring-boot-starter-web` unlocking Tomcat, MVC, and Jackson
* `spring-boot-starter-data-jpa` plus H2 unlocking DataSource, Hibernate, and transactions
* Reading `dependency:tree` and the conditions report after each classpath change

## Chapter 3 — The Application Layer

To view the files for Chapter 3, check out the `chapter-03-code` tag (commit
`a237c44`). That snapshot moves the application to `com.ekart`, adds `Order` /
`LineItem`, repositories, `OrderService`, and `BootstrapAppData` seeded with
Datafaker.

```bash
git checkout chapter-03-code
```

You can also check out the commit directly:

```bash
git checkout a237c448191e038eabd0d3ec743ac13b90d5df5a
```

Run the Chapter 3 application with JDK 21:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then open `http://localhost:9090/h2-console` (JDBC URL `jdbc:h2:mem:ekartdb`,
user `sa`, empty password).

### Topics covered in Chapter 3

* Moving the scan root to `com.ekart` / `EkartApplication`
* DevTools and Lombok (Lombok excluded from the fat JAR)
* `Order` and `LineItem` JPA mapping, cascade, and owning-side foreign keys
* Spring Data repositories as generated proxies
* `OrderService` constructor injection and `@Transactional` writes
* Datafaker `2.7.0` and `ApplicationReadyEvent` seeding of 10 orders
* H2 console verification of `ORDERS` and `LINE_ITEM`

## Chapter 4 — The Web Layer

To view the files for Chapter 4, check out the `chapter-04-code` tag (commit
`2a4dbd7`). That snapshot keeps the Chapter 3 eKart domain and adds
`OrderRestController`, validation DTOs, and `GlobalExceptionHandler`.

```bash
git checkout chapter-04-code
```

You can also check out the commit directly:

```bash
git checkout 2a4dbd75bf2fa35781798db65c0935868e824c84
```

Run the Chapter 4 application with JDK 21:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then call `http://localhost:9090/v1/orders`.

### Topics covered in Chapter 4

* `OrderRestController` mappings under `/v1/orders`
* `CreateOrderRequest` / `UpdateOrderRequest` with `@Valid`
* `spring-boot-starter-validation` (Hibernate Validator is not transitive from the web starter on Boot 4.1.0)
* `GlobalExceptionHandler` returning `ErrorResponse` for `404`
* TRACE mappings on `RequestMappingHandlerMapping`
* curl success and not-found paths on port 9090

Return to the latest code with `git checkout main`.
