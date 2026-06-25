# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Quarkus microservices study project called **vintageStore**, consisting of two independent services:

- **`rest-book`** — Book service exposing `GET /api/books`
- **`rest-number`** — Number service exposing `GET /api/numbers`

Each service is a standalone Quarkus 3.36.3 application built with Maven (Java 25). They share identical structure and dependency sets: RESTEasy Classic + JSON-B, SmallRye OpenAPI, ArC CDI, and REST-Assured for testing.

## Commands

All commands must be run from within the service directory (`rest-book/` or `rest-number/`).

**Dev mode (live reload):**
```shell
./mvnw quarkus:dev
```
Dev UI is available at `http://localhost:8080/q/dev/` and Swagger UI at `http://localhost:8080/q/swagger-ui/`.

**Build:**
```shell
./mvnw package
./mvnw package -Dquarkus.package.jar.type=uber-jar   # über-jar variant
```

**Run tests:**
```shell
./mvnw test                                           # unit tests only
./mvnw test -Dtest=BookResourceTest                   # single test class
./mvnw verify -Dskip ITs=false                        # include integration tests
```

**Native build (requires GraalVM or Docker):**
```shell
./mvnw package -Dnative
./mvnw package -Dnative -Dquarkus.native.container-build=true  # via container
```

## Architecture

Both services follow the same structure and are fully independent — no shared parent POM or shared library exists at the repo root level.

**Test split:** `@QuarkusTest` tests (unit/dev-mode) live in `*Test.java`; `@QuarkusIntegrationTest` tests (packaged artifact) live in `*IT.java`. The IT class simply extends the test class to reuse assertions. Integration tests are skipped by default (`skipITs=true`) and enabled via the `native` Maven profile.

**OpenAPI:** Both services include `quarkus-smallrye-openapi`, so Swagger UI is available in dev mode without additional configuration.

**Ports:** Both services default to port 8080. Running them simultaneously requires overriding the port for one service, e.g. `./mvnw quarkus:dev -Dquarkus.http.port=8081`.
