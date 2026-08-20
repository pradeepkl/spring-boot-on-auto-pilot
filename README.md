# Chapter 12 — Deployment

Companion snapshot for *Spring Boot on Auto Pilot*. This tree peels Chapter 11
packaging and adds a Compose PostgreSQL deployment for `ekart-app` (prod
profile, schema init, image).

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-12-code
```

`main` also holds this latest application tree, plus the book-wide chapter
index. Return to it with `git checkout main`.

## How to run

Use JDK 21 and Docker:

```bash
./mvnw -pl ekart-app -am package
docker build -t ekart-app:0.0.1-SNAPSHOT .
docker compose up
```

HTTP is on port 9090. Use HTTP Basic (`customer@ekart.com` / `admin@ekart.com`,
password `password123`). Tear down with `docker compose down` (omit `-v` to
keep the named volume).

## Topics covered

* `application-prod.yaml` env-var datasource and `ddl-auto: validate`
* Docker Compose `postgres` + `ekart-app` with `service_healthy`
* Schema init matching `UserAccount` / `Order` / `LineItem`
* Actuator health with the PostgreSQL contributor
* Volume persistence across container recreate
* Graceful Tomcat shutdown on SIGTERM
