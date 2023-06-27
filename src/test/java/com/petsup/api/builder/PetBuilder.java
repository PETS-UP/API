package com.petsup.api.builder;

import com.petsup.api.models.cliente.Pet;
import com.petsup.api.models.enums.Especie;
import com.petsup.api.dto.cliente.PetDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class PetBuilder {

    public static Pet buildPet() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setNome("Fluffy");
        pet.setSexo("M");
        pet.setEspecie(Especie.CACHORRO);
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
        petDto.setEspecie(Especie.CACHORRO);

        return petDto;
    }
}
