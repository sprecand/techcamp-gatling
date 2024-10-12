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
public class TransactionalController implements TransactionalApi{
    private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    @Override
    public ResponseEntity<List<ExampleDto>> _findExampleListById(String exampleId, Boolean readOnly) {
        if (readOnly == null || readOnly.booleanValue()){
            List<ExampleDto> dtoList = exampleService.getListReadOnlyTrue(exampleId)
                .stream()
                .map(exampleMapper::toDto)
                .toList();

            if (dtoList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(dtoList);
            }
        }
        List<ExampleDto> dtoList = exampleService.getListReadOnlyFalse(exampleId)
            .stream()
            .map(exampleMapper::toDto)
            .toList();

        if (dtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dtoList);
        }
    }
}
