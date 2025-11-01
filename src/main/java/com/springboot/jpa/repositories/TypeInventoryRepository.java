package com.springboot.jpa.repositories;


import com.springboot.jpa.entities.Type_Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeInventoryRepository extends JpaRepository<Type_Inventory,Long> {
    Optional<Type_Inventory> findByName(String name);
}
