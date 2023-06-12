package com.petsup.api.repositories;

import com.petsup.api.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByFkClienteId(int fkCliente);
    @Query("SELECT p FROM Pet p JOIN p.cliente c WHERE c.id = :clienteId AND p.nome = :nomePet")
    Pet filterPet (int fkCliente, String nomePet);
}
