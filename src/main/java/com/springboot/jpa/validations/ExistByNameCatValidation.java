package com.springboot.jpa.validations;

import com.springboot.jpa.entities.Category;
import com.springboot.jpa.services.CategorieService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ExistByNameCatValidation implements ConstraintValidator<ExistByNameCat, Category> {
    @Autowired
    private CategorieService service;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context) {
        if(service == null){
            return true;
        }
        if (category == null || category.getName() == null) return true;

        Optional<Category> existente = service.findByName(category.getName());
        return existente.isEmpty() || existente.get().getId().equals(category.getId());
    }
}
