package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.autentication.dto.ClienteLoginDto;
import com.petsup.api.service.autentication.dto.ClienteTokenDto;
import com.petsup.api.service.dto.UsuarioClienteDto;
import com.petsup.api.service.dto.UsuarioMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Clientes", description = "Requisições relacionadas a clientes")
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

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenDto> login(@RequestBody ClienteLoginDto usuarioLoginDto) {
        ClienteTokenDto usuarioTokenDto = this.usuarioService.autenticarCliente(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso não haja clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<UsuarioClienteDto>> getClientes(){
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();
        List<UsuarioClienteDto> usuariosDto = new ArrayList<>();

        for (UsuarioCliente usuario : usuarios) {
            usuariosDto.add(UsuarioMapper.ofClienteDto(usuario));
        }

        return usuariosDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(usuariosDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCliente> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        ));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        this.clienteRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
