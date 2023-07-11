package com.petsup.api.mapper;

import com.petsup.api.dto.AgendamentoDto;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.models.Agendamento;
import com.petsup.api.util.ListaObj;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoMapper {
    public static AgendamentoDto ofAgendamentoDto(Agendamento agendamento) {
        AgendamentoDto agendamentoDto = new AgendamentoDto();

        agendamentoDto.setDataHora(agendamento.getDataHora());
        agendamentoDto.setNomeCliente(agendamento.getFkCliente().getNome());
        agendamentoDto.setEmailCliente(agendamento.getFkCliente().getEmail());
        agendamentoDto.setNomePetshop(agendamento.getFkPetshop().getNome());
        agendamentoDto.setNomePet(agendamento.getFkPet().getNome());
        agendamentoDto.setEspecie(agendamento.getFkPet().getEspecie());
        agendamentoDto.setSexo(agendamento.getFkPet().getSexo());
        agendamentoDto.setServico(agendamento.getFkServico().getNome());
        agendamentoDto.setPreco(agendamento.getFkServico().getPreco());

        return agendamentoDto;
    }

    public static AgendamentoRespostaDto ofAgendamentoRespostaDto(Agendamento agendamento) {
        AgendamentoRespostaDto agendamentoRespostaDto = new AgendamentoRespostaDto();

        agendamentoRespostaDto.setId(agendamento.getId());
        agendamentoRespostaDto.setDataHora(agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
        agendamentoRespostaDto.setNomeCliente(agendamento.getFkCliente().getNome());
        agendamentoRespostaDto.setEmailCliente(agendamento.getFkCliente().getEmail());
        agendamentoRespostaDto.setNomePetshop(agendamento.getFkPetshop().getNome());
        agendamentoRespostaDto.setNomePet(agendamento.getFkPet().getNome());
        agendamentoRespostaDto.setEspecie(agendamento.getFkPet().getEspecie().toString());
        agendamentoRespostaDto.setSexo(agendamento.getFkPet().getSexo());
        agendamentoRespostaDto.setServico(agendamento.getFkServico().getNome().toString());
        agendamentoRespostaDto.setPreco(agendamento.getFkServico().getPreco().toString());

        return agendamentoRespostaDto;
    }

    public static List<AgendamentoRespostaDto> ofListaAgendamentoRespostaDto(List<Agendamento> agendamentos) {
        List<AgendamentoRespostaDto> listaAgendamentosDto = new ArrayList<>();

        for (int i = 0; i < agendamentos.size(); i++) {
            listaAgendamentosDto.add(ofAgendamentoRespostaDto(agendamentos.get(i)));
        }
        return listaAgendamentosDto;
    }

    public static ListaObj<AgendamentoRespostaDto> ofListaObjAgendamentoRespostaDto(ListaObj<Agendamento> agendamentos) {
        ListaObj<AgendamentoRespostaDto> listaObj = new ListaObj(agendamentos.getTamanho());

        for (int i = 0; i < agendamentos.getTamanho(); i++) {
            listaObj.adiciona(ofAgendamentoRespostaDto(agendamentos.getElemento(i)));
        }
        return listaObj;
    }
}
