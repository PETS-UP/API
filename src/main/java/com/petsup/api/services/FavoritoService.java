package com.petsup.api.services;

import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.models.Favorito;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.FavoritoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;


    public void postFavorito(Integer idCliente, Integer idPetshop) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        Favorito favorito = new Favorito();

        favorito.setFkCliente(cliente);
        favorito.setFkPetshop(petshop);

        favoritoRepository.save(favorito);
    }

        public List<UsuarioPetshopDto> getFavoritos(Integer idCliente){
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        List<Favorito> favoritos = favoritoRepository.findAllByFkClienteId(idCliente);
        List<UsuarioPetshop> petshops = new ArrayList<>();
        List<UsuarioPetshopDto> petshopDetalhesDtos = new ArrayList<>();

        for (int i = 0; i < favoritos.size(); i ++){
            petshops.add(favoritos.get(i).getFkPetshop());
            petshopDetalhesDtos.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }

        return petshopDetalhesDtos;
    }

    public Boolean isFavoritado(Integer idCliente, Integer idPetshop){
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        Optional<Favorito> favoritoOptional = favoritoRepository.findByFkClienteIdAndFkPetshopId(idCliente,
                idPetshop);

        if (favoritoOptional.isEmpty()){
            return false;
        }

        return true;
    }

    public void deleteFavorito(Integer idCliente, Integer idPetshop){
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        Optional<Favorito> optionalFavorito = favoritoRepository.findByFkClienteIdAndFkPetshopId(idCliente, idPetshop);
        Favorito favorito = optionalFavorito.get();

        favoritoRepository.delete(favorito);
    }
}