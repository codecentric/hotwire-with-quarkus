# hotwire-with-quarkus

This project contains a sample implementation of a to-do app which uses different components of [Hotwire](https://hotwired.dev/).
There is also a blog post for this topic available in [English](https://blog.codecentric.de/en/2022/08/hotwire-new-approach-for-modern-web-applications/) and [German](https://blog.codecentric.de/2022/08/hotwire-neuer-ansatz-moderne-webanwendungen/).

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Starting Keycloak to secure the application
You can start keycloak via docker: 
```shell
docker-compose -f src/main/docker/docker-compose.yaml up -d
```

Wait until Keycloak is started (check the logs via `docker logs keycloak`)

There are two users available:
- admin-user (password admin)
- test-user (password test)

admin-user has ADMIN role, test-user is just a standard USER.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/hotwire-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.
