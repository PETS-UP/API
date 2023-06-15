package com.petsup.api.service.dto;

import com.petsup.api.entities.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicoMapper {

    public static ServicoDto ofServicoDto(Servico servico) {
        ServicoDto servicoDto = new ServicoDto();

        servicoDto.setNome(servico.getNome());
        servicoDto.setPreco(servico.getPreco());
        servicoDto.setDescricao(servico.getDescricao());

        return servicoDto;
    }

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
