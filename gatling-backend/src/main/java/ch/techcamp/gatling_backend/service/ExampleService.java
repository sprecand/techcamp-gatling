package ch.techcamp.gatling_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.techcamp.gatling_backend.domain.Example;
import ch.techcamp.gatling_backend.repository.ExampleRepository;

import java.util.ArrayList;
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

    public void saveOrUpdate(String id, Example example) {
        example.setId(UUID.fromString(id));
        exampleRepository.save(exampleMapper.toEntity(example));
        log.info("Saved or updated Example with id {}", id);
    }

    @Transactional
    public void deleteAllExample(){
        exampleRepository.deleteAll();
    }

    @Transactional(readOnly=true)
    public List<Example> getListReadOnlyTrue(String id) {
        return getList(id); 
    }

    @Transactional(readOnly=false)
    public List<Example> getListReadOnlyFalse(String id) {
        return getList(id);
    }

    private List<Example> getList(String id){
        List<Example> list = new ArrayList<>();
        Optional<Example> start = findById(UUID.fromString(id));
        
        if (start.isEmpty()){
            return list;
        }
        list.add(start.get());

        Optional<Example> child = findById(start.get().getChildId());
        while(child.isPresent()){
            list.add(child.get());
            child = findById(child.get().getChildId());
        }

        Optional<Example> parent = findById(start.get().getParentId());
        while(parent.isPresent()){
            list.add(parent.get());
            parent = findById(parent.get().getChildId());
        }

        return list;
    }

    private Optional<Example> findById(UUID id){
        if(id==null){
            return Optional.empty();
        }

        return exampleRepository.findById(id)
        .map(exampleMapper::toDomain);
    }
}
