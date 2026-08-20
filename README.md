# Chapter 10 — Context Slice Testing

Companion snapshot for *Spring Boot on Auto Pilot*. This tree adds web and JPA
slice tests, `findByEmail`, a `test` profile used only by
`TestProfileConfigurationTests`, and one `RANDOM_PORT` HTTP test.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-10-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am test
```

## Topics covered

* Same autoconfiguration engine for tests; slice vs full conditions reports
* `@WebMvcTest` + `@MockitoBean` on `OrderRestController` (`/v1/orders`)
* `@DataJpaTest` + `findByEmail` (orders require an owner)
* `@SpringBootTest` `contextLoads` and `RANDOM_PORT` real HTTP
* `@ActiveProfiles("test")` without breaking default `dev` tests
* Security on the production chain: anonymous 401, customer 403, admin 200
