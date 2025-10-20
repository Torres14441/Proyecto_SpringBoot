package com.springboot.jpa.repositories;

import com.springboot.jpa.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByName(String correo);
    Optional<Client> findByCcr(String ccr);
}
