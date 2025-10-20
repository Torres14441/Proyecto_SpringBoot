package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.User;
import com.springboot.jpa.services.UserService;
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
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> view(@PathVariable Long id){
        Optional<User> userOpcional = service.findById(id);
        if(userOpcional.isPresent()){
            return ResponseEntity.ok(userOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
       if(result.hasErrors()){
           return validation(result);
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @Valid @RequestBody User user , BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<User> userOpcional = service.update(id,user);
        if(userOpcional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(userOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        Optional<User> userOpcional = service.delete(id);
        if(userOpcional.isPresent()){
            return ResponseEntity.ok(userOpcional.orElseThrow());
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
