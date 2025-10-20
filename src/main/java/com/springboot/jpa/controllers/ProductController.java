package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Product;
import com.springboot.jpa.services.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update ( @PathVariable Long id, @Valid @RequestBody Product product, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Product> productoptional = productService.update(id, product);
        if(productoptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productoptional.orElseThrow());
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
