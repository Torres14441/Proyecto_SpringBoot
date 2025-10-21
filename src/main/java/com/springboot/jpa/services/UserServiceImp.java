package com.springboot.jpa.services;

import com.springboot.jpa.entities.Rol;
import com.springboot.jpa.entities.User;
import com.springboot.jpa.repositories.RolRepository;
import com.springboot.jpa.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        List<Rol> roles = asignarRol(user.isAdmin());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabledValue(1);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> userOpcional = userRepository.findById(id);
        if(userOpcional.isPresent()){
            User userDb = userOpcional.orElseThrow();

            userDb.setName(user.getName());
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());


            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                if (!passwordEncoder.matches(user.getPassword(), userDb.getPassword())) {
                    userDb.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }


            List<Rol> roles = asignarRol(user.isAdmin());
            userDb.setRoles(roles);
            return Optional.of(userRepository.save(userDb));
        }
        return userOpcional;
    }

    @Override
    @Transactional
    public Optional<User> delete(Long id) {
        Optional<User> userOpcional = userRepository.findById(id);
        userOpcional.ifPresent(userDb -> {
            if(!userDb.isEnabled()){
                throw new IllegalStateException("El usuario ya está deshabilitado");
            }
            userDb.setEnabledValue(0);
            userRepository.save(userDb);
        });
        return userOpcional;
    }

    private List<Rol> asignarRol(boolean isAdmin) {
        List<Rol> roles = new ArrayList<>();

        rolRepository.findByName("ROLE_USER").ifPresent(roles::add);

        if(isAdmin){
            rolRepository.findByName("ROLE_ADMIN").ifPresent(roles::add);
        }
        if(roles.isEmpty()){
            throw new IllegalStateException("No se pudieron asignar roles al usuario.");
        }
        return roles;
    }
}
