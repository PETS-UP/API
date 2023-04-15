package com.petsup.api.controllers;

import com.petsup.api.entities.usurario.Usuario;
import com.petsup.api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> postUser(@RequestBody @Valid Usuario usuario){
        Usuario usuarioCadastrado = (Usuario)this.usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(usuarioCadastrado);
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
