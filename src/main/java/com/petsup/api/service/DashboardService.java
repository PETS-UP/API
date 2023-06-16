package com.petsup.api.service;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

//    public List<Integer> getAgendamentosUltimaSemana() {
//        List<Agendamento> agendamentos = agendamentoRepository.findAllByDataHoraBetween(
//                LocalDateTime.now().minusDays(7L), LocalDateTime.now()
//        );
//        List<Integer> qtdAgendamentos;
//        int contador;
//
//        for (int i = 0; i < agendamentos.size(); i++) {
//            Long toLong = (long) i;
//        }
//        agendamentos.stream().filter(agendamento -> agendamento.getDataHora().isEqual(LocalDateTime.now().minusDays(7L)))
//
//        return agendamentoRepository.findCountAgendamentoByDataHoraBetween(
//                LocalDateTime.now().minusDays(7L), LocalDateTime.now()
//        );
//    }
}
