package com.petsup.api.controllers;

import com.petsup.api.entities.Favorito;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.FavoritoRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.service.dto.UsuarioMapper;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<UsuarioCliente> usuarioCliente = clienteRepository.findById(idCliente);

        if (usuarioCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Favorito> favoritos = favoritoRepository.findAllByFkClienteId(idCliente);
        List<UsuarioPetshop> petshops = new ArrayList<>();
        List<UsuarioPetshopDto> petshopDetalhesDtos = new ArrayList<>();

        if (favoritos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        for (int i = 0; i < favoritos.size(); i ++){
            petshops.add(favoritos.get(i).getFkPetshop());
            petshopDetalhesDtos.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }


        return ResponseEntity.ok(petshopDetalhesDtos);
    }

    @GetMapping("/favoritado/{idPetshop}")
    public ResponseEntity<Boolean> isFavoritado(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){

        Optional<UsuarioCliente> usuarioCliente = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> usuarioPetshop = petshopRepository.findById(idPetshop);

        if (usuarioCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        } else if(usuarioPetshop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<Favorito> favoritoOptional = favoritoRepository.findByFkClienteIdAndFkPetshopId(idCliente,
                idPetshop);

        if (favoritoOptional.isEmpty()){
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping("/{idPetshop}")
    public ResponseEntity<Void> favoritar(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        Optional<UsuarioCliente> usuarioCliente = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> usuarioPetshop = petshopRepository.findById(idPetshop);

        if (usuarioCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        } else if(usuarioPetshop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Favorito favorito = new Favorito();

        favorito.setFkCliente(usuarioCliente.get());
        favorito.setFkPetshop(usuarioPetshop.get());

        favoritoRepository.save(favorito);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idPetshop}")
    public ResponseEntity<Void> deletarFavorito(@PathVariable Integer idCliente, @PathVariable Integer idPetshop){
        Optional<UsuarioCliente> usuarioCliente = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> usuarioPetshop = petshopRepository.findById(idPetshop);

        if (usuarioCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        } else if(usuarioPetshop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<Favorito> optionalFavorito = favoritoRepository.findByFkClienteIdAndFkPetshopId(idCliente, idPetshop);
        Favorito favorito = optionalFavorito.get();

        favoritoRepository.delete(favorito);

        return ResponseEntity.noContent().build();
    }
}
