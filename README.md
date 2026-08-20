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

## Chapter 5 — Overriding Defaults

To view the files for Chapter 5, check out the `chapter-05-code` tag (commit
`fe985c1`). That snapshot keeps the Chapter 4 REST API and adds property
tuning, Hibernate/Jackson/WebMvc extension points, and audit columns. HikariCP
and Logback remain the defaults.

```bash
git checkout chapter-05-code
```

You can also check out the commit directly:

```bash
git checkout fe985c18518549306408392ae34eabf53c270fb9
```

Run the Chapter 5 application with JDK 21:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then call `http://localhost:9090/v1/orders` (`orderDate` is `dd-MM-yyyy`).

### Topics covered in Chapter 5

* Property tuning for Hikari, Jackson `non_null`, and Tomcat threads
* `HibernatePropertiesCustomizer` naming strategy and audit interceptor
* `JsonMapperBuilderCustomizer` for `LocalDate` as `dd-MM-yyyy`
* `WebMvcConfigurer` CORS on `/v1/**`
* Audit fields on `Order` / `LineItem` (`@JsonIgnore`, no `UserAccount`)
* Implementation-replacement and `JsonMapper` `@Bean` examples are in the
  manuscript only — this tag keeps starter defaults and camelCase JSON

## Chapter 6 — Custom Autoconfiguration

To view the files for Chapter 6, check out the `chapter-06-code` tag (commit
`0ccac1a`). That snapshot splits the Chapter 5 tree into `ekart-dev-starter`
and `ekart-app`. Seeding is registered through `AutoConfiguration.imports`.
H2 remains the database. Security, Actuator, and profiles are not in this tag.

```bash
git checkout chapter-06-code
```

You can also check out the commit directly:

```bash
git checkout 0ccac1a8e29ff5fc2e93d2e11a6963f7831341c4
```

Run the Chapter 6 application with JDK 21:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

Then call `http://localhost:9090/v1/orders` (no credentials; seeder enabled in
`application.yaml`). Disable with `--ekart.dev.seeder.enabled=false`.

### Topics covered in Chapter 6

* Parent POM with sibling modules `ekart-dev-starter` and `ekart-app`
* `SeederProperties` under `ekart.dev.seeder`
* `DevDataProvider`, `DataSeeder`, and `BootstrapAppData` in the starter
* `EkartDevAutoConfiguration` with `@ConditionalOnProperty` and `@ConditionalOnBean`
* `AutoConfiguration.imports` registration
* `EkartDevDataProvider` plus Datafaker 2.7.0 in `ekart-app`
* `scanBasePackages` so `com.ekart.dev` is not component-scanned
* `--debug` CONDITIONS EVALUATION REPORT for enable and disable
* `CustomDevDataProvider` is in the manuscript only — this tag does not include it

## Chapter 7 — Spring Security

To view the files for Chapter 7, check out the `chapter-07-code` tag (commit
`4942304`). That snapshot adds `spring-boot-starter-security` to `ekart-app`.
H2 remains the database. Actuator, PostgreSQL, and `@Profile` are not in this
tag.

```bash
git checkout chapter-07-code
```

You can also check out the commit directly:

```bash
git checkout 4942304bfd29a2ac955bc9a06bb687cfe00b8adc
```

Run the Chapter 7 application with JDK 21:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

Unauthenticated `GET /v1/orders` returns 401. After seeding, use HTTP Basic:

* `admin@ekart.com` / `password123` for the collection
* `customer@ekart.com` / `password123` for owned orders
* `othercustomer@ekart.com` / `password123` is 403 on another customer's order

### Topics covered in Chapter 7

* Default security from the starter (generated password, 401, `--debug` matches)
* `UserAccount`, `Role`, `UserRepository`, and `Order.owner`
* `AppUserDetailsService`, `DaoAuthenticationProvider`, custom `SecurityFilterChain`
* `OrderSecurity` and `@PreAuthorize` on `OrderService`
* User seeding in `EkartDevDataProvider` (starter stays free of Security types)
* 401 / 403 / 200 verification without Actuator

## Chapter 8 — Profiles and Configuration

To view the files for Chapter 8, check out the `chapter-08-code` tag (commit
`97c905b`). That snapshot splits YAML into shared, `dev`, and `prod` files.
`@Profile("dev")` gates `EkartDevDataProvider`. Two `SecurityFilterChain`
beans select permit-all HTTP versus the Chapter 7 authenticated chain.
PostgreSQL is a runtime dependency. Actuator is not in this tag.

```bash
git checkout chapter-08-code
```

You can also check out the commit directly:

```bash
git checkout 97c905ba60552b09ddd85fc805688c59d6ece4f6
```

