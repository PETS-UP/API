package com.petsup.api.entities.enums;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public enum Especie {
    CACHORRO("cachorro"),
    GATO("gato"),
    COELHO("coelho"),
    ROEDOR("roedor");

    private String especie;

    private Especie(String especie) {
        this.especie = especie;
    }
}
