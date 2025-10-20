package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "details_clients")
public class ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnoreProperties({"details","handler","hibernateLazyInitializer"})
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Client client;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    @Column(name = "created")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    @Column(name = "updated")
    private Date updated;

    @PrePersist
    public void onCreate() {
        this.created = new Date();
        this.updated = new Date();
    }

    @PreUpdate
    public void actualizarFecha() {
        this.updated = new Date();
    }

}
