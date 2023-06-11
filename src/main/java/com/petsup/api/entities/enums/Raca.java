package com.petsup.api.entities.enums;

public enum Raca {
    PASTOR_ALEMAO(Especie.CACHORRO, "Pastor Alemão"),
    LABRADOR(Especie.CACHORRO, "Labrador"),
    PERSA(Especie.GATO, "Persa"),
    SIAMES(Especie.GATO, "Siamês"),
    ANGORA(Especie.COELHO, "Angorá"),
    HOLANDES(Especie.ROEDOR, "Holandês"),
    PERUANO(Especie.ROEDOR, "Peruano");

    private final Especie especie;
    private String raca;

    Raca(Especie especie, String raca) {
        this.especie = Raca.this.especie;
        this.raca = raca;
    }

    public Especie getEspecie() {
        return especie;
    }
}
