package ch.techcamp.gatling_backend.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain=true)
public class ExampleEntity {
    
    @Id
    private UUID id;
    
    private String exampleText;
    
    private int exampleNumber;
    
    private LocalDate exampleDate;
    private UUID parentId;
    private UUID childId;
}