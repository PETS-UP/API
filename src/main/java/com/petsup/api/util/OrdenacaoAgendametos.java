package com.petsup.api.util;

import com.petsup.api.models.Agendamento;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.mapper.AgendamentoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdenacaoAgendametos {

    public static Optional<AgendamentoRespostaDto> pesquisaBinaria(ListaObj<Agendamento> agendamentos, LocalDateTime dataHoraAgendamento) {
        int inicio = 0;
        int fim = agendamentos.getTamanho() - 1;
        Optional<AgendamentoRespostaDto> agendamentoOptional;

        do {
            int meio = (inicio + fim) / 2;
            if (dataHoraAgendamento.isEqual(agendamentos.getElemento(meio).getDataHora())) {
                agendamentoOptional = Optional.of(AgendamentoMapper.ofAgendamentoRespostaDto(agendamentos.getElemento(meio)));
                return agendamentoOptional;
            } else {
                if (dataHoraAgendamento.isAfter(agendamentos.getElemento(meio).getDataHora())) {
                    inicio = meio + 1;
                } else {
                    fim = meio - 1;
                }
            }
        } while (inicio <= fim);
        return Optional.empty();
    }

    public static ListaObj<Agendamento> ordenaListaAgendamento(List<Agendamento> listaAgendamentos) {

        ListaObj<Agendamento> listaLocal = new ListaObj(listaAgendamentos.size());

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            listaLocal.adiciona(listaAgendamentos.get(i));
        }

        int i, j, indMenor;
        for (i = 0; i < listaAgendamentos.size() - 1; i++) {
            indMenor = i;
            for (j = i + 1; j < listaAgendamentos.size(); j++) {
                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(indMenor).getDataHora())) {
                    indMenor = j;
                }
            }
            Agendamento ag = listaLocal.getElemento(indMenor);
            //ag = listaLocal.getElemento(i);
            listaLocal.removeDeixaNulo(indMenor);
            listaLocal.adicionaNoNulo(indMenor, listaLocal.getElemento(i));
            listaLocal.removeDeixaNulo(i);
            listaLocal.adicionaNoNulo(i, ag);
        }
        return listaLocal;
    }
}
