package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.jpa.validations.ExistByCpr;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ExistByCpr
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "No puede estar vacío")
    @Size(min=5, max=10)
    private String cpr;

    @NotBlank(message = "No puede estar vacío")
    @Size(max=100)
    private String name;

    @Size(max=1000)
    private String description;

    @NotNull(message = "Tiene que tener un precio")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type_inv", nullable = false)
    @NotNull(message = "El tipo de inventario es obligatorio")
    private Type_Inventory type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id","category_id"})}
    )
    private List<Category> categories;

}
