# springboot-webflux-test
This repository is a practical one about springboot-webflux.

## Precondition
This repository dependes on
* java
    * 1.8
* docker
    * 17.12 or more
* docker-compose
    * 1.18.0 or more

## Execute

* bootstrap mock server

```
docker-compose up &
```

* execute all gatling simulations

```
./gradlew gatlingRun
```

* execute specific gatling simulation

ex)
```
./gradlew gatlingRun-computerdatabase.SleepHelloSimulation`
```
