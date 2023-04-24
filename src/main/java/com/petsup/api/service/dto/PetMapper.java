package com.petsup.api.service.dto;

import com.petsup.api.entities.Pet;

public class PetMapper {

    public static Pet ofPet(PetDto petCriacaoDto) {
        Pet pet = new Pet();

        pet.setNome(petCriacaoDto.getNome());
        pet.setSexo(petCriacaoDto.getSexo());
        pet.setDataNasc(petCriacaoDto.getDataNasc());
        pet.setCastrado(petCriacaoDto.getCastrado());
        pet.setEspecie(petCriacaoDto.getEspecie());
        pet.setRaca(petCriacaoDto.getRaca());
        pet.setFkCliente(pet.getFkCliente());

        return pet;
    }
}
