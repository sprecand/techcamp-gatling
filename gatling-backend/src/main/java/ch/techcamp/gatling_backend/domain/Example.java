package ch.techcamp.gatling_backend.domain;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class Example {
    UUID id;
    String exampleText;
    int exampleNumber;
    LocalDate exampleDate;
}
