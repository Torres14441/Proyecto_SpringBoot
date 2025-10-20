package com.springboot.jpa.repositories;

import com.springboot.jpa.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    boolean existsByCpr(String cpr);

    Optional<Product> findByCpr(String cpr);
}
