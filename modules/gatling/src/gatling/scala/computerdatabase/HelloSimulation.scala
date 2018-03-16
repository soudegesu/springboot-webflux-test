package computerdatabase
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.UUID
import scala.util.Random

class HelloSimulation extends Simulation {

  var rampSec = 60
  // 1 min
  var constantSec = 1 * 60 * 1
  val httpConf = http
      .baseURL("http://localhost:8081")
      .acceptCharsetHeader("utf-8")
      .acceptHeader("application/json")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
      .doNotTrackHeader("1")
      .disableFollowRedirect

  val helloScenario = scenario("Hello Scenario")
      .exec(http("request")
          .get("/hello")
          .check(status.is(200))
      )

  setUp(helloScenario.inject(
        rampUsersPerSec(1) to(130) during(rampSec seconds),
        constantUsersPerSec(130) during(constantSec seconds)
      ).protocols(httpConf))
}
