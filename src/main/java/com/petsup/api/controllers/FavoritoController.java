package com.petsup.api.controllers;

import com.petsup.api.models.Favorito;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.services.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 POST:   /favoritos/{idCliente}/{idPetshop}
 GET:    /favoritos/{idCliente}
 GET:    /favoritos/{idCliente}/favoritado/{idPetshop}
 DELETE: /favoritos/{idCliente}/{idPetshop}
*/

@RestController
@RequestMapping("/favoritos/{idCliente}")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/{idPetshop}")
    public ResponseEntity<Void> postFavorito(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        favoritoService.postFavorito(idCliente, idPetshop);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioPetshopDto>> getFavoritos(@PathVariable Integer idCliente){
        List<UsuarioPetshopDto> usuarioPetshopDtos = favoritoService.getFavoritos(idCliente);

        if (usuarioPetshopDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarioPetshopDtos);
    }

    @GetMapping("/favoritado/{idPetshop}")
    public ResponseEntity<Boolean> isFavoritado(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        return ResponseEntity.ok(favoritoService.isFavoritado(idCliente, idPetshop));
    }

    @DeleteMapping("/{idPetshop}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        favoritoService.deleteFavorito(idCliente, idPetshop);
        return ResponseEntity.noContent().build();
    }
}