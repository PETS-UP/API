package com.petsup.api.controllers.petshop;

import com.petsup.api.dto.AgendamentoDto;
import com.petsup.api.dto.petshop.ServicoDto;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.cliente.ClienteSubscriber;
import com.petsup.api.models.enums.NomeServico;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.ClienteSubscriberRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.util.ListaObj;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.services.UsuarioService;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.util.GeradorCsv;
import com.petsup.api.util.GeradorTxt;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
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
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody UsuarioPetshopDto usuarioPetshopDto) {
        UsuarioPetshop usuarioPetshop = petshopRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Petshop não encontrado"));

        UsuarioPetshop usuarioPetshopAtt = UsuarioMapper.ofPetshop(usuarioPetshopDto, usuarioPetshop);
        petshopRepository.save(usuarioPetshopAtt);
        return ResponseEntity.ok(usuarioPetshopAtt);
    }

    // Método para atualizar preços fica na controller
    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/servico")
    public ResponseEntity<ServicoDto> updateServico(@RequestBody ServicoDto servicoAtt, @RequestParam Integer idServico,
                                                    @RequestParam Integer idPetshop) {
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);
        //Optional<ClienteSubscriber> clientePetshopSubscriberOptional =
        //        clienteSubscriberRepository.findByFkPetshopId(idPetshop);

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

        servico.setNome(NomeServico.valueOf(servicoAtt.getNome()));
        servico.setPreco(servicoAtt.getPreco());
        servico.setDescricao(servicoAtt.getDescricao());
        servicoRepository.save(servico);
        return ResponseEntity.ok(servicoAtt);
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

//    @GetMapping("/report/arquivo/txt/{id}")
//    @ApiResponse(responseCode = "201", description = "Relatório gravado em TXT.")
//    public ResponseEntity<Resource> gerarReportTxt(@PathVariable int id) {
//        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(id);
////
//        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());
////
//        for (int i = 0; i < as.size(); i++) {
//            listaLocal.adiciona(as.get(i));
//        }
////        GeradorTxt.gravaArquivoTxt(listaLocal);
////        return ResponseEntity.status(201).build();
//
//            String nomeArq = "agendamentos";
//            byte[] encodedBytes;
//            try {
//                encodedBytes = Files.readAllBytes(GeradorTxt.gravaArquivoTxt(listaLocal, nomeArq).toPath());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            InputStream inputStream = new ByteArrayInputStream( new String(encodedBytes, StandardCharsets.UTF_8)
//                    .getBytes(StandardCharsets.UTF_8));
//
//            Resource resource = new InputStreamResource(inputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", nomeArq);
//
//            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//        }

    @GetMapping("/download/csv/{id}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em CSV.")
    public ResponseEntity<Resource> downloadCsv(@PathVariable int id) {
        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
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
        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(id);
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
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
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
