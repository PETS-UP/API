package com.petsup.api.service;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
            agendamentos.stream().filter(agendamento -> agendamento.getDataHora().toLocalDate().isEqual(LocalDate.now()
                            .minusDays(toLong))).collect(Collectors.toList());
            qtdAgendamentos.add(aux.size());
        }

        return qtdAgendamentos;
    }

    public String getDiaMaisMovimentado(){
        List<Integer> qtdAgendamentos = getAgendamentosUltimaSemana();
        int diaMaisMovimentado = 0;
        int valorCount = 0;

        for (int i = 0; i < qtdAgendamentos.size(); i++){
            if (valorCount < qtdAgendamentos.get(i)){
                diaMaisMovimentado = i;
                valorCount = qtdAgendamentos.get(i);
            }
        }

        Locale locale = new Locale("pt", "BR");
        LocalDateTime dataDesejada = LocalDateTime.now().minusDays(7L).plusDays(diaMaisMovimentado);
        String output = dataDesejada.getDayOfWeek().getDisplayName(TextStyle.FULL, locale).toString();

        return output.replaceFirst("\\b\\p{L}", output.substring(0, 1).toUpperCase());
    }
}
