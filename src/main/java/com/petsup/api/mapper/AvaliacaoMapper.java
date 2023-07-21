package com.petsup.api.mapper;

import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.models.AvaliacaoPetshop;

public class AvaliacaoMapper {
    public static AvaliacaoDto ofAvaliacaoDto(AvaliacaoPetshop avaliacao){
        AvaliacaoDto avaliacaoDto = new AvaliacaoDto();

        avaliacaoDto.setId(avaliacao.getId());
        avaliacaoDto.setNota(avaliacao.getNota());

        return avaliacaoDto;
    }
}
