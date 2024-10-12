package simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ExampleSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    ScenarioBuilder myScenario = scenario("My Scenario")
    .exec(http("Request 1").get("/example"));

    {
        setUp(
            myScenario.injectOpen(constantUsersPerSec(100).during(20))
        ).protocols(httpProtocol);
    }
}