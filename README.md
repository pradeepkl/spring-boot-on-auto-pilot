# Spring Boot on Auto Pilot — companion code

This repository is the chapter-by-chapter companion to *Spring Boot on Auto
Pilot*. The sample is a Spring Boot **4.1.0** / **Java 21** Maven project
(wrapper included). `main` is the finished tree at the end of Chapter 12
(Compose + PostgreSQL deployment). Earlier chapters are **tags**, not branches
you need to merge yourself.

## How to use this GitHub repo

1. Clone the repository and stay on `main` if you want the latest application.

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
```

2. Use JDK 21. Run tests or the app with the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
```

Chapter 12 on `main` is meant to run with Docker Compose (HTTP on host port
**9090**), not only `spring-boot:run`:

```bash
./mvnw -pl ekart-app -am package
docker build -t ekart-app:0.0.1-SNAPSHOT .
docker compose up
```

3. To study a single chapter’s snapshot, check out that chapter’s tag. The
   working tree then matches the code as of that chapter. Return with
   `git checkout main`.

```bash
git checkout chapter-01-code
```

The table below lists every chapter tag. Open a tag link on GitHub to browse
that snapshot without checking it out locally.

## Chapters

| Chapter | Title | What this chapter adds | Tag |
|--------:|-------|------------------------|-----|
| 1 | The Spring Boot Contract | Inspect-first Boot 4 project: bean registry and conditions report, not hand-wired XML. | [chapter-01-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-01-code) |
| 2 | The Conditions Engine | Autoconfiguration as stacked conditions; web/JPA starters and `application.yaml`. | [chapter-02-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-02-code) |
| 3 | The Application Layer | eKart domain: entities, repositories, transactions, Datafaker seeding. | [chapter-03-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-03-code) |
| 4 | The Web Layer | REST orders API, validation, `@ControllerAdvice`, HTTP evidence. | [chapter-04-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-04-code) |
| 5 | Overriding Defaults | Escalation ladder: properties, customizers, and when to own a bean. | [chapter-05-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-05-code) |
| 6 | Custom Autoconfiguration | `ekart-dev-starter` module and `AutoConfiguration.imports`. | [chapter-06-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-06-code) |
| 7 | Spring Security | HTTP + method security on eKart; default chain replaced with domain identity. | [chapter-07-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-07-code) |
| 8 | Profiles and Configuration | Profiles, typed properties, environment-gated beans. | [chapter-08-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-08-code) |
| 9 | Actuator | Runtime endpoints, health, and observability on the running app. | [chapter-09-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-09-code) |
| 10 | Context Slice Testing | Smallest sufficient test context: slice, JPA, and HTTP tests. | [chapter-10-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-10-code) |
| 11 | Packaging | Layered executable JAR and Buildpack image name on `ekart-app`. | [chapter-11-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-11-code) |
| 12 | Deployment | Compose + PostgreSQL prod-shaped run: health, persistence, graceful shutdown. | [chapter-12-code](https://github.com/pradeepkl/spring-boot-on-auto-pilot/tree/chapter-12-code) |

## Book website

The book itself lives at [https://spring-boot.classpath.in](https://spring-boot.classpath.in).
Use that site for the manuscript and reading path. This GitHub repository is
only the chapter code companion.
