# Chapter 6 — Custom Autoconfiguration

Companion snapshot for *Spring Boot on Auto Pilot*. This tree splits the
Chapter 5 code into `ekart-dev-starter` and `ekart-app`. Seeding is registered
through `AutoConfiguration.imports`. H2 remains the database. Security,
Actuator, and profiles are not in this tag.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-06-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

Then call `http://localhost:9090/v1/orders` (no credentials; seeder enabled in
`application.yaml`). Disable with `--ekart.dev.seeder.enabled=false`.

## Topics covered

* Parent POM with sibling modules `ekart-dev-starter` and `ekart-app`
* `SeederProperties` under `ekart.dev.seeder`
* `DevDataProvider`, `DataSeeder`, and `BootstrapAppData` in the starter
* `EkartDevAutoConfiguration` with `@ConditionalOnProperty` and `@ConditionalOnBean`
* `AutoConfiguration.imports` registration
* `EkartDevDataProvider` plus Datafaker 2.7.0 in `ekart-app`
* `scanBasePackages` so `com.ekart.dev` is not component-scanned
* `--debug` CONDITIONS EVALUATION REPORT for enable and disable
* `CustomDevDataProvider` is in the manuscript only — this tag does not include it
