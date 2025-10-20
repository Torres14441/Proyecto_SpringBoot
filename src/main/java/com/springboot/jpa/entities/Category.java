package com.springboot.jpa.entities;

import com.springboot.jpa.validations.ExistByNameCat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Entity
@Data
@Table(name = "categories")
@ExistByNameCat
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Size(min = 3, max = 50)
    private String name;
}
