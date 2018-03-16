# springboot-webflux-test
This repository is a practical repo about `springboot-webflux`.
In addition, this has sample code to compare with `spring-mvc` in performance and resistance for backpressure,

**if you'd like to know accurate performance, I recommend you to deploy programs to servers**

## Precondition
This repository dependes on
* java
    * 1.8
* docker
    * 17.12 or more
* docker-compose
    * 1.18.0 or more

## Execute
### bootstrap docker containers

```
docker-compose up &
```

execute this command, you can boot 5 containers.
* application
    * use spring-mvc
    * use spring-webflux
* mock
    * use openresty
* tools
    * [prometheus](https://prometheus.io/)
    * [grafana](https://grafana.com/)

### execute specific gatling simulation

ex)
```
./gradlew gatlingRun-computerdatabase.HelloSimulation`
```

## Access
### Endpoint
* spring-mvc

`http://localhost:8081/`

* spring-webflux

`http://localhost:8082/`

### Tools
* Prometheus

open `http://localhost:9090/` with your browser.

* Grafana

open `http://localhost:3000/` with your browser.

