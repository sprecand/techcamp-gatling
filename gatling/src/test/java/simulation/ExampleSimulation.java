package simulation;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ExampleSimulation extends Simulation {

    private static final FeederBuilder.FileBased<Object> jsonFeeder = jsonFile("example_dummy.json").random();
    private static final FeederBuilder.FileBased<Object> jsonFeederSingleExample = jsonFile("example_dummy_put.json").random();

    private static final String testUUID = "4cdf38a8-8d2e-4378-a17b-3a9a1bd2b4d7";

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    ScenarioBuilder putScenario = scenario("Basic Put Scenario")
            .feed(jsonFeederSingleExample)
            .exec(http("Create or update - #{id}")
                .put("/example/#{id}")
                .header("Content-Type", "application/json")
                .body(ElFileBody("example_dummy_template.json")).asJson()
                .check(status().is(204)));

    ScenarioBuilder putScenarioSingle = scenario("Basic Put Scenario Single")
            .exec(http("Put-Request Single")
                    .put("/example/" + testUUID)
                    .header("Content-Type", "application/json")
                    .body(RawFileBody("example_dummy_put.json")).asJson()
                    .check(status().is(204)));

    ScenarioBuilder deleteScenario = scenario("Basic Delete Scenario")
            .exec(http("Delete-Request")
                    .delete("/example/" + testUUID)
                    .check(status().is(204)));

    ScenarioBuilder getScenario = scenario("Basic Get Scenario")
            .exec(http("Get-Request")
                    .get("/example/" + testUUID)
                    .check(status().is(200)));

    {
        setUp(
                getScenario.injectOpen(rampUsers(1000).during(5)).protocols(httpProtocol),
                putScenario.injectOpen(atOnceUsers(10)).protocols(httpProtocol)
//                putScenarioSingle.injectOpen(rampUsers(5).during(5)).protocols(httpProtocol)
//                getScenario.injectOpen(rampUsers(5).during(2)).protocols(httpProtocol)
        );
    }
}