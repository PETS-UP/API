package com.petsup.api.repositories.cliente;

import com.petsup.api.models.cliente.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByFkClienteId(int fkCliente);
//    @Query("SELECT p FROM Pet p JOIN p.cliente c WHERE c.id = :clienteId AND p.nome = :nomePet")
//    Pet filterPet (int fkCliente, String nomePet);
}
