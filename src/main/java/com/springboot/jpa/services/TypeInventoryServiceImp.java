package com.springboot.jpa.services;

import com.springboot.jpa.entities.Type_Inventory;
import com.springboot.jpa.repositories.TypeInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeInventoryServiceImp implements TypeInventoryService {


    private final TypeInventoryRepository typerepo;

    public TypeInventoryServiceImp(TypeInventoryRepository tyrepo) {
        this.typerepo = tyrepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Type_Inventory> findAll() {
        return typerepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Type_Inventory> findByName(String name) {
        return typerepo.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Type_Inventory> findById(Long id) {
        return typerepo.findById(id);
    }

    @Override
    @Transactional
    public Type_Inventory save(Type_Inventory type){
        return typerepo.save(type);
    }

    @Override
    @Transactional
    public Optional<Type_Inventory> update(Long id, Type_Inventory type){
        Optional<Type_Inventory> optionalType = typerepo.findById(id);
        if(optionalType.isPresent()){
            Type_Inventory typeDB = optionalType.orElseThrow();

            typeDB.setName(type.getName());
            return Optional.of(typerepo.save(typeDB));
        }
        return optionalType;
    }

    @Override
    @Transactional
    public Optional<Type_Inventory> delete(Long id){
        Optional<Type_Inventory> optionalType = typerepo.findById(id);
        optionalType.ifPresent(typeDB-> {
            log.info("Deleting type inventory with id: {}", id);
            typerepo.delete(typeDB);
        });
        return optionalType;
    }

}
