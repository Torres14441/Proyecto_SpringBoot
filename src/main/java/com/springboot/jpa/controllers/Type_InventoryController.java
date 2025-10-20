package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Type_Inventory;
import com.springboot.jpa.services.Type_InventoryService;
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
@RequestMapping("/api/types")
public class Type_InventoryController {
    @Autowired
    Type_InventoryService TIservice;

    @GetMapping
    public List<Type_Inventory> findAll(){
        return TIservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type_Inventory> view(@PathVariable Long id){
        Optional<Type_Inventory> TyOptional = TIservice.findById(id);
        if(TyOptional.isPresent()){
            return ResponseEntity.ok(TyOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Type_Inventory typeInventory,  BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(TIservice.save(typeInventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@Valid @RequestBody Type_Inventory typeInventory, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Type_Inventory> TyOpcional = TIservice.update(id,typeInventory);
        if(TyOpcional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(TyOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Type_Inventory> delete(@PathVariable Long id){
        Optional<Type_Inventory> TyOptional = TIservice.delete(id);
        if(TyOptional.isPresent()){
            return ResponseEntity.ok(TyOptional.orElseThrow());
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
