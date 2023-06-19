package com.petsup.api.repositories;

import com.petsup.api.entities.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
    List<Favorito> findAllByFkCliente(Integer idCliente);

    Optional<Favorito> findByFkClienteAndFkPetshop(Integer idCliente, Integer idPetshop);
}
