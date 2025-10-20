package com.springboot.jpa.repositories;

import com.springboot.jpa.entities.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDetailRepository extends JpaRepository<ClientDetails, Integer> {
}
