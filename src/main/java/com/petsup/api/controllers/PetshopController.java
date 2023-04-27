package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ClientePetshopSubscriber;
import com.petsup.api.entities.ListaObj;
import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.dto.*;
import com.petsup.api.service.autentication.dto.PetshopLoginDto;
import com.petsup.api.service.autentication.dto.PetshopTokenDto;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import com.petsup.api.util.GeradorCsv;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Tag(name = "Petshops", description = "Requisições relacionadas a petshops")
@RestController
@RequestMapping("/petshops")
public class PetshopController {

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClientePetshopSubscriberRepository clientePetshopSubscriberRepository;

    //Crud inicio
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "201", description =
            "Petshop cadastrado com sucesso.", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> postUserPetshop(@RequestBody @Valid UsuarioPetshopDto usuarioDto) {
        this.usuarioService.criarPetshop(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<PetshopTokenDto> login(@RequestBody PetshopLoginDto usuarioLoginDto) {
        PetshopTokenDto usuarioTokenDto = this.usuarioService.autenticarPetshop(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping
    @ApiResponse(responseCode = "204", description =
            "Não há petshops cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshops encontrados.")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshops() {
        List<UsuarioPetshop> usuarios = this.petshopRepository.findAll();
        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();

        for (UsuarioPetshop petshop : usuarios) {
            petshopsDto.add(UsuarioMapper.ofPetshopDto(petshop));
        }

        return petshopsDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(petshopsDto);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshop> getUserById(@PathVariable Integer id) {
        return ResponseEntity.of(this.petshopRepository.findById(id));
    }

    @DeleteMapping
    @ApiResponse(responseCode = "204", description = "Petshop deletado.")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.petshopRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody UsuarioPetshop usuario){
        UsuarioPetshop updateUser = this.petshopRepository.save(usuario);
        return ResponseEntity.status(200).body(updateUser);
    }

    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/preco")
    public ResponseEntity<Servico> updatePreco(@RequestBody ServicoDto servicoAtt, @RequestParam Integer idServico,
                                               @RequestParam Integer idPetshop) {
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Optional<UsuarioPetshop> petshopOptional = servicoRepository.findByFkPetshop(idPetshop);
        Optional<ClientePetshopSubscriber> clientePetshopSubscriberOptional =
                clientePetshopSubscriberRepository.findByFkPetshopId(idPetshop);

        if (servicoOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        Servico servico = servicoOptional.get();
        UsuarioPetshop petshop = petshopOptional.get();
        ClientePetshopSubscriber cps = clientePetshopSubscriberOptional.get();

        if (servico.getPreco() > servicoAtt.getPreco()){
            for (int i = 0; i < cps.getFkPetshop().getInscritos().size(); i++){
            petshop.notifica(petshop.getEmail(), cps.getFkCliente().getEmail());
            }
        }

        servico.setPreco(servicoAtt.getPreco());
        servicoRepository.save(servico);
        return ResponseEntity.status(200).body(servico);
    }

    //Crud fim

    @GetMapping("/report/arquivo/{id}")
    @ApiResponse(responseCode = "201", description = "Relatório gravado.")
    public ResponseEntity<Void> gerarReport(@PathVariable int id) {
        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(id);

        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());

        for (int i = 0; i < as.size(); i++) {
            listaLocal.adiciona(as.get(i));
        }
        GeradorCsv.gravaArquivoCsv(listaLocal);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/download/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos")
    public ResponseEntity<byte[]> download(@PathVariable int id){
        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
        ListaObj<Agendamento> agendamentos = new ListaObj<>(list.size());
            //Transfere elementos de list para agendamentos
            for (int i = 0; i < list.size(); i++) {
                agendamentos.adiciona(list.get(i));
            }
        GeradorCsv.gravaArquivoCsv(agendamentos);
        return GeradorCsv.buscaArquivoCsv();
    }

    @GetMapping("/report/{usuario}")
    @ApiResponse(responseCode = "204", description =
            "Retorna uma lista de agendamentos vazia.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos em ordem crescente de data.")
    @ApiResponse(responseCode = "404", description = "Não há petshops com esse identificador.")
    public ResponseEntity<ListaObj<AgendamentoDto>> ordenarAgendamentosPorData(@PathVariable Integer usuario) {

        Optional<UsuarioPetshop> usuarioPetshopOptional = petshopRepository.findById(usuario);

        if (usuarioPetshopOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioPetshop usuarioPetshop = usuarioPetshopOptional.get();

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(usuario);

        if (listaAgendamentos.size() == 0) {
            return ResponseEntity.status(204).build();
        }

        ListaObj<AgendamentoDto> listaLocal = ordenaListaAgendamento(listaAgendamentos);

        return ResponseEntity.status(200).body(listaLocal);
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

    public static Optional<AgendamentoDto> pesquisaBinaria(ListaObj<AgendamentoDto> agendamentos, LocalDateTime dataHoraAgendamento) {
        int inicio = 0;
        int fim = agendamentos.getTamanho() - 1;
        Optional<AgendamentoDto> agendamentoDtoOptional;

        do {
            int meio = (inicio + fim) / 2;
            if (dataHoraAgendamento.isEqual(agendamentos.getElemento(meio).getDataHora())) {
                agendamentoDtoOptional = Optional.of(agendamentos.getElemento(meio));
                return agendamentoDtoOptional;
            } else {
                if (dataHoraAgendamento.isAfter(agendamentos.getElemento(meio).getDataHora())) {
                    inicio = meio + 1;
                } else {
                    fim = meio - 1;
                }
            }
        } while (inicio <= fim);
        return Optional.empty();
    }

    public static ListaObj<AgendamentoDto> ordenaListaAgendamento(List<Agendamento> listaAgendamentos) {

        ListaObj<AgendamentoDto> listaLocal = new ListaObj(listaAgendamentos.size());

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            listaLocal.adiciona(AgendamentoMapper.ofAgendamentoDto(listaAgendamentos.get(i)));
        }

        int i, j, indMenor;
        for (i = 0; i < listaAgendamentos.size() - 1; i++) {
            indMenor = i;
            for (j = i + 1; j < listaAgendamentos.size(); j++) {
                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(indMenor).getDataHora())) {
                    indMenor = j;
                }
            }
            AgendamentoDto ag = listaLocal.getElemento(indMenor);
            //ag = listaLocal.getElemento(i);
            listaLocal.removeDeixaNulo(indMenor);
            listaLocal.adicionaNoNulo(indMenor, listaLocal.getElemento(i));
            listaLocal.removeDeixaNulo(i);
            listaLocal.adicionaNoNulo(i, ag);
        }
        return listaLocal;
    }
}
