package com.petsup.api.controllers;

import com.petsup.api.dto.petshop.PetshopExibicaoDto;
import com.petsup.api.services.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 POST:   /favoritos/{idCliente}/{idPetshop}
 GET:    /favoritos/{idCliente}
 GET:    /favoritos/{idCliente}/favoritado/{idPetshop}
 DELETE: /favoritos/{idCliente}/{idPetshop}
*/

@RestController
@RequestMapping("/api/favoritos/{idCliente}")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/{idPetshop}")
    public ResponseEntity<Void> postFavorito(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        favoritoService.postFavorito(idCliente, idPetshop);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PetshopExibicaoDto>> getFavoritos(@PathVariable Integer idCliente){
        List<PetshopExibicaoDto> petshopDtos = favoritoService.getFavoritos(idCliente);

        if (petshopDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopDtos);
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