package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.dto.UsuarioClienteDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid UsuarioClienteDto usuarioDto){
        this.usuarioService.criarCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioCliente>> getClientes(){
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();
        return usuarios.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCliente> getUserById(@PathVariable Integer id){
        return ResponseEntity.of(this.clienteRepository.findById(id));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        this.clienteRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }
}
