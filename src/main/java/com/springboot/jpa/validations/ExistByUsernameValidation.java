package com.springboot.jpa.validations;

import com.springboot.jpa.entities.User;
import com.springboot.jpa.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, User> {
    @Autowired
    private UserService service;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if(service == null){
            return true;
        }

        if (user == null || user.getUsername() == null) return true;
        Optional<User> existente = service.findByUsername(user.getUsername());
        return existente.isEmpty() || existente.get().getId().equals(user.getId());

    }
}
