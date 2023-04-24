package com.petsup.api.controllers;

import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.repositories.ServicoRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Tag(name = "Serviços", description = "Requisições relacionadas a serviços.")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postServico(@RequestBody @Valid Servico servico, @RequestParam Integer idPetshop) {
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);

        if (petshopOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioPetshop petshop = petshopOptional.get();
        servico.setFkPetshop(petshop);
        servicoRepository.save(servico);
        return ResponseEntity.status(201).build();
    }
}
