# Chapter 8 — Profiles and Configuration

Companion snapshot for *Spring Boot on Auto Pilot*. This tree splits YAML into
shared, `dev`, and `prod` files. `@Profile("dev")` gates `EkartDevDataProvider`.
Two `SecurityFilterChain` beans select permit-all HTTP versus the Chapter 7
authenticated chain. PostgreSQL is a runtime dependency. Actuator is not in
this tag.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-08-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
```

Default `dev` uses H2 and seeds data. `prod` expects PostgreSQL at
`localhost:5432`. Tests override the prod datasource to H2.

## Topics covered

* Shared `application.yaml` plus `application-dev.yaml` / `application-prod.yaml`
* `spring.profiles.default: dev` and command-line profile activation
* Property-source precedence (`--ekart.dev.seeder.order-count=3`)
* `@Profile("dev")` on `EkartDevDataProvider`; starter seeder stays conditional
* `productionFilterChain` and `devPermitAllFilterChain` with `ekart.security.require-auth`
* PostgreSQL runtime driver; no Actuator (`/actuator/*` is Chapter 9)
* Maven profiles vs Spring profiles (Datafaker stays a compile dependency)
