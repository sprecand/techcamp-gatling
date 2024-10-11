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

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    ScenarioBuilder putScenario = scenario("Basic Put Scenario")
            .feed(jsonFeeder)
            .exec(http("Create new example - #{id}")
                .put("/example/#{id}")
                .header("Content-Type", "application/json")
                .body(ElFileBody("example_dummy_template.json")).asJson()
                .check(status().is(204)));

    ScenarioBuilder putScenarioParallel = scenario("Basic Put Scenario 2")
            .exec(http("Put-Request 2")
                    .put("/example/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                    .header("Content-Type", "application/json")
                    .body(RawFileBody("example_dummy.json")).asJson()
                    .check(status().is(204)));

    ScenarioBuilder deleteScenario = scenario("Basic Delete Scenario")
            .exec(http("Delete-Request")
                    .delete("/example/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                    .check(status().is(204)));

    ScenarioBuilder getScenario = scenario("Basic Get Scenario")
            .exec(http("Get-Request")
                    .get("/example/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                    .check(status().is(200)));

    {
        setUp(
                putScenario.injectOpen(atOnceUsers(100)).protocols(httpProtocol)

//                getScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocol)
        );
    }
}