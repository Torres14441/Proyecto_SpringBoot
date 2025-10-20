package com.springboot.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.jpa.validations.ExistByCcr;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "clients")
@ExistByCcr
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 4)
    private String ccr;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 20)
    private String tel;

    @JsonIgnoreProperties({"client","handler","hibernateLazyInitializer"})
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ClientDetails details;


}
