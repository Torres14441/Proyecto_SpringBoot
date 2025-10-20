package com.springboot.jpa.validations;

import com.springboot.jpa.entities.Client;
import com.springboot.jpa.services.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ExistByCcrValidation implements ConstraintValidator<ExistByCcr, Client> {
    @Autowired
    private ClientService service;

    @Override public boolean isValid(Client client,  ConstraintValidatorContext context){
        if(service == null){
            return true;
        }
        if(client == null || client.getCcr() == null)return true;
        Optional<Client> existente = service.findByCcr(client.getCcr());
        return existente.isEmpty() || existente.get().getId().equals(client.getId());
    }

}
