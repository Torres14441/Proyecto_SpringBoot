package com.springboot.jpa.controllers;

import com.springboot.jpa.entities.Order;
import com.springboot.jpa.services.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        if(order.isPresent()){
            return ResponseEntity.ok(order.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Order order, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Order order, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Order> orderOptional = orderService.update(id, order);
        if(orderOptional.isPresent()){
            return ResponseEntity.ok(orderOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Order> orderOptional = orderService.delete(id);
        if(orderOptional.isPresent()){
            return ResponseEntity.ok(orderOptional.orElseThrow());
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
