package ch.techcamp.gatling_backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.techcamp.gatling_backend.domain.Example;
import ch.techcamp.gatling_backend.dto.ExampleDto;
import ch.techcamp.gatling_backend.entity.ExampleEntity;

@Service
public class ExampleMapper {

    public Example toDomain(ExampleDto exampleDto){
        return new Example()
        .setId(exampleDto.getId())
        .setExampleText(exampleDto.getExampleText())
        .setExampleNumber(exampleDto.getExampleNumber().intValue())
        .setExampleDate(exampleDto.getExampleDate());
    }

    public Example toDomain(ExampleEntity exampleEntity){
        return new Example()
        .setId(exampleEntity.getId())
        .setExampleText(exampleEntity.getExampleText())
        .setExampleNumber(exampleEntity.getExampleNumber())
        .setExampleDate(exampleEntity.getExampleDate());
    }

    public ExampleEntity toEntity(Example example){
        return new ExampleEntity()
        .setId(example.getId())
        .setExampleText(example.getExampleText())
        .setExampleNumber(example.getExampleNumber())
        .setExampleDate(example.getExampleDate());
    }

    public List<ExampleDto> toDtoList(List<Example> exampleList) {
        return exampleList.stream()
        .map(this::toDto)
        .toList();
    }

    public ExampleDto toDto(Example example) {
        return new ExampleDto()
        .id(example.getId())
        .exampleText(example.getExampleText())
        .exampleNumber(BigDecimal.valueOf(example.getExampleNumber()))
        .exampleDate(example.getExampleDate());
    }
}
