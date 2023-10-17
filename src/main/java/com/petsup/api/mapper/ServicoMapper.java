package com.petsup.api.mapper;

import com.petsup.api.dto.servico.ServicoRespostaDto;
import com.petsup.api.models.petshop.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicoMapper {

    public static ServicoRespostaDto ofServicoRespostaDto(Servico servico) {
        ServicoRespostaDto servicoRespostaDto = new ServicoRespostaDto();

        servicoRespostaDto.setId(servico.getId());
        servicoRespostaDto.setNome(servico.getNome().toString());
        servicoRespostaDto.setPreco(String.format("R$%.2f", servico.getPreco()));
        servicoRespostaDto.setDescricao(servico.getDescricao());

        return servicoRespostaDto;
    }

    public static List<ServicoRespostaDto> ofListaServicoRespostaDto(List<Servico> servicos) {
        List<ServicoRespostaDto> listaServicoRespostaDto = new ArrayList<>();

        for (int i = 0; i < servicos.size(); i++) {
            listaServicoRespostaDto.add(ofServicoRespostaDto(servicos.get(i)));
        }

        return listaServicoRespostaDto;
    }
}
