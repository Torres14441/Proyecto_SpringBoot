package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Localization;
import com.springboot.jpa.services.LocalizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/localizations")
public class LocalizationController {
    @Autowired
    private LocalizationService Lcservice;

    @GetMapping
    public List<Localization> findAll(){
        return Lcservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localization> findById(@PathVariable Long id){
        Optional<Localization> locOptional = Lcservice.findById(id);
        if(locOptional.isPresent()){
            return ResponseEntity.ok(locOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Localization localization, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Lcservice.save(localization));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Localization localization, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Localization> locOptional = Lcservice.update(id, localization);
        if(locOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(locOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Localization> locOptional = Lcservice.delete(id);
        if(locOptional.isPresent()){
            return ResponseEntity.ok(locOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        result.getGlobalErrors().forEach(err ->
                errors.put("error", err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);}
}
