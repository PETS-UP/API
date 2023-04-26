package com.petsup.api.service.dto;

import com.petsup.api.entities.Servico;

public class ServicoMapper {

    public static ServicoDto ofServicoDto(Servico servico) {
        ServicoDto servicoDto = new ServicoDto();

        servicoDto.setNome(servico.getNome());
        servicoDto.setPreco(servico.getPreco());
        servicoDto.setDescricao(servico.getDescricao());

        return servicoDto;
    }
}
