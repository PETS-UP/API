package com.petsup.api.controllers;

import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.services.AgendamentoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;

/*
 POST:  /agendamentos
 GET:   /agendamentos/petshop
 GET:   /agendamentos/cliente
 GET:   /agendamentos/report/agendamento/{usuario}
 GET:   /agendamentos/report/dia/{idPetshop}
 GET:   /agendamentos/{id}
 */

@Tag(name = "Agendamentos", description = "Requisições relacionadas a agendamentos.")
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Entidade não encontrada.")
    @PostMapping
    public ResponseEntity<Void> postAgendamento(@RequestParam LocalDateTime dataHora,
                                                @RequestParam Integer idCliente, @RequestParam Integer idPetshop,
                                                @RequestParam Integer idPet, @RequestParam Integer idServico) {
        agendamentoService.postAgendamento(dataHora, idCliente, idPetshop, idPet, idServico);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Entidade não encontrada.")
    @PostMapping("/v2")
    public ResponseEntity<Void> postAgendamento(@RequestParam String dataHora,
                                                @RequestParam Integer idCliente, @RequestParam Integer idPetshop,
                                                @RequestParam Integer idPet, @RequestParam Integer idServico) {
        agendamentoService.postAgendamento(dataHora, idCliente, idPetshop, idPet, idServico);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao pet shop.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o pet shop não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado.")
    @GetMapping("/petshop")
    public ResponseEntity<List<AgendamentoRespostaDto>> getAgendamentosByIdPetshop(@RequestParam Integer idPetshop) {
        List<AgendamentoRespostaDto> agendamentos = agendamentoService.getAgendamentosByIdPetshop(idPetshop);

        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agendamentos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao cliente.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o cliente não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @GetMapping("/cliente")
    public ResponseEntity<List<AgendamentoRespostaDto>> getAgendamentosByIdCliente(@RequestParam Integer idCliente) {
        List<AgendamentoRespostaDto> agendamentos = agendamentoService.getAgendamentosByIdCliente(idCliente);

        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agendamentos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o agendamento com a data e hora especificada.")
    @GetMapping("/report/agendamento/{usuario}")
    public ResponseEntity<AgendamentoRespostaDto> findAgendamentoByData(@RequestParam LocalDateTime dataHora,
                                                                        @PathVariable Integer usuario) {
        AgendamentoRespostaDto agendamento = agendamentoService.findAgendamentoByData(dataHora, usuario);
        return ResponseEntity.ok(agendamento);
    }

    @GetMapping("/report/dia/{idPetshop}")
    public ResponseEntity<List<AgendamentoRespostaDto>> findAgendamentosDoDia(@RequestParam LocalDateTime dataHora,
                                                                              @PathVariable Integer idPetshop) {
        List<AgendamentoRespostaDto> agendamentos = agendamentoService.findAgendamentosDoDia(dataHora, idPetshop);

        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }                                             
        return ResponseEntity.ok(agendamentos);                           
    }

    @ApiResponse(responseCode = "200", description = "Retorna o agendamento a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoRespostaDto> getAgendamentoById(@PathVariable Integer id) {
        AgendamentoRespostaDto agendamento = agendamentoService.getAgendamentoById(id);
        return ResponseEntity.ok(agendamento);
    }
}
