package com.petsup.api.service.dto;

import com.petsup.api.entities.Pet;

public class PetMapper {

    public static PetDto ofPetDto(Pet pet) {
        PetDto petDto = new PetDto();

        petDto.setNome(pet.getNome());
        petDto.setSexo(pet.getSexo());
        petDto.setDataNasc(pet.getDataNasc());
        petDto.setCastrado(pet.getCastrado());
        petDto.setEspecie(pet.getEspecie());
        petDto.setRaca(pet.getRaca());

        return petDto;
    }
}
