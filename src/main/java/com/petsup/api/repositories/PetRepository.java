package com.petsup.api.repositories;

import com.petsup.api.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByFkClienteId(int fkCliente);
}
