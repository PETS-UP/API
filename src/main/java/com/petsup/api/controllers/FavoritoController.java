package com.petsup.api.controllers;

import com.petsup.api.entities.Favorito;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.FavoritoRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.service.autentication.dto.PetshopDeatlhesDto;
import com.petsup.api.service.dto.UsuarioMapper;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/favoritos/{idCliente}")
public class FavoritoController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioPetshopDto>> listarFavoritos(@PathVariable Integer idCliente){
        UsuarioCliente usuarioCliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        List<Favorito> favoritos = favoritoRepository.findAllByFkCliente(idCliente);
        List<UsuarioPetshop> petshops = new ArrayList<>();
        List<UsuarioPetshopDto> petshopDeatlhesDtos = new ArrayList<>();

        if (favoritos.isEmpty()){
            throw new RuntimeException("Este cliente não possui favoritos");
        }

        for (int i = 0; i < favoritos.size(); i ++){
            petshops.add(favoritos.get(i).getFkPetshop());
            petshopDeatlhesDtos.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }


        return ResponseEntity.ok(petshopDeatlhesDtos);
    }

    @PostMapping("/{idPetshop}")
    public Favorito favoritar(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        UsuarioCliente usuarioCliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        UsuarioPetshop usuarioPetshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new RuntimeException("Petshop não encontrado")
        );

        Favorito favorito = new Favorito();

        favorito.setFkCliente(usuarioCliente);
        favorito.setFkPetshop(usuarioPetshop);

        return favorito;
    }

    @DeleteMapping("/{idPetshop}")
    public ResponseEntity<Void> deletarFavorito(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){


        return null;
    }
}
