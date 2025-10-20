package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Client;
import com.springboot.jpa.services.ClientService;
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
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> findAll(){
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id){
        Optional<Client> client = clientService.findById(id);
        if(client.isPresent()){
            return ResponseEntity.ok(client.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Client client, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Client client, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<Client> clientOptional = clientService.update(id, client);
        if(clientOptional.isPresent()){
            return ResponseEntity.ok(clientOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Client> clientOptional = clientService.delete(id);
        if(clientOptional.isPresent()){
            return ResponseEntity.ok(clientOptional.orElseThrow());
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
