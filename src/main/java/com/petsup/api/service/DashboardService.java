package com.petsup.api.service;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Integer> getAgendamentosUltimaSemana() {
        List<Agendamento> agendamentos = agendamentoRepository.findAllByDataHoraBetween(
                LocalDateTime.now().minusDays(7L), LocalDateTime.now()
        );
        List<Integer> qtdAgendamentos = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            Long toLong = (long) i;
            List<Agendamento> aux =
            agendamentos.stream().filter(agendamento -> agendamento.getDataHora().isEqual(LocalDateTime.now().minusDays(toLong)))
                    .collect(Collectors.toList());
            qtdAgendamentos.add(aux.size());
        }

        return qtdAgendamentos;
    }
}
