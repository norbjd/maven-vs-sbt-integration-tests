# Maven vs SBT : integration tests execution (Scalatest)

This repository aims to expose the difference between Maven and SBT
when executing integration tests (`mvn integration-test` vs
`sbt it:test`). While `mvn integration-test` run tests against jar
created in `package` phase (when run with Failsafe), `sbt it:test`
executes integration tests using `src/main/` classes.

This makes a huge difference, because using packaged classes when
running integration tests can reveal issues that can be encountered
when running packaged app in production environments, in particular
shading problems (not always so simple to debug in production).

Versions used :

- Environment :
    - JDK : 1.8
    - Scala : 2.12.4
- Tests :
    - Scalatest : 3.0.4
    - JUnit : 4.12
- Build tools :
    - Maven : 3.5.2
    - SBT : 1.1.1

## Tests description

Tests are written in Java and Scala :

- unit tests in `src/test/(java|scala)`
- integration tests in `src/it/(java|scala)`

Java tests are written with JUnit, whereas Scala tests are written
with Scalatest. In Scala, 2 versions of each test are written :

- tests marked to run with a `JUnitRunner` (to be executed by Surefire
and Failsafe) : `ScalaJunitRunner(Unit|Integration)Test`
- "ordinary" tests (no `JUnitRunner`) :
`ScalaNoJunitRunner(Unit|Integration)Test`

### Expected results

#### Unit tests

When running unit tests, we expect that `MainClass` refers to a
`.class` file located :

- in `target/classes` for Maven
- in `target/scala-2.12/classes` for SBT

In fact, this is not important to run unit tests against packaged
classes, because unit tests are small tests designed to check single
methods behaviour.

#### Integration tests

When running integration tests, we expect that `MainClass` refers to
a `.class` file located **inside** packaged `JAR` :

- with Maven, this is possible because `package` phase is run before
`integration-test` phase
- with SBT, this is more difficult because there is no `package` phase
run before integration tests : `sbt it:test` can not execute tests on
packaged JAR if there is no packaged JAR. Even if `assembly` is used
to create a JAR, `it:test` runs using classes outside of this JAR

## Maven

2 profiles are defined :

- `tests-with-surefire-and-failsafe`, running `test` and
`integration-test` phases with respectively Surefire and Failsafe
- `tests-with-scalatest`, running `test` and `integration-test` with
Scalatest plugin. `scalatest-maven-plugin` does not differentiate
unit tests and integration tests, so a hack is made to run only
unit tests during `test` phase and integration tests during
`integration-test` phase

Commands :

    mvn clean verify [-Ptests-with-surefire-and-failsafe] # active by default
    mvn clean verify -Ptests-with-scalatest

`verify` phase runs `test`, `package` and `integration-test` phases
(in this order).

## SBT

Integration tests are run with SBT by specifying `IntegrationTest`
config and `Defaults.itSettings` setting. To run Java tests with
JUnit, `junit-interface` is used.

Commands :

    sbt clean test
    sbt clean it:test

## Results

### Maven

- with `tests-with-surefire-and-failsafe` profile, all tests succeeded : unit
tests are executed using `classes/` while integration tests are
executed using classes packaged in JAR. Tests not marked with `@RunWith(classOf[JUnitRunner])`
are not executed
- with `tests-with-scalatest` profile :
    - **Scala** integration tests failed, because tests are executed
    using `classes/`
    - **Java** integration tests succeeded, because tests are run with
    Failsafe

### SBT

- `sbt test` succeeded : unit tests are executed using `classes/`
- `sbt it:test` failed : **all** (Java + Scala) integration tests are executed using `classes/`.

## Conclusion

Unit tests executed with Maven (Surefire or Scalatest plugin) or SBT
have the same behaviour : all unit tests are run using classes
located in `classes/`.

The behaviour is different for integration tests :

- SBT : because `it:test` is not related to any packaging phase,
tests are executed from `classes/`
- Scalatest : always execute tests from `classes/` (SBT or Maven plugin)
- Maven : Failsafe execute tests from classes in packaged JAR. Using
Failsafe for Scalatest tests requires to annotate test with
`@RunWith(classOf[JUnitRunner])`. To stay consistent, Surefire plugin can
be used instead of `scalatest-maven-plugin`. It also allows to run
Scala and Java tests (see `tests-with-surefire-and-failsafe` profile
configuration in `pom.xml`).