package com.petsup.api.util;

import com.petsup.api.models.Agendamento;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.mapper.AgendamentoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrdenacaoAgendametos {

    public static Optional<AgendamentoRespostaDto> pesquisaBinaria(ListaObj<AgendamentoRespostaDto> agendamentos, LocalDateTime dataHoraAgendamento) {
        int inicio = 0;
        int fim = agendamentos.getTamanho() - 1;
        Optional<AgendamentoRespostaDto> agendamentoRespostaDtoOptional;

        do {
            int meio = (inicio + fim) / 2;
            if (dataHoraAgendamento.isEqual(agendamentos.getElemento(meio).getDataHora())) {
                agendamentoRespostaDtoOptional = Optional.of(agendamentos.getElemento(meio));
                return agendamentoRespostaDtoOptional;
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

    public static ListaObj<AgendamentoRespostaDto> ordenaListaAgendamento(List<Agendamento> listaAgendamentos) {

        ListaObj<AgendamentoRespostaDto> listaLocal = new ListaObj(listaAgendamentos.size());

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            listaLocal.adiciona(AgendamentoMapper.ofAgendamentoRespostaDto(listaAgendamentos.get(i)));
        }

        int i, j, indMenor;
        for (i = 0; i < listaAgendamentos.size() - 1; i++) {
            indMenor = i;
            for (j = i + 1; j < listaAgendamentos.size(); j++) {
                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(indMenor).getDataHora())) {
                    indMenor = j;
                }
            }
            AgendamentoRespostaDto ag = listaLocal.getElemento(indMenor);
            //ag = listaLocal.getElemento(i);
            listaLocal.removeDeixaNulo(indMenor);
            listaLocal.adicionaNoNulo(indMenor, listaLocal.getElemento(i));
            listaLocal.removeDeixaNulo(i);
            listaLocal.adicionaNoNulo(i, ag);
        }
        return listaLocal;
    }
}
