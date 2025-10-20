package com.springboot.jpa.validations;

import com.springboot.jpa.entities.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordRequiredIfNewValidator implements ConstraintValidator<PasswordRequiredIfNew, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getId() == null) {
            return user.getPassword() != null && !user.getPassword().isBlank();
        }
        return true;
    }


}
