package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.Pet;
import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.service.dto.AgendamentoDto;
import com.petsup.api.service.dto.AgendamentoMapper;
import com.petsup.api.service.dto.AgendamentoRespostaDto;
import com.petsup.api.service.dto.PetMapper;
import com.petsup.api.util.ListaObj;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;
import static com.petsup.api.util.OrdenacaoAgendametos.pesquisaBinaria;

@Tag(name = "Agendamentos", description = "Requisições relacionadas a agendamentos.")
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

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

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Entidade não encontrada.")
    @PostMapping
    public ResponseEntity<Void> postAgendamento(@RequestBody @Valid Agendamento agendamento,
                                                @RequestParam Integer idCliente, @RequestParam Integer idPetshop,
                                                @RequestParam Integer idPet, @RequestParam Integer idServico) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);
        Optional<Pet> petOptional = petRepository.findById(idPet);
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);

        if (clienteOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"
            );
        } else if (petshopOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Petshop não encontrado"
            );
        } else if (petOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pet não encontrado"
            );
        } else if (servicoOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Serviço não encontrado"
            );
        }

        UsuarioCliente cliente = clienteOptional.get();
        UsuarioPetshop petshop = petshopOptional.get();
        Pet pet = petOptional.get();
        Servico servico = servicoOptional.get();

        agendamento.setFkCliente(cliente);
        agendamento.setFkPetshop(petshop);
        agendamento.setFkPet(pet);
        agendamento.setFkServico(servico);
        agendamentoRepository.save(agendamento);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao pet shop.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o pet shop não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado.")
    @GetMapping("/petshop")
    public ResponseEntity<List<AgendamentoRespostaDto>> getAgendamentosByIdPetshop(@RequestParam Integer idPetshop) {

        if (petshopRepository.findById(idPetshop).isPresent()) {
            List<Agendamento> agendamentos = agendamentoRepository.findByFkPetshopId(idPetshop);
            List<AgendamentoRespostaDto> agendamentoDtos = AgendamentoMapper.ofListaAgendamentoRespostaDto(agendamentos);

            return agendamentoDtos.isEmpty() ? ResponseEntity.status(204).build()
                    : ResponseEntity.status(200).body(agendamentoDtos);
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao cliente.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o cliente não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @GetMapping("/cliente")
    public ResponseEntity<List<AgendamentoRespostaDto>> getAgendamentosByIdCliente(@RequestParam Integer idCliente) {

        if (clienteRepository.findById(idCliente).isPresent()) {
            List<Agendamento> agendamentos = agendamentoRepository.findByFkClienteId(idCliente);
            List<AgendamentoRespostaDto> agendamentoDtos = AgendamentoMapper.ofListaAgendamentoRespostaDto(agendamentos);

            return agendamentoDtos.isEmpty() ? ResponseEntity.status(204).build()
                    : ResponseEntity.status(200).body(agendamentoDtos);
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna o agendamento com a data e hora especificada.")
    @GetMapping("report/agendamento/{usuario}")
    public ResponseEntity<AgendamentoDto> encontrarAgendamentoPorData(@RequestParam LocalDateTime dataHora, @PathVariable Integer usuario) {

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(usuario);
        ListaObj<AgendamentoDto> listaLocal = ordenaListaAgendamento(listaAgendamentos);
        Optional<AgendamentoDto> agendamentoDtoOptional = pesquisaBinaria(listaLocal, dataHora);

        if (agendamentoDtoOptional.isPresent()) {
            return ResponseEntity.status(200).body(agendamentoDtoOptional.get());
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna o agendamento a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")

    @GetMapping("{id}")
    public ResponseEntity<AgendamentoRespostaDto> getAgendamentoById(@PathVariable Integer id) {
        return ResponseEntity.ok(AgendamentoMapper.ofAgendamentoRespostaDto(agendamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Agendamento não encontrado"))
        ));
    }
}
