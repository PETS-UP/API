package com.petsup.api.services;

import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.mapper.AgendamentoMapper;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.cliente.Pet;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.PetRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.util.ListaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;
import static com.petsup.api.util.OrdenacaoAgendametos.pesquisaBinaria;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public void postAgendamento(LocalDateTime dataHora,
                                Integer idCliente, Integer idPetshop,
                                Integer idPet, Integer idServico) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Pet shop não encontrado", null)
        );
        Pet pet = petRepository.findById(idPet).orElseThrow(
                () -> new ResponseStatusException(404, "Pet não encontrado", null)
        );
        Servico servico = servicoRepository.findById(idServico).orElseThrow(
                () -> new ResponseStatusException(404, "Serviço não encontrado", null)
        );
        Agendamento agendamento = new Agendamento();
        agendamento.setFkCliente(cliente);
        agendamento.setFkPetshop(petshop);
        agendamento.setFkPet(pet);
        agendamento.setFkServico(servico);
        agendamento.setDataHora(dataHora);
        agendamentoRepository.save(agendamento);
    }

    public List<AgendamentoRespostaDto> getAgendamentosByIdPetshop(Integer idPetshop) {
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Pet shop não encontrado", null)
        );
        return AgendamentoMapper.ofListaAgendamentoRespostaDto(agendamentoRepository.findByFkPetshopId(idPetshop));
    }

    public List<AgendamentoRespostaDto> getAgendamentosByIdCliente(Integer idCliente) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        return AgendamentoMapper.ofListaAgendamentoRespostaDto(agendamentoRepository.findByFkClienteId(idCliente));
    }

    public AgendamentoRespostaDto findAgendamentoByData(LocalDateTime dataHora, Integer usuario) {
        List<Agendamento> agendamentos = agendamentoRepository.findByFkPetshopId(usuario);
        ListaObj<Agendamento> listaLocal = ordenaListaAgendamento(agendamentos);
        AgendamentoRespostaDto agendamentoRespostaDto = pesquisaBinaria(listaLocal, dataHora).orElseThrow(
                () -> new ResponseStatusException(404, "Agendamento não encontrado", null)
        );
        return agendamentoRespostaDto;
    }

    public List<AgendamentoRespostaDto> findAgendamentosDoDia(LocalDateTime dataHora, Integer idPetshop) {
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Pet shop não encontrado", null)
        );
        // Define o início do dia (00:00:00)
        LocalDateTime inicioDoDia = dataHora.with(LocalTime.MIN);

        // Define o fim do dia (23:59:59)
        LocalDateTime fimDoDia = dataHora.with(LocalTime.MAX);

        List<Agendamento> agendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, inicioDoDia, fimDoDia
        );

        List<AgendamentoRespostaDto> agendamentoRespostaDtos = AgendamentoMapper.ofListaAgendamentoRespostaDto(agendamentos);
        return agendamentoRespostaDtos;
    }

    public AgendamentoRespostaDto getAgendamentoById(Integer id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(404, "Agendamento não encontrado", null)
        );
        return AgendamentoMapper.ofAgendamentoRespostaDto(agendamento);
    }
}
