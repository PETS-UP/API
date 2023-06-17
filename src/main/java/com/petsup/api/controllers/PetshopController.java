package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ClientePetshopSubscriber;
import com.petsup.api.service.dto.*;
import com.petsup.api.util.ListaObj;
import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.autentication.dto.PetshopLoginDto;
import com.petsup.api.service.autentication.dto.PetshopTokenDto;
import com.petsup.api.util.GeradorCsv;
import com.petsup.api.util.GeradorTxt;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;
import static com.petsup.api.util.OrdenacaoAgendametos.pesquisaBinaria;

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
    private ClienteRepository clienteRepository;

    @Autowired
    private ClientePetshopSubscriberRepository clientePetshopSubscriberRepository;

    @Autowired
    private JavaMailSender enviador;

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

    @GetMapping("/busca/nome")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshopsByNome(@RequestParam String nome) {
        List<UsuarioPetshop> petshops = petshopRepository.findAllByNomeLike(nome);
        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();

        if (petshops.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (UsuarioPetshop usuarioPetshop : petshops) {
            petshopsDto.add(UsuarioMapper.ofPetshopDto(usuarioPetshop));
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshopDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(UsuarioMapper.ofPetshopDto(petshopRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Petshop não encontrado"))
        ));
    }

    @GetMapping("/busca-email/{email}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshop> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(this.petshopRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        ));
    }

    @DeleteMapping
    @ApiResponse(responseCode = "204", description = "Petshop deletado.")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.petshopRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody UsuarioPetshop usuario) {
        UsuarioPetshop updateUser = this.petshopRepository.save(usuario);
        return ResponseEntity.status(200).body(updateUser);
    }

    // Método para atualizar preços fica na controller
    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/preco")
    public ResponseEntity<ServicoDto> updatePreco(@RequestBody ServicoDto servicoAtt, @RequestParam Integer idServico,
                                                  @RequestParam Integer idPetshop) {
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);
        //Optional<ClientePetshopSubscriber> clientePetshopSubscriberOptional =
        //        clientePetshopSubscriberRepository.findByFkPetshopId(idPetshop);

        if (servicoOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        Servico servico = servicoOptional.get();
        UsuarioPetshop petshop = petshopOptional.get();

        // Observer
        if (servico.getPreco() > servicoAtt.getPreco()) {
            for (int i = 0; i < petshop.getInscritos().size(); i++) {
                petshop.atualiza(enviador, petshop.getInscritos().get(i).getFkCliente().getEmail(),
                        petshop.getEmail(), servicoAtt.getPreco()); // Chamada do método de atualização na
                // entidade observada (publisher)
            }
        }
        // Observer

        servico.setPreco(servicoAtt.getPreco());
        servicoRepository.save(servico);
        return ResponseEntity.status(200).body(servicoAtt);
    }

    //Crud fim

    @GetMapping("/report/arquivo/csv/{id}")
    @ApiResponse(responseCode = "201", description = "Relatório gravado em CSV.")
    public ResponseEntity<Void> gerarReportCsv(@PathVariable int id) {
        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(id);

        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());

        for (int i = 0; i < as.size(); i++) {
            listaLocal.adiciona(as.get(i));
        }
        GeradorCsv.gravaArquivoCsv(listaLocal);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/report/arquivo/txt/{id}")
    @ApiResponse(responseCode = "201", description = "Relatório gravado em TXT.")
    public ResponseEntity<Void> gerarReportTxt(@PathVariable int id) {
        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(id);

        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());

        for (int i = 0; i < as.size(); i++) {
            listaLocal.adiciona(as.get(i));
        }
        GeradorTxt.gravaArquivoTxt(listaLocal);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/download/csv/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em CSV.")
    public ResponseEntity<byte[]> downloadCsv(@PathVariable int id) {
        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
        ListaObj<Agendamento> agendamentos = new ListaObj<>(list.size());
        //Transfere elementos de list para agendamentos
        for (int i = 0; i < list.size(); i++) {
            agendamentos.adiciona(list.get(i));
        }
        GeradorCsv.gravaArquivoCsv(agendamentos);
        return GeradorCsv.buscaArquivoCsv();
    }

    @GetMapping("/download/txt/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em TXT.")
    public ResponseEntity<byte[]> downloadTxt(@PathVariable int id) {
        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
        ListaObj<Agendamento> agendamentos = new ListaObj<>(list.size());
        //Transfere elementos de list para agendamentos
        for (int i = 0; i < list.size(); i++) {
            agendamentos.adiciona(list.get(i));
        }
        GeradorTxt.gravaArquivoTxt(agendamentos);
        return GeradorTxt.buscaArquivoTxt();
    }

    @ApiResponse(responseCode = "201", description = "Inscrição realizada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado.")
    @PostMapping("/inscrever/{idPetshop}")
    public ResponseEntity<Void> subscribeToPetshop(@PathVariable Integer idPetshop, @RequestParam Integer idCliente) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);

        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado."
            );
        }

        if (petshopOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pet shop não encontrado."
            );
        }

        UsuarioCliente usuarioCliente = clienteOptional.get();
        UsuarioPetshop usuarioPetshop = petshopOptional.get();
        ClientePetshopSubscriber inscrito = new ClientePetshopSubscriber();
        inscrito.setFkCliente(usuarioCliente);
        inscrito.setFkPetshop(usuarioPetshop);
        clientePetshopSubscriberRepository.save(inscrito);

        return ResponseEntity.status(201).build();
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

}
