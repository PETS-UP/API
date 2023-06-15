package com.petsup.api.builder;

import com.petsup.api.entities.Servico;
import com.petsup.api.entities.enums.NomeServico;
import com.petsup.api.service.dto.ServicoDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;

public class ServicoBuilder {
    public static Servico buildServico() {
        Servico servico = new Servico();
        servico.setId(1);
        servico.setFkPetshop(UsuarioPetshopBuilder.buildUsuarioPetshop());
        servico.setNome(NomeServico.BANHO);
        servico.setPreco(14.90);
        servico.setDescricao("Banho teste");
        servico.setAgendamentos(emptyList());

        return servico;
    }

    public static ServicoDto buildServicoDto() {
        ServicoDto servicoDto = new ServicoDto();
        servicoDto.setNome(NomeServico.BANHO.toString());
        servicoDto.setPreco(14.90);
        servicoDto.setDescricao("Banho teste");

        return servicoDto;
    }

    public static List<Servico> buildListaServico() {
        List<Servico> lista = new ArrayList<>();
        Servico servico1 = buildServico();
        Servico servico2 = buildServico();
        Servico servico3 = buildServico();
        servico2.setId(2);
        servico3.setId(3);

        lista.add(servico1);
        lista.add(servico2);
        lista.add(servico3);

        return lista;
    }
}
