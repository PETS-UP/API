package com.petsup.api.controllers.petshop;

import com.petsup.api.dto.AgendamentoDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.ServicoDto;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.ClienteSubscriber;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.enums.NomeServico;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.ClienteSubscriberRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.services.PetshopService;
import com.petsup.api.services.UsuarioService;
import com.petsup.api.util.GeradorCsv;
import com.petsup.api.util.GeradorTxt;
import com.petsup.api.util.ListaObj;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;

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
    private PetshopService petshopService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteSubscriberRepository clienteSubscriberRepository;

    @Autowired
    private JavaMailSender enviador;

    //Crud inicio
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "201", description =
            "Petshop cadastrado com sucesso.", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> postUserPetshop(@RequestBody @Valid UsuarioPetshopDto usuarioDto) {
        this.petshopService.criarPetshop(usuarioDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<PetshopTokenDto> login(@RequestBody PetshopLoginDto usuarioLoginDto) {
        PetshopTokenDto usuarioTokenDto = this.petshopService.autenticarPetshop(usuarioLoginDto);

        return ResponseEntity.ok(usuarioTokenDto);
    }

    @GetMapping
    @ApiResponse(responseCode = "204", description =
            "Não há petshops cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshops encontrados.")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshops() {
        List<UsuarioPetshopDto> petshopsDto = petshopService.listarPetshops();

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/busca/nome")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshopsByNome(@RequestParam String nome) {
        List<UsuarioPetshopDto> petshopsDto = petshopService.listarPetshopsPorNome(nome);

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshopDto> getPetshopById(@PathVariable Integer id) {
        return ResponseEntity.ok(petshopService.getPetshopById(id));
    }

    @GetMapping("/busca-email/{email}")
    @ApiResponse(responseCode = "204", description = "Petshops não encontrado.",
            content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshopDto> getPetshopByEmail(@PathVariable String email) {
        return ResponseEntity.ok(petshopService.getPetshopByEmail(email));
    }

    @DeleteMapping
    @ApiResponse(responseCode = "204", description = "Petshop deletado.")
    @ApiResponse(responseCode = "404", description = "Petshop não encontrado.")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        petshopService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<UsuarioPetshopDto> updatePetshop(@PathVariable Integer idPetshop,
                                                           @RequestBody UsuarioPetshopDto usuarioPetshopDto) {
        return ResponseEntity.ok(petshopService.updatePetshop(idPetshop, usuarioPetshopDto));
    }

    // Método para atualizar preços fica na controller
    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/servico")
    public ResponseEntity<ServicoRespostaDto> updateServico(@RequestBody ServicoDto servicoAtt, @RequestParam Integer idServico,
                                                            @RequestParam Integer idPetshop) {
        return ResponseEntity.ok(petshopService.atualizarServico(servicoAtt, idServico, idPetshop));
    }

    //Crud fim

//    @GetMapping("/report/arquivo/csv/{id}")
//    @ApiResponse(responseCode = "201", description = "Relatório gravado em CSV.")
//    public ResponseEntity<Void> reportCsv(@PathVariable int id) {
//        petshopService.gerarRelatorioCsv(id);
//        return ResponseEntity.status(201).build();
//    }

    @GetMapping("/download/csv/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em CSV.")
    public ResponseEntity<Resource> downloadCsv(@PathVariable int id) {
        if (petshopRepository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
        if (list.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ListaObj<Agendamento> agendamentos = new ListaObj<>(list.size());

        //Transfere elementos de list para agendamentos
        for (int i = 0; i < list.size(); i++) {
            agendamentos.adiciona(list.get(i));
        }

        File file = GeradorCsv.gravaArquivoCsv(agendamentos);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=agendamentos.csv");

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download/txt/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em TXT.")
    public ResponseEntity<Resource> downloadTxt(@PathVariable int id) throws FileNotFoundException {
        if (petshopRepository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
        if (list.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ListaObj<Agendamento> agendamentos = new ListaObj<>(list.size());

        //Transfere elementos de list para agendamentos
        for (int i = 0; i < list.size(); i++) {
            agendamentos.adiciona(list.get(i));
        }

        File file = GeradorTxt.gravaArquivoTxt(agendamentos);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=agendamentos.txt");

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ApiResponse(responseCode = "201", description = "Inscrição realizada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado.")
    @PostMapping("/inscrever/{idPetshop}")
    public ResponseEntity<Void> subscribeToPetshop(@PathVariable Integer idPetshop, @RequestParam Integer idCliente) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);
        if (petshopOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioCliente usuarioCliente = clienteOptional.get();
        UsuarioPetshop usuarioPetshop = petshopOptional.get();

        ClienteSubscriber inscrito = new ClienteSubscriber();
        inscrito.setFkCliente(usuarioCliente);
        inscrito.setFkPetshop(usuarioPetshop);

        clienteSubscriberRepository.save(inscrito);

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
            return ResponseEntity.notFound().build();
        }

        UsuarioPetshop usuarioPetshop = usuarioPetshopOptional.get();

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(usuario);

        if (listaAgendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        ListaObj<AgendamentoDto> listaLocal = ordenaListaAgendamento(listaAgendamentos);

        return ResponseEntity.ok(listaLocal);
    }

}
