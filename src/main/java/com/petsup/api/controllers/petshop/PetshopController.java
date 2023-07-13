package com.petsup.api.controllers.petshop;

import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.ServicoDto;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.models.Agendamento;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.petshop.PetshopService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/*
 POST:   /petshops
 POST:   /petshops/login
 GET:    /petshops
 GET:    /petshops/{idPetshop}
 GET:    /petshops/busca/nome
 GET:    /petshops/busca-email/{email}
 PATCH:  /petshops/{idPetshop}
 DELETE: /petshops/{idPetshop}
 PATCH:  /petshops/atualizar/servico
 GET:    /petshops/download/csv/{idPetshop}
 GET:    /petshops/download/txt/{idPetshop}
 POST:   /petshops/inscrever/{idPetshop}
 GET:    /petshops/report/{idPetshop}
*/

@Tag(name = "Petshops", description = "Requisições relacionadas a petshops")
@RestController
@RequestMapping("/petshops")
public class PetshopController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PetshopService petshopService;

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private JavaMailSender enviador;

    //Crud inicio
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "201", description =
            "Petshop cadastrado com sucesso.", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> postPetshop(@RequestBody @Valid UsuarioPetshopDto usuarioDto) {
        this.petshopService.postPetshop(usuarioDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<PetshopTokenDto> login(@RequestBody PetshopLoginDto usuarioLoginDto) {
        PetshopTokenDto usuarioTokenDto = this.petshopService.authenticatePetshop(usuarioLoginDto);

        return ResponseEntity.ok(usuarioTokenDto);
    }

    @GetMapping
    @ApiResponse(responseCode = "204", description =
            "Não há petshops cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshops encontrados.")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshops() {
        List<UsuarioPetshopDto> petshopsDto = petshopService.listPetshops();

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/{idPetshop}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshopDto> getPetshopById(@PathVariable Integer idPetshop) {
        return ResponseEntity.ok(petshopService.getPetshopById(idPetshop));
    }

    @GetMapping("/busca/nome")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshopsByNome(@RequestParam String nome) {
        List<UsuarioPetshopDto> petshopsDto = petshopService.getPetshopsByNome(nome);

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/busca-email/{email}")
    @ApiResponse(responseCode = "204", description = "Petshops não encontrado.",
            content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshopDto> getPetshopByEmail(@PathVariable String email) {
        return ResponseEntity.ok(petshopService.getPetshopByEmail(email));
    }

    @PatchMapping("/{idPetshop}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<UsuarioPetshopDto> updatePetshop(@PathVariable Integer idPetshop,
                                                           @RequestBody UsuarioPetshopDto usuarioPetshopDto) {
        return ResponseEntity.ok(petshopService.updatePetshop(idPetshop, usuarioPetshopDto));
    }

    @DeleteMapping
    @ApiResponse(responseCode = "204", description = "Petshop deletado.")
    @ApiResponse(responseCode = "404", description = "Petshop não encontrado.")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        petshopService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Método para atualizar preços fica na controller
    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/servico")
    public ResponseEntity<ServicoRespostaDto> updateServico(@RequestBody ServicoDto servicoAtt,
                                                            @RequestParam Integer idServico,
                                                            @RequestParam Integer idPetshop) {
        return ResponseEntity.ok(petshopService.updateServico(servicoAtt, idServico, idPetshop));
    }

    //Crud fim

//    @GetMapping("/report/arquivo/csv/{id}")
//    @ApiResponse(responseCode = "201", description = "Relatório gravado em CSV.")
//    public ResponseEntity<Void> reportCsv(@PathVariable int id) {
//        petshopService.gerarRelatorioCsv(id);
//        return ResponseEntity.status(201).build();
//    }

    @GetMapping("/download/csv/{idPetshop}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em CSV.")
    public ResponseEntity<Resource> downloadCsv(@PathVariable int idPetshop) {
        if (petshopRepository.findById(idPetshop).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(idPetshop);
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

    @GetMapping("/download/txt/{idPetshop}")
    @ApiResponse(responseCode = "200", description = "Endpoint de download de agendamentos em TXT.")
    public ResponseEntity<Resource> downloadTxt(@PathVariable int idPetshop) throws FileNotFoundException {
        if (petshopRepository.findById(idPetshop).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<Agendamento> list = agendamentoRepository.findByFkPetshopId(idPetshop);
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
        petshopService.subscribeToPetshop(idPetshop, idCliente);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/report/{idPetshop}")
    @ApiResponse(responseCode = "204", description =
            "Retorna uma lista de agendamentos vazia.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos em ordem crescente de data.")
    @ApiResponse(responseCode = "404", description = "Não há petshops com esse identificador.")
    public ResponseEntity<ListaObj<AgendamentoRespostaDto>> orderAgendamentosByDate(@PathVariable Integer idPetshop) {
        ListaObj<AgendamentoRespostaDto> agendamentoDtoListaObj = petshopService.orderAgendamentosByDate(idPetshop);

        if (agendamentoDtoListaObj.getTamanho() == 0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(agendamentoDtoListaObj);
    }

}
