package com.springboot.jpa.services;

import com.springboot.jpa.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<User> update(Long id, User user);
    Optional<User> delete(Long id);
}
