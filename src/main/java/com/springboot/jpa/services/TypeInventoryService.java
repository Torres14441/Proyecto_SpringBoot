package com.springboot.jpa.services;

import com.springboot.jpa.entities.Type_Inventory;

import java.util.List;
import java.util.Optional;

public interface TypeInventoryService {
    List<Type_Inventory> findAll();
    Optional<Type_Inventory> findByName(String name);
    Optional<Type_Inventory> findById(Long id);
    Type_Inventory save(Type_Inventory product);
    Optional<Type_Inventory> update(Long id,Type_Inventory typeInventory);
    Optional<Type_Inventory> delete(Long id);
}
