package com.petsup.api.controllers;

import com.petsup.api.entities.Pet;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

}
