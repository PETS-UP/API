package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ListaObj;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.service.dto.UsuarioClienteDto;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.dto.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/newClient")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid UsuarioClienteDto usuarioDto){
        this.usuarioService.criarCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/newPetshop")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postUserPetshop(@RequestBody @Valid UsuarioPetshopDto usuarioDto){
        this.usuarioService.criarPetshop(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public  ResponseEntity<List<Usuario>> getUsers(){
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        return usuarios.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id){
        return ResponseEntity.of(this.usuarioRepository.findById(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        this.usuarioRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario){
        Usuario updateUser = this.usuarioRepository.save(usuario);
        return ResponseEntity.status(200).body(updateUser);
    }

}
