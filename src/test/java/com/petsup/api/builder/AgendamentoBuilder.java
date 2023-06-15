package com.petsup.api.builder;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.NomeServico;
import com.petsup.api.entities.enums.Raca;
import com.petsup.api.service.dto.AgendamentoDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoBuilder {
    public static Agendamento buildAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(1);
        agendamento.setDataHora(LocalDateTime.of(2022, 1, 1, 12, 0));
        agendamento.setFkPet(PetBuilder.buildPet());
        agendamento.setFkPetshop(UsuarioPetshopBuilder.buildUsuarioPetshop());
        agendamento.setFkCliente(UsuarioClienteBuilder.buildUsuarioCliente());
        agendamento.setFkServico(ServicoBuilder.buildServico());

        return agendamento;
    }

    public static AgendamentoDto buildAgendamentoDto() {
        AgendamentoDto agendamentoDto = new AgendamentoDto();
        agendamentoDto.setDataHora(LocalDateTime.of(2022, 1, 1, 12, 0));
        agendamentoDto.setNomeCliente("Ana Beatriz");
        agendamentoDto.setEmailCliente("ana@gmail.com");
        agendamentoDto.setNomePetshop("Petshop Teste");
        agendamentoDto.setNomePet("Fluffy");
        agendamentoDto.setEspecie(Especie.CACHORRO);
        agendamentoDto.setRaca(Raca.LABRADOR);
        agendamentoDto.setSexo("M");
        agendamentoDto.setServico(NomeServico.BANHO);
        agendamentoDto.setPreco(14.90);

        return agendamentoDto;
    }

    public static List<Agendamento> buildListaAgendamento() {
        List<Agendamento> lista = new ArrayList<>();
        Agendamento agendamento1 = buildAgendamento();
        Agendamento agendamento2 = buildAgendamento();
        Agendamento agendamento3 = buildAgendamento();
        agendamento2.setId(2);
        agendamento3.setId(3);

        lista.add(agendamento1);
        lista.add(agendamento2);
        lista.add(agendamento3);

        return lista;
    }
}
