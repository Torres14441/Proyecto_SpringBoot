package com.springboot.jpa.services;

import com.springboot.jpa.entities.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<Inventory> findAll();
    Optional<Inventory> findById(Long id);
    Optional<Inventory> findByProductId(Long product);
    Inventory save(Inventory product);
    Optional<Inventory> update(Long id,Inventory inventory);
    Optional<Inventory> delete(Long id);
}
