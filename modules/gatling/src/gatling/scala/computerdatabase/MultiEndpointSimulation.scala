package computerdatabase
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MultiEndpointSimulation extends Simulation {

    var rampSec = 60
    // 1 min
    var constantSec = 5 * 60 * 1
    val httpConf = http
            .acceptCharsetHeader("utf-8")
            .acceptHeader("application/json")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
            .doNotTrackHeader("1")
            .disableFollowRedirect

    val helloScenario = scenario("Hello Scenario")
            .exec(http("mvc")
                    .get("http://localhost:8081/hello")
                    .check(status.is(200))
            ).exec(http("webflux")
                  .get("http://localhost:8082/hello")
                  .check(status.is(200))
            )

    setUp(
      helloScenario.inject(rampUsersPerSec(1) to(30) during(rampSec seconds), constantUsersPerSec(30) during(constantSec seconds)).protocols(httpConf)
    )

}
