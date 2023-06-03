package com.petsup.api.controllers;

import com.petsup.api.entities.Pet;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetRepository;
import com.petsup.api.service.dto.PetDto;
import com.petsup.api.service.dto.PetMapper;
import com.petsup.api.util.PilhaObj;
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

    private PilhaObj<String> pilhaObj = new PilhaObj<String>(5);

    @PostMapping("/adicionar-pilha")
    public ResponseEntity<Void> adicionarNaPilha(String obj){
        pilhaObj.push(obj);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pop-pilha")
    public ResponseEntity<String> getInfosAndPop(){
        return ResponseEntity.ok().body(pilhaObj.pop());
    }

    @PostMapping("limpa-pilha")
    public ResponseEntity<Void> limparPilha(){
        for (int i = 0; i < pilhaObj.getTopo(); i++) {
            pilhaObj.pop();
        }
        return ResponseEntity.ok().build();
    }

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

    @ApiResponse(responseCode = "200", description = "Retorna o pet a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Integer id) {
        return ResponseEntity.ok(PetMapper.ofPetDto(petRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pet não encontrado"))
        ));
    }
}
