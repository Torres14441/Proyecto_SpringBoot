package com.springboot.jpa.services;

import com.springboot.jpa.entities.Type_Inventory;
import com.springboot.jpa.repositories.Type_InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class Type_InventoryServiceImp implements Type_InventoryService {

    @Autowired
    private Type_InventoryRepository TIrepository;

    @Override
    @Transactional(readOnly = true)
    public List<Type_Inventory> findAll() {
        return TIrepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Type_Inventory> findByName(String name) {
        return TIrepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Type_Inventory> findById(Long id) {
        return TIrepository.findById(id);
    }

    @Override
    @Transactional
    public Type_Inventory save(Type_Inventory type){
        return TIrepository.save(type);
    }

    @Override
    @Transactional
    public Optional<Type_Inventory> update(Long id, Type_Inventory type){
        Optional<Type_Inventory> optionalType = TIrepository.findById(id);
        if(optionalType.isPresent()){
            Type_Inventory typeDB = optionalType.orElseThrow();

            typeDB.setName(type.getName());
            return Optional.of(TIrepository.save(typeDB));
        }
        return optionalType;
    }

    @Override
    @Transactional
    public Optional<Type_Inventory> delete(Long id){
        Optional<Type_Inventory> optionalType = TIrepository.findById(id);
        optionalType.ifPresent(typeDB-> {
            TIrepository.delete(typeDB);
        });
        return optionalType;
    }

}
