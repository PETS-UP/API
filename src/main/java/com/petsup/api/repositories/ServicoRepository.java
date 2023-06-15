package com.petsup.api.repositories;

import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findAllByFkPetshopId(int fkPetshop);
    Optional<UsuarioPetshop> findFkPetshopById(Integer integer);
}
