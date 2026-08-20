# Chapter 3 — The Application Layer

Companion snapshot for *Spring Boot on Auto Pilot*. This tree moves the
application to `com.ekart`, adds `Order` / `LineItem`, repositories,
`OrderService`, and `BootstrapAppData` seeded with Datafaker.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-03-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then open `http://localhost:9090/h2-console` (JDBC URL `jdbc:h2:mem:ekartdb`,
user `sa`, empty password).

## Topics covered

* Moving the scan root to `com.ekart` / `EkartApplication`
* DevTools and Lombok (Lombok excluded from the fat JAR)
* `Order` and `LineItem` JPA mapping, cascade, and owning-side foreign keys
* Spring Data repositories as generated proxies
* `OrderService` constructor injection and `@Transactional` writes
* Datafaker `2.7.0` and `ApplicationReadyEvent` seeding of 10 orders
* H2 console verification of `ORDERS` and `LINE_ITEM`
