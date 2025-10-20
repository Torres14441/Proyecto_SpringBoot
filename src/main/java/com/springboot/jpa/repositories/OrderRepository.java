package com.springboot.jpa.repositories;

import com.springboot.jpa.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
