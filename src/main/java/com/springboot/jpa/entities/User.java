package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.jpa.validations.ExistByUsername;
import com.springboot.jpa.validations.PasswordRequiredIfNew;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="users")
@ExistByUsername
@PasswordRequiredIfNew
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min=3, max=50)
    private String name;


    @NotEmpty(message = "El username no puede estar vacío")
    @Size(min=4, max=20)
    private String username;

    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "Formato incorrecto")
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private List<Rol> roles;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    @Column(name = "status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer enabledValue;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/El_Salvador")
    private Date updated;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.updated = new Date();
    }

    public boolean isEnabled() {
        return enabledValue != null && enabledValue == 1;
    }

}
