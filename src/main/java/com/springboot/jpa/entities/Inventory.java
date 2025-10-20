package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_localization", nullable = false)
    private Localization localization;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock_actual")
    private Integer stockActual;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock_min")
    private Integer stockMin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    private LocalDateTime update;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.update = LocalDateTime.now();
    }

}
