package com.petsup.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "Pet")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private char sexo;

    @NotNull
    @PastOrPresent
    private LocalDate dataNasc;

    @Min(0)
    @Max(1)
    @NotNull
    private Integer castrado;
}
