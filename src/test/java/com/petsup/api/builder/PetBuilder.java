package com.petsup.api.builder;

import com.petsup.api.entities.Pet;
import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.Raca;
import com.petsup.api.service.dto.PetDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class PetBuilder {

    public static Pet buildPet() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setNome("Fluffy");
        pet.setSexo("M");
        pet.setDataNasc(LocalDate.of(2020, 1, 1));
        pet.setCastrado(0);
        pet.setEspecie(Especie.CACHORRO);
        pet.setRaca(Raca.LABRADOR);
        pet.setAgendamentos(emptyList());
        pet.setFkCliente(UsuarioClienteBuilder.buildUsuarioCliente());

        return pet;
    }

    public static List<Pet> buildListaPet() {
        List<Pet> lista = new ArrayList<>();
        Pet pet1 = PetBuilder.buildPet();
        Pet pet2 = PetBuilder.buildPet();
        Pet pet3 = PetBuilder.buildPet();
        pet2.setId(2);
        pet3.setId(3);

        lista.add(pet1);
        lista.add(pet2);
        lista.add(pet3);

        return lista;
    }

    public static PetDto buildPetDto() {
        PetDto petDto = new PetDto();
        petDto.setNome("Fluffy");
        petDto.setSexo("M");
        petDto.setDataNasc(LocalDate.of(2020, 1, 1));
        petDto.setCastrado(0);
        petDto.setEspecie(Especie.CACHORRO);
        petDto.setRaca(Raca.LABRADOR);

        return petDto;
    }
}
