package com.petsup.api.util;

import com.petsup.api.models.Agendamento;
import com.petsup.api.dto.AgendamentoDto;
import com.petsup.api.mapper.AgendamentoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdenacaoAgendametos {

    public static Optional<AgendamentoDto> pesquisaBinaria(ListaObj<AgendamentoDto> agendamentos, LocalDateTime dataHoraAgendamento) {
        int inicio = 0;
        int fim = agendamentos.getTamanho() - 1;
        Optional<AgendamentoDto> agendamentoDtoOptional;

        do {
            int meio = (inicio + fim) / 2;
            if (dataHoraAgendamento.isEqual(agendamentos.getElemento(meio).getDataHora())) {
                agendamentoDtoOptional = Optional.of(agendamentos.getElemento(meio));
                return agendamentoDtoOptional;
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

    public static ListaObj<AgendamentoDto> ordenaListaAgendamento(List<Agendamento> listaAgendamentos) {

        ListaObj<AgendamentoDto> listaLocal = new ListaObj(listaAgendamentos.size());

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            listaLocal.adiciona(AgendamentoMapper.ofAgendamentoDto(listaAgendamentos.get(i)));
        }

        int i, j, indMenor;
        for (i = 0; i < listaAgendamentos.size() - 1; i++) {
            indMenor = i;
            for (j = i + 1; j < listaAgendamentos.size(); j++) {
                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(indMenor).getDataHora())) {
                    indMenor = j;
                }
            }
            AgendamentoDto ag = listaLocal.getElemento(indMenor);
            //ag = listaLocal.getElemento(i);
            listaLocal.removeDeixaNulo(indMenor);
            listaLocal.adicionaNoNulo(indMenor, listaLocal.getElemento(i));
            listaLocal.removeDeixaNulo(i);
            listaLocal.adicionaNoNulo(i, ag);
        }
        return listaLocal;
    }
}
