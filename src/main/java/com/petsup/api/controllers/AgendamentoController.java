package com.petsup.api.controllers;

import com.petsup.api.models.Agendamento;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.dto.AgendamentoDto;
import com.petsup.api.mapper.AgendamentoMapper;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.services.AgendamentoService;
import com.petsup.api.util.ListaObj;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

import java.time.LocalTime;
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
    public ResponseEntity<AgendamentoRespostaDto> findAgendamentoByData(@RequestParam LocalDateTime dataHora, @PathVariable Integer usuario) {

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(usuario);
        ListaObj<AgendamentoDto> listaLocal = ordenaListaAgendamento(listaAgendamentos);
        Optional<AgendamentoDto> agendamentoDtoOptional = pesquisaBinaria(listaLocal, dataHora);

        if (agendamentoDtoOptional.isPresent()) {
            return ResponseEntity.status(200).body(agendamentoDtoOptional.get());
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/report/dia/{idPetshop}")
    public ResponseEntity<List<AgendamentoDto>> encontrarAgendamentosDoDia(@RequestParam LocalDateTime dataHora,
                                                                             @PathVariable Integer idPetshop){

        Optional<UsuarioPetshop> usuarioPetshopOptional = petshopRepository.findById(idPetshop);

        if (usuarioPetshopOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        // Define o início do dia (00:00:00)
        LocalDateTime inicioDoDia = dataHora.with(LocalTime.MIN);

        // Define o fim do dia (23:59:59)
        LocalDateTime fimDoDia = dataHora.with(LocalTime.MAX);

        List<Agendamento> listaAgendamentos = agendamentoRepository.findAllByFkPetshopIdAndDataHoraBetween(
                idPetshop, inicioDoDia, fimDoDia
        );

        if (listaAgendamentos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<AgendamentoDto> agendamentoDtos = new ArrayList<>();

        for (int i = 0; i < listaAgendamentos.size(); i ++){
            agendamentoDtos.add(AgendamentoMapper.ofAgendamentoDto(listaAgendamentos.get(i)));
        }

        return ResponseEntity.ok(agendamentoDtos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o agendamento a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoRespostaDto> getAgendamentoById(@PathVariable Integer id) {
        return ResponseEntity.ok(AgendamentoMapper.ofAgendamentoRespostaDto(agendamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Agendamento não encontrado"))
        ));
    }

//    @PostMapping("/upload")
//    public ResponseEntity<Void> uploadMultipleAgendamento(@RequestParam("arquivo") MultipartFile file){
//        Path diretorioBase;
//        List<AgendamentoDto> listaTxt;
//        List<Agendamento> listaAgendamentos = new ArrayList<>();
//        if(file.isEmpty()){
//            return ResponseEntity.status(400).build();
//        }
//        listaTxt =  (List<AgendamentoDto>) GeradorTxt.leArquivoTxt(file);
//        for (AgendamentoDto elemento: listaTxt) {
//            Agendamento newAg = new Agendamento();
//            newAg.setDataHora(elemento.getDataHora());
//            newAg.setFkCliente(clienteRepository.findByEmail(elemento.getEmailCliente()).get());
//            newAg.setFkPet(petRepository.filterPet(clienteRepository.findByEmail(elemento.getEmailCliente()).get().getId(), elemento.getNomePet()));
//            newAg.setFkPetshop(petshopRepository.findAllByNomeLike(elemento.getNomePetshop()).get(1));
//            List listaServicos = petshopRepository.findAllByNomeLike(elemento.getNomePetshop()).get(1).getServicos();
//            Servico servico = null;
//            for (int i = 0; i < listaServicos.size(); i++) {
//                if(listaServicos.get(i).equals(elemento.getServico())){
//                    servico = (Servico) listaServicos.get(i);
//                    break;
//                }
//            }
//            newAg.setFkServico(servico);
//
//            listaAgendamentos.add(newAg);
//            agendamentoRepository.save(newAg);
//        }
//
//        return ResponseEntity.status(201).build();
//    }
}
