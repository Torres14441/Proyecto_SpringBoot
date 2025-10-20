package com.springboot.jpa.services;

import com.springboot.jpa.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);
    Optional<Product> findByCpr(String code);
    Product save(Product product);
    Optional<Product> update(Long id,Product product);
    Optional<Product> delete(Long id);
}
