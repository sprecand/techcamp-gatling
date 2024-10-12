package ch.techcamp.gatling_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ch.techcamp.gatling_backend.domain.Example;
import ch.techcamp.gatling_backend.repository.ExampleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExampleService {
    private final ExampleRepository exampleRepository;
    private final ExampleMapper exampleMapper;

    @Transactional
    public void deleteExample(String id) {
        exampleRepository.deleteById(UUID.fromString(id));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.info("Delete-Transaction Interrupted - UUID: %s".formatted(id));
        }
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

    @Transactional
    public void saveOrUpdate(String id, Example example) {
        example.setId(UUID.fromString(id));
        exampleRepository.save(exampleMapper.toEntity(example));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.info("Save-Transaction Interrupted - UUID: %s".formatted(example.getId()));
        }
        log.info("Saved or updated Example with id {}", id);
    }
}
