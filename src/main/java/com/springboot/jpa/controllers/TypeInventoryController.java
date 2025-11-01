package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Type_Inventory;
import com.springboot.jpa.services.TypeInventoryService;
import jakarta.validation.Valid;
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
public class TypeInventoryController {

    private final TypeInventoryService tiservice;

    public TypeInventoryController(TypeInventoryService tiservice) {
        this.tiservice = tiservice;
    }

    @GetMapping
    public List<Type_Inventory> findAll(){
        return tiservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type_Inventory> view(@PathVariable Long id){
        Optional<Type_Inventory> tyOptional = tiservice.findById(id);
        if(tyOptional.isPresent()){
            return ResponseEntity.ok(tyOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Type_Inventory typeInventory,  BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tiservice.save(typeInventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id ,@Valid @RequestBody Type_Inventory typeInventory, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Type_Inventory> tyOpcional = tiservice.update(id,typeInventory);
        if(tyOpcional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(tyOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Type_Inventory> delete(@PathVariable Long id){
        Optional<Type_Inventory> tyOptional = tiservice.delete(id);
        if(tyOptional.isPresent()){
            return ResponseEntity.ok(tyOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Object> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        result.getGlobalErrors().forEach(err ->
                errors.put("error", err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);}
}
