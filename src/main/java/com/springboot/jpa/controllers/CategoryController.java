package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Category;
import com.springboot.jpa.services.CategorieService;
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
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public List<Category> findAll(){
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Optional<Category> category = categorieService.findById(id);
        if(category.isPresent()){
            return ResponseEntity.ok().body(category.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Category category,  BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@Valid @RequestBody Category category, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Category> categoryOptional = categorieService.update(id, category);
        if(categoryOptional.isPresent()){
            return ResponseEntity.ok().body(categoryOptional.orElseThrow());
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
