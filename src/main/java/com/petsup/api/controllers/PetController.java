package com.petsup.api.controllers;

import com.petsup.api.entities.Pet;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetRepository;
import com.petsup.api.service.dto.PetDto;
import com.petsup.api.service.dto.PetMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Pets", description = "Requisições relacionadas a pets.")
@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @ApiResponse(responseCode = "201", description = "Pet cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postPet(@RequestBody @Valid Pet pet, @RequestParam Integer idCliente) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioCliente cliente = clienteOptional.get();
        pet.setFkCliente(cliente);
        petRepository.save(pet);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de pets atrelados ao cliente.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o cliente não tenha pets cadastrados.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping
    public ResponseEntity<List<PetDto>> getPetsByIdCliente(@RequestParam Integer idCliente) {

        if (clienteRepository.findById(idCliente).isPresent()) {
            List<Pet> pets = petRepository.findByFkClienteId(idCliente);
            List<PetDto> petsDto = new ArrayList<>();

            for (Pet pet : pets) {
                petsDto.add(PetMapper.ofPetDto(pet));
            }

            return petsDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(petsDto);
        }
        return ResponseEntity.status(404).build();
    }

}
