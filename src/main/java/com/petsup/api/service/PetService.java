package com.petsup.api.service;

import com.petsup.api.entities.Pet;
import com.petsup.api.repositories.PetRepository;
import com.petsup.api.service.dto.PetDto;
import com.petsup.api.service.dto.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public void criarPet(PetDto petDto){
        final Pet novoPet = PetMapper.ofPet(petDto);
        this.petRepository.save(novoPet);
    }
}
