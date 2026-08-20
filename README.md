# Chapter 1 — The Spring Boot Contract

Companion snapshot for *Spring Boot on Auto Pilot*. This tree is a non-web
Spring Boot 4.1.0 / Java 21 project with `DemoApplication` and an `AppConfig`
`CommandLineRunner` that dumps the bean registry.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-01-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--debug
```

## Topics covered

* Why Spring Boot exists and the infrastructure tax it removes
* Convention over configuration: declared capabilities imply infrastructure
* Opinionated defaults, and when a narrow change is enough
* Inspect-first working order versus configure-first Spring Framework habits
* Generated project layout, Maven wrapper, parent BOM, and starters
* `@SpringBootApplication` as configuration source, auto-configuration, and component scan
* The `ApplicationContext` as the runtime container
* Reading the bean registry with `CommandLineRunner`
* Reading the conditions evaluation report with `--debug`
