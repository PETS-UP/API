package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.dto.UsuarioClienteDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private UsuarioService usuarioService;

    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.")
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid UsuarioClienteDto usuarioDto){
        this.usuarioService.criarCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso não haja clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<UsuarioCliente>> getClientes(){
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();
        return usuarios.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(usuarios);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCliente> getUserById(@PathVariable Integer id){
        return ResponseEntity.of(this.clienteRepository.findById(id));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        this.clienteRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
