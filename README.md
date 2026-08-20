# Chapter 5 — Overriding Defaults

Companion snapshot for *Spring Boot on Auto Pilot*. This tree keeps the
Chapter 4 REST API and adds property tuning, Hibernate/Jackson/WebMvc
extension points, and audit columns. HikariCP and Logback remain the defaults.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-05-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw test
./mvnw spring-boot:run
```

Then call `http://localhost:9090/v1/orders` (`orderDate` is `dd-MM-yyyy`).

## Topics covered

* Property tuning for Hikari, Jackson `non_null`, and Tomcat threads
* `HibernatePropertiesCustomizer` naming strategy and audit interceptor
* `JsonMapperBuilderCustomizer` for `LocalDate` as `dd-MM-yyyy`
* `WebMvcConfigurer` CORS on `/v1/**`
* Audit fields on `Order` / `LineItem` (`@JsonIgnore`, no `UserAccount`)
* Implementation-replacement and `JsonMapper` `@Bean` examples are in the
  manuscript only — this tag keeps starter defaults and camelCase JSON