Run the Chapter 8 application with JDK 21:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
```

Default `dev` uses H2 and seeds data. `prod` expects PostgreSQL at
`localhost:5432` (see Appendix C). Tests override the prod datasource to H2.

### Topics covered in Chapter 8

* Shared `application.yaml` plus `application-dev.yaml` / `application-prod.yaml`
* `spring.profiles.default: dev` and command-line profile activation
* Property-source precedence (`--ekart.dev.seeder.order-count=3`)
* `@Profile("dev")` on `EkartDevDataProvider`; starter seeder stays conditional
* `productionFilterChain` and `devPermitAllFilterChain` with `ekart.security.require-auth`
* PostgreSQL runtime driver; no Actuator (`/actuator/*` is Chapter 9)
* Maven profiles vs Spring profiles (Datafaker stays a compile dependency)

## Chapter 9 — Actuator

To view the files for Chapter 9, check out the `chapter-09-code` tag (commit
`7658401`). That snapshot adds `spring-boot-starter-actuator` on `ekart-app`,
graceful shutdown, probes, a development health indicator, `orders.created`,
and Actuator matchers on `productionFilterChain` only. Default `dev` HTTP
stays permit-all. Do not merge this tag onto `main`.

```bash
git checkout chapter-09-code
```

You can also check out the commit directly:

```bash
git checkout 7658401728e5990c3242f7904216ff63fc8b306a
```

Run the Chapter 9 application with JDK 21:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

`GET /actuator` lists the exposed endpoints. `management.server.port` is not
bound; management shares port 9090.

### Topics covered in Chapter 9

* `spring-boot-starter-actuator` on `ekart-app`
* Endpoint registration versus web exposure (`health` is the Boot 4.1.0 default)
* `BufferingApplicationStartup(2048)` in `EkartApplication.main()`
* Liveness and readiness probes; `@Profile("dev")` `/v1/state` toggles
* `SeededOrdersHealthIndicator` (`org.springframework.boot.health.contributor`)
* Micrometer `orders.created` after a successful `OrderService.saveOrder`
* `/actuator/loggers` for `com.ekart.service.OrderService`
* Production-chain Actuator security; health public, other routes `ADMIN`

## Chapter 10 — Context Slice Testing

To view the files for Chapter 10, check out the `chapter-10-code` tag (commit
`bd7aade`). That snapshot adds web and JPA slice tests, `findByEmail`, a `test`
profile used only by `TestProfileConfigurationTests`, and one `RANDOM_PORT`
HTTP test. Do not merge this tag onto `main`.

```bash
git checkout chapter-10-code
```

You can also check out the commit directly:

```bash
git checkout bd7aadea3e8318c64d50021c715ed4b063d4f6cc
```

Run the Chapter 10 tests with JDK 21:

```bash
./mvnw -pl ekart-app -am test
```

### Topics covered in Chapter 10

* Same autoconfiguration engine for tests; slice vs full conditions reports
* `@WebMvcTest` + `@MockitoBean` on `OrderRestController` (`/v1/orders`)
* `@DataJpaTest` + `findByEmail` (orders require an owner)
* `@SpringBootTest` `contextLoads` and `RANDOM_PORT` real HTTP
* `@ActiveProfiles("test")` without breaking default `dev` tests
* Security on the production chain: anonymous 401, customer 403, admin 200

## Chapter 11 — Packaging

To view the files for Chapter 11, check out the `chapter-11-code` tag (commit
`afdb56d`). That snapshot keeps the Chapter 10 tests and turns on layered
executable JAR packaging plus a Buildpack image name on `ekart-app`. Do not
merge this tag onto `main`.

```bash
git checkout chapter-11-code
```

You can also check out the commit directly:

```bash
git checkout afdb56d0550d83be262654728f296122f0e0b598
```

Package and inspect the executable JAR with JDK 21:

```bash
./mvnw -pl ekart-app -am package
java -jar ekart-app/target/ekart-app-0.0.1-SNAPSHOT.jar
java -Djarmode=tools -jar ekart-app/target/ekart-app-0.0.1-SNAPSHOT.jar list-layers
```

`./mvnw -pl ekart-app spring-boot:build-image` needs a Docker daemon. It was
not run on the Chapter 11 workstation.

### Topics covered in Chapter 11

* Spring Boot Maven Plugin `repackage` and `BOOT-INF` layout
* Manifest `Main-Class` (`JarLauncher`) and `Start-Class` (`EkartApplication`)
* Layered JARs (`BOOT-INF/layers.idx`; `jarmode=tools`)
* Convention-first Buildpack image name `ekart-app:${project.version}`
* Build once, configure per environment (`--server.port`)

Return to the latest code with `git checkout main`.
