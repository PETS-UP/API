package com.petsup.api.service.dto;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.Pet;

public class AgendamentoMapper {
    public static AgendamentoDto ofAgendamentoDto(Agendamento agendamento) {
        AgendamentoDto agendamentoDto = new AgendamentoDto();

        agendamentoDto.setId(agendamento.getId());
        agendamentoDto.setDataHora(agendamento.getDataHora());
        agendamentoDto.setNomeCliente(agendamento.getFkCliente().getNome());
        agendamentoDto.setEmailCliente(agendamento.getFkCliente().getEmail());
        agendamentoDto.setNomePetshop(agendamento.getFkPetshop().getNome());
        agendamentoDto.setNomePet(agendamento.getFkPet().getNome());
        agendamentoDto.setNomePet(agendamento.getFkPet().getNome());
        agendamentoDto.setEspecie(agendamento.getFkPet().getEspecie());
        agendamentoDto.setRaca(agendamento.getFkPet().getRaca());
        agendamentoDto.setSexo(agendamento.getFkPet().getSexo());
        agendamentoDto.setPreco(agendamento.getFkServico().getPreco());

        return agendamentoDto;
    }
}
