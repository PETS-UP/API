package com.petsup.api.entities.enums;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public enum Especie {
    CACHORRO("Cachorro"),
    GATO("Gato"),
    COELHO("Coelho"),
    ROEDOR("Roedor");

    private String especie;

    private Especie(String especie) {
        this.especie = especie;
    }
}
