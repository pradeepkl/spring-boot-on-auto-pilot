# Chapter 2 — The Conditions Engine

Companion snapshot for *Spring Boot on Auto Pilot*. This tree keeps
`com.example.demo` and adds `Student`, `StudentConfig`, web and JPA starters,
H2, and `application.yaml`.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-02-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--loadStudent=true
./mvnw spring-boot:run -Dspring-boot.run.arguments=--debug
./mvnw dependency:tree
```

## Topics covered

* Autoconfiguration as a conditions engine, not magic
* `@ConditionalOnProperty`, `@ConditionalOnBean`, `@ConditionalOnMissingBean`, and `@ConditionalOnClass`
* Stacked conditions are always AND
* Filtering the bean registry to `student*` beans
* `loadStudent` and `server.port` in `application.yaml`
* `spring-boot-starter-web` unlocking Tomcat, MVC, and Jackson
* `spring-boot-starter-data-jpa` plus H2 unlocking DataSource, Hibernate, and transactions
* Reading `dependency:tree` and the conditions report after each classpath change
