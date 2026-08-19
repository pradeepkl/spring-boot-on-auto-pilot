# Spring Boot on Auto Pilot — companion code

This repository is the chapter-by-chapter companion to *Spring Boot on Auto
Pilot*. `main` is the latest chapter's working tree, not Chapter 1.

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

Return to the latest code with `git checkout main`.
