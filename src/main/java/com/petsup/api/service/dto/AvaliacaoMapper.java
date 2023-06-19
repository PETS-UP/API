package com.petsup.api.service.dto;

import com.petsup.api.entities.AvaliacaoPetshop;

public class AvaliacaoMapper {
    public static AvaliacaoDto ofAvaliacaoDto(AvaliacaoPetshop avaliacao){
        AvaliacaoDto avaliacaoDto = new AvaliacaoDto();

        avaliacaoDto.setId(avaliacao.getId());
        avaliacaoDto.setNota(avaliacao.getNota());

        return avaliacaoDto;
    }
}
