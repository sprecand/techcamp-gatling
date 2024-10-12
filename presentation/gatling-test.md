```java
    private static final FeederBuilder.FileBased<Object> jsonFeederSingleExample = jsonFile("example_dummy_put.json").random();

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    ScenarioBuilder putScenario = scenario("Basic Put Scenario")
            .feed(jsonFeederSingleExample)
            .exec(http("Create or update - #{id}")
                    .put("/example/#{id}")
                    .header("Content-Type", "application/json")
                    .body(ElFileBody("example_dummy_template.json")).asJson()
                    .check(status().is(204)));
    {
        setUp(
                putScenario.injectOpen(atOnceUsers(10)).protocols(httpProtocol)
        );
    }
```