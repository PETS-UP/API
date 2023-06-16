package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    List<Agendamento> findByFkPetshopId(int fkPetshop);
    List<Agendamento> findByFkClienteId(int fkCliente);
    List<Agendamento> findAllByDataHoraBetween(LocalDateTime diaAnterior, LocalDateTime diaAtual);

//    Integer findCountAgendamentoByDataHoraBetween(LocalDateTime diaAnterior, LocalDateTime diaAtual);
//    Double findCount
}
