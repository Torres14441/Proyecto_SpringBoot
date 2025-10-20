package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    @JsonIgnoreProperties({"details", "created", "updated","handler", "hibernateLazyInitializer"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnoreProperties({"roles", "enabled", "created", "updated","handler", "hibernateLazyInitializer"})
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"order", "handler", "hibernateLazyInitializer"})
    private List<OrderDetails> items = new ArrayList<>();


    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "status")
    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    @Column(name = "created")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    @Column(name = "updated")
    private Date updated;



    @PrePersist
    protected void onCreate() {
        this.created = new Date();
        this.updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }

    public BigDecimal calcularTotal() {
        return items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
