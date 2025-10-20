package com.springboot.jpa.services;

import com.springboot.jpa.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategorieService {
    List<Category> findAll();
    Optional<Category> findByName(String name);
    Optional<Category> findById(Long id);
    Category save(Category product);
    Optional<Category> update (Long id,Category category);
    Optional<Category> delete(Long id);
}
