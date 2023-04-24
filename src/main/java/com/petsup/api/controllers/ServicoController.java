package com.petsup.api.controllers;

import com.petsup.api.entities.Servico;
import com.petsup.api.repositories.ServicoRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Serviços", description = "Requisições relacionadas a serviços.")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @PostMapping
    public ResponseEntity<Void> postServico(@RequestBody @Valid Servico servico) {
        servicoRepository.save(servico);
        return ResponseEntity.status(201).build();
    }
}
