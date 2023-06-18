package com.petsup.api.service;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.Servico;
import com.petsup.api.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Integer> getAgendamentosUltimaSemana(int idPetshop) {
        List<Agendamento> agendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, LocalDateTime.now().minusDays(6L), LocalDateTime.now()
        );
        List<Integer> qtdAgendamentos = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Long toLong = (long) i;
            List<Agendamento> aux = agendamentos.stream().filter(agendamento -> agendamento.getDataHora()
                    .toLocalDate().isEqual(LocalDate.now()
                            .minusDays(toLong))).collect(Collectors.toList());
            qtdAgendamentos.add(aux.size());
        }

        Collections.reverse(qtdAgendamentos);

        return qtdAgendamentos;
    }

    public String getDiaMaisMovimentado(int idPetshop) {
        List<Integer> qtdAgendamentos = getAgendamentosUltimaSemana(idPetshop);
        int diaMaisMovimentado = 0;
        int valorCount = 0;

        for (int i = 0; i < qtdAgendamentos.size(); i++) {
            if (valorCount < qtdAgendamentos.get(i)) {
                diaMaisMovimentado = i;
                valorCount = qtdAgendamentos.get(i);
            }
        }

        Locale locale = new Locale("pt", "BR");
        LocalDateTime dataDesejada = LocalDateTime.now().minusDays(6L).plusDays(diaMaisMovimentado);
        String output = dataDesejada.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);

        return output.replaceFirst("\\b\\p{L}", output.substring(0, 1).toUpperCase());
    }

    public String getDiaMenosMovimentado(int idPetshop) {
        List<Integer> qtdAgendamentos = getAgendamentosUltimaSemana(idPetshop);
        int diaMenosMovimentado = 0;
        int valorCount = 999;

        for (int i = 0; i < qtdAgendamentos.size(); i++) {
            if (valorCount > qtdAgendamentos.get(i)) {
                diaMenosMovimentado = i;
                valorCount = qtdAgendamentos.get(i);
            }
        }

        Locale locale = new Locale("pt", "BR");
        LocalDateTime dataDesejada = LocalDateTime.now().minusDays(6L).plusDays(diaMenosMovimentado);
        String output = dataDesejada.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);

        return output.replaceFirst("\\b\\p{L}", output.substring(0, 1).toUpperCase());
    }

    public List<Double> getAgendamentosUltimosMeses(int idPetshop) {
        List<Agendamento> agendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, LocalDateTime.now().withDayOfMonth(1).minusMonths(5L),
                LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).minusMonths(1L)
        );

        List<Double> valorServicoAgendamentosPorMes = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Long toLong = (long) i;
            Double somaValorServico = agendamentos.stream().filter(agendamento -> agendamento.getDataHora()
                    .toLocalDate().isAfter(LocalDate.now()
                            .minusMonths(toLong)))
                    .mapToDouble(agendamento -> agendamento.getFkServico().getPreco())
                    .reduce(0.0, Double::sum);
            valorServicoAgendamentosPorMes.add(somaValorServico);
        }

        return valorServicoAgendamentosPorMes;
    }

    public Double getRendaEsteMes(int idPetshop){
        List<Agendamento> agendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, LocalDateTime.now().withDayOfMonth(1),
                LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
        );
        Double valorTotal = 0.0;

        for (int i = 0; i < agendamentos.size(); i++) {
            valorTotal += agendamentos.get(i).getFkServico().getPreco();
        }

        return valorTotal;
    }

    public String getServicoMaisAgendadoMesAtual(int idPetshop){
        List<Agendamento> agendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, LocalDateTime.now().withDayOfMonth(1),
                LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
        );

        int countBanho = 0;
        int countTosa = 0;
        int countBanhoTosa = 0;

        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getFkServico().getNome().toString().equals("BANHO")){
                countBanho ++;
            } else if (agendamentos.get(i).getFkServico().getNome().toString().equals("TOSA")) {
                countTosa ++;
            } else {
                countBanhoTosa++;
            }
        }

        int maior = Math.max(countBanho, Math.max(countTosa, countBanhoTosa));

        if (maior == countBanho) {
            return "Banho";
        } else if (maior == countTosa) {
            return "Tosa";
        } else {
            return "Banho&Tosa";
        }
    }
}
