package com.petsup.api.models.enums;

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
