package com.springboot.jpa.services;

import com.springboot.jpa.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();
    Optional<Client> findByName(String name);
    Optional<Client> findById(Long id);
    Optional<Client> findByCcr(String email);
    Client save(Client client);
    Optional<Client> update (Long id,Client category);
    Optional<Client> delete(Long id);
}
