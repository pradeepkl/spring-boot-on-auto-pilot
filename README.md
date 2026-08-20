# Chapter 9 — Actuator

Companion snapshot for *Spring Boot on Auto Pilot*. This tree adds
`spring-boot-starter-actuator` on `ekart-app`, graceful shutdown, probes, a
development health indicator, `orders.created`, and Actuator matchers on
`productionFilterChain` only. Default `dev` HTTP stays permit-all.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout tags/chapter-09-code
```

`git checkout chapter-09-code` may resolve to a local branch of the same name.
Prefer `tags/chapter-09-code`. Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

`GET /actuator` lists the exposed endpoints. `management.server.port` is not
bound; management shares port 9090.

## Topics covered

* `spring-boot-starter-actuator` on `ekart-app`
* Endpoint registration versus web exposure (`health` is the Boot 4.1.0 default)
* `BufferingApplicationStartup(2048)` in `EkartApplication.main()`
* Liveness and readiness probes; `@Profile("dev")` `/v1/state` toggles
* `SeededOrdersHealthIndicator` (`org.springframework.boot.health.contributor`)
* Micrometer `orders.created` after a successful `OrderService.saveOrder`
* `/actuator/loggers` for `com.ekart.service.OrderService`
* Production-chain Actuator security; health public, other routes `ADMIN`
