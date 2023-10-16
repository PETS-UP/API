package com.petsup.api.repositories;

import com.petsup.api.models.AvaliacaoPetshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoPetshop, Integer> {
    Optional<AvaliacaoPetshop> findByFkClienteIdAndFkPetshopId(Integer idCliente, Integer idPetshop);
}
