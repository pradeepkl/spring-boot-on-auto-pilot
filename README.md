# Chapter 11 — Packaging

Companion snapshot for *Spring Boot on Auto Pilot*. This tree keeps the
Chapter 10 tests and turns on layered executable JAR packaging plus a
Buildpack image name on `ekart-app`.

## How to check out this chapter

```bash
git clone git@github.com:pradeepkl/spring-boot-on-auto-pilot.git
cd spring-boot-on-auto-pilot
git checkout chapter-11-code
```

Return to the latest tree with `git checkout main`.

## How to run

Use JDK 21 and the Maven wrapper:

```bash
./mvnw -pl ekart-app -am package
java -jar ekart-app/target/ekart-app-0.0.1-SNAPSHOT.jar
java -Djarmode=tools -jar ekart-app/target/ekart-app-0.0.1-SNAPSHOT.jar list-layers
```

`./mvnw -pl ekart-app spring-boot:build-image` needs a Docker daemon.

## Topics covered

* Spring Boot Maven Plugin `repackage` and `BOOT-INF` layout
* Manifest `Main-Class` (`JarLauncher`) and `Start-Class` (`EkartApplication`)
* Layered JARs (`BOOT-INF/layers.idx`; `jarmode=tools`)
* Convention-first Buildpack image name `ekart-app:${project.version}`
* Build once, configure per environment (`--server.port`)
