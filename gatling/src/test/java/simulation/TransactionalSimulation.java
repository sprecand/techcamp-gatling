package simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import lombok.extern.slf4j.Slf4j;
import simulation.dto.ExampleDto;

@Slf4j
public class TransactionalSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
    .acceptHeader("*/*")
    .contentTypeHeader("application/json");

    ExampleDto example = new ExampleDto().id(UUID.randomUUID())
                            .parentId(UUID.randomUUID())
                            .childId(UUID.randomUUID());

    String exampleJson = String.format("{ \"id\": \"%s\", \"exampleText\": \"string\", \"exampleNumber\": 0, \"exampleDate\": \"2024-10-11\" }", example.getId());

    List<Map<String, Object>> feederData = new ArrayList<>();

    public TransactionalSimulation() {
        setupSimulation();
        runSimulation();
    }

    private void setupSimulation() {
        UUID parent = UUID.randomUUID();
        UUID current = UUID.randomUUID();
        UUID child = UUID.randomUUID();
        for (int i = 0; i < 100; i++) {
            example = new ExampleDto()
                .id(current)
                .exampleDate(LocalDate.now())
                .exampleNumber(BigDecimal.valueOf(i))
                .exampleText("el" + i)
                .parentId(parent)
                .childId(child);
            
            Map<String, Object> row = Map.of(
                "id", example.getId().toString(),
                "exampleJson", String.format(
                    "{\"id\":\"%s\",\"exampleText\":\"%s\",\"exampleNumber\":%s,\"exampleDate\":\"%s\",\"parentId\":\"%s\",\"childId\":\"%s\"}",
                    example.getId(), example.getExampleText(), example.getExampleNumber(), example.getExampleDate(),
                    example.getParentId(), example.getChildId()
                )
            );

            feederData.add(row);
            parent = current;
            current = child;
            child = UUID.randomUUID();
        }
    }

    private void runSimulation() {
        ScenarioBuilder putToDB = scenario("One-time Setup Put Request")
        .repeat(feederData.size()).on(
            feed(listFeeder(feederData))
            .exec(
                http("Setup Put Request")
                    .put("/example/#{id}")
                    .body(StringBody("#{exampleJson}"))
                    .check(status().is(204))
            )
        );
        
        ScenarioBuilder getLinkedListReadOnly = scenario("Get linked List with transactional read only")
            .feed(listFeeder(feederData))
            .exec(
                http("Get Request Read Only")
                    .get("/transactional/#{id}?readOnly=true")
                    .check(status().is(200))
            );

        ScenarioBuilder getLinkedList = scenario("Get linked List")
            .feed(listFeeder(feederData))
            .exec(
                http("Get Request")
                    .get("/transactional/#{id}?readOnly=false")
                    .check(status().is(200))
            );

        setUp(
            putToDB.injectOpen(atOnceUsers(1)).protocols(httpProtocol)
            .andThen(getLinkedListReadOnly.injectOpen(constantUsersPerSec(10).during(10)))
            .andThen(getLinkedList.injectOpen(constantUsersPerSec(10).during(10)))
         ).protocols(httpProtocol);
    }
}
