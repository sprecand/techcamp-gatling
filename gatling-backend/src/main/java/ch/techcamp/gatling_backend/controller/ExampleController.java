package ch.techcamp.gatling_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ch.techcamp.gatling_backend.dto.ExampleDto;
import ch.techcamp.gatling_backend.service.ExampleMapper;
import ch.techcamp.gatling_backend.service.ExampleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExampleController implements ExampleApi {
      private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    @Override
    public ResponseEntity<Void> _deleteExampleById(String exampleId) {
        exampleService.deleteExample(exampleId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ExampleDto> _findExampleById(String exampleId) {
        return exampleService.findById(exampleId)
            .map(exampleMapper::toDto) 
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<List<ExampleDto>> _findExampleList() {
        return ResponseEntity.ok(exampleMapper.toDtoList(exampleService.findAll()));
    }

    @Override
    public ResponseEntity<Void> _putExampleById(String exampleId, ExampleDto exampleDto) {
        exampleService.saveOrUpdate(exampleId, exampleMapper.toDomain(exampleDto));
        return ResponseEntity.noContent().build();
    }
}