# Chapter 7 — Spring Security

Companion snapshot for *Spring Boot on Auto Pilot*. This tree adds
`spring-boot-starter-security` to `ekart-app`. H2 remains the database.
Actuator, PostgreSQL, and `@Profile` are not in this tag.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-07-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am test
./mvnw -pl ekart-app spring-boot:run
./mvnw -pl ekart-app spring-boot:run -Dspring-boot.run.arguments=--debug
```

Unauthenticated `GET /v1/orders` returns 401. After seeding, use HTTP Basic:

* `admin@ekart.com` / `password123` for the collection
* `customer@ekart.com` / `password123` for owned orders
* `othercustomer@ekart.com` / `password123` is 403 on another customer's order

## Topics covered

* Default security from the starter (generated password, 401, `--debug` matches)
* `UserAccount`, `Role`, `UserRepository`, and `Order.owner`
* `AppUserDetailsService`, `DaoAuthenticationProvider`, custom `SecurityFilterChain`
* `OrderSecurity` and `@PreAuthorize` on `OrderService`
* User seeding in `EkartDevDataProvider` (starter stays free of Security types)
* 401 / 403 / 200 verification without Actuator
