package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Inventory;
import com.springboot.jpa.services.InventoryService;
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
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> findAll() {
        return inventoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> findById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryService.findById(id);
        if (inventory.isPresent()) {
            return ResponseEntity.ok().body(inventory.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Inventory inventory,  BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.save(inventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Inventory inventory, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Inventory> inventoryOptional = inventoryService.update(id, inventory);
        if (inventoryOptional.isPresent()) {
            return ResponseEntity.ok().body(inventoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryService.delete(id);
        if (inventory.isPresent()) {
            return ResponseEntity.ok().body(inventory.orElseThrow());
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
