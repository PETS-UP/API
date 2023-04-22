package com.petsup.api.entities.enums;

public enum Raca {
    PASTOR_ALEMAO(Especie.CACHORRO),
    LABRADOR(Especie.CACHORRO),
    PERSA(Especie.GATO),
    SIAMES(Especie.GATO),
    ANGORA(Especie.COELHO),
    HOLANDES(Especie.ROEDOR),
    PERUANO(Especie.ROEDOR);

    private final Especie especie;

    Raca(Especie especie) {
        this.especie = Raca.this.especie;
    }

    public Especie getEspecie() {
        return especie;
    }
}
