package com.petsup.api.controllers;

import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.repositories.ServicoRepository;
import com.petsup.api.service.dto.ServicoDto;
import com.petsup.api.service.dto.ServicoMapper;
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

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao pet shop.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o pet shop não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado")
    @GetMapping
    public ResponseEntity<List<ServicoDto>> getServicosByIdPetshop(@RequestParam Integer idPetshop) {

        if (petshopRepository.findById(idPetshop).isPresent()) {
            List<Servico> servicos = servicoRepository.findByFkPetshopId(idPetshop);
            List<ServicoDto> servicosDto = new ArrayList<>();

            for (Servico servico : servicos) {
                servicosDto.add(ServicoMapper.ofServicoDto(servico));
            }

            return servicosDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(servicosDto);
        }
        return ResponseEntity.status(404).build();
    }
}
