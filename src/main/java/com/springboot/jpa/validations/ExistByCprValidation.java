package com.springboot.jpa.validations;

import com.springboot.jpa.entities.Product;
import com.springboot.jpa.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ExistByCprValidation implements ConstraintValidator<ExistByCpr, Product> {
    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if(service == null){
            return true;
        }
        if (product == null || product.getCpr() == null) return true;

        Optional<Product> existente = service.findByCpr(product.getCpr());
        return existente.isEmpty() || existente.get().getId().equals(product.getId());
    }

}
