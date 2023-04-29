package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    List<Agendamento> findByFkPetshopId(int fkPetshop);
    List<Agendamento> findByFkClienteId(int fkCliente);
}
