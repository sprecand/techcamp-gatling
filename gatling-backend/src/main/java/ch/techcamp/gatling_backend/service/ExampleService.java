package ch.techcamp.gatling_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ch.techcamp.gatling_backend.domain.Example;
import ch.techcamp.gatling_backend.repository.ExampleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExampleService {
    private final ExampleRepository exampleRepository;
    private final ExampleMapper exampleMapper;

    public void deleteExample(String id) {
        exampleRepository.deleteById(UUID.fromString(id));
    }

    public Optional<Example> findById(String id) {
        return exampleRepository.findById(UUID.fromString(id))
        .map(exampleMapper::toDomain);
    }

    public List<Example> findAll() {
        return exampleRepository.findAll()
        .stream()
        .map(exampleMapper::toDomain)
        .toList();
    }

    public void deleteAll() {
        exampleRepository.deleteAll();
    }

    public void saveOrUpdate(String id, Example example) {
        example.setId(UUID.fromString(id));
        exampleRepository.save(exampleMapper.toEntity(example));
        log.info("Saved or updated Example with id {}", id);
    }
}
