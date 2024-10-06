package ch.techcamp.gatling_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.techcamp.gatling_backend.entity.ExampleEntity;

public interface ExampleRepository extends JpaRepository<ExampleEntity, UUID>{
    
}
