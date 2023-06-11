package com.petsup.api.service.dto;

import com.petsup.api.entities.Pet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static PetRespostaDto ofPetRespostaDto(Pet pet) {
        PetRespostaDto petRespostaDto = new PetRespostaDto();

        petRespostaDto.setNome(pet.getNome());
        petRespostaDto.setSexo(pet.getSexo());
        petRespostaDto.setDataNasc(pet.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        petRespostaDto.setEspecie(pet.getEspecie().toString());
        petRespostaDto.setRaca(pet.getRaca().toString());

        return petRespostaDto;
    }

    public static List<PetRespostaDto> ofListaPetRespostaDto(List<Pet> pets) {
        List<PetRespostaDto> listaPetDto = new ArrayList<>();

        for (Pet p : pets) {
            listaPetDto.add(ofPetRespostaDto(p));
        }

        return listaPetDto;
    }
}
