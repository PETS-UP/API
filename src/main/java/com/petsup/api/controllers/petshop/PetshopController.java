package com.petsup.api.controllers.petshop;

import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.dto.petshop.DiaSemanaDto;
import com.petsup.api.dto.petshop.HorariosDto;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.servico.ServicoDto;
import com.petsup.api.dto.servico.ServicoRespostaDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.models.Agendamento;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.petshop.PetshopService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public ResponseEntity<Void> postPetshop(@RequestBody @Valid PetshopDto usuarioDto) {
        this.petshopService.postPetshop(usuarioDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<PetshopTokenDto> login(@RequestBody PetshopLoginDto usuarioLoginDto) {
        PetshopTokenDto usuarioTokenDto = this.petshopService.authenticatePetshop(usuarioLoginDto);

        return ResponseEntity.ok(usuarioTokenDto);
    }

    @PostMapping("/adicionar-pfp/{idPetshop}")
    public ResponseEntity<Boolean> postProfilePicture(@PathVariable Integer idPetshop,
                                                      @RequestParam MultipartFile image) throws IOException {
        if (this.petshopService.postProfilePicture(idPetshop, image)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/retornar-blob/{idPetshop}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int idPetshop) {
        byte[] response = this.petshopService.getProfilePicture(idPetshop);

        if (response.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/retornar-imagem/{idPetshop}")
    public ResponseEntity<String> getImage(@PathVariable int idPetshop) {
        return ResponseEntity.ok(petshopService.getImage(idPetshop));
    }

    @PutMapping("/atualizar-imagem/{idPetshop}")
    public ResponseEntity<Boolean> updateImage(@PathVariable int idPetshop,
                                               @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(petshopService.updateImage(idPetshop, image));
    }

    @DeleteMapping("/deletar-imagem/{idPetshop}")
    public ResponseEntity<String> deleteImage(@PathVariable int idPetshop) {
        if (petshopService.deleteImage(idPetshop)){
            return ResponseEntity.ok("Imagem deletada");
        }

        return ResponseEntity.internalServerError().body("Erro ao deletar imagem");
    }
    @GetMapping
    @ApiResponse(responseCode = "204", description =
            "Não há petshops cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshops encontrados.")
    public ResponseEntity<List<PetshopDto>> getPetshops() {
        List<PetshopDto> petshopsDto = petshopService.listPetshops();

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/{idPetshop}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<PetshopDto> getPetshopById(@PathVariable Integer idPetshop) {
        return ResponseEntity.ok(petshopService.getPetshopById(idPetshop));
    }

    @GetMapping("/busca/nome")
    public ResponseEntity<List<PetshopDto>> getPetshopsByNome(@RequestParam String nome) {
        List<PetshopDto> petshopsDto = petshopService.getPetshopsByNome(nome);

        if (petshopsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopsDto);
    }

    @GetMapping("/busca-email/{email}")
    @ApiResponse(responseCode = "204", description = "Petshops não encontrado.",
            content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<PetshopDto> getPetshopByEmail(@PathVariable String email) {
        return ResponseEntity.ok(petshopService.getPetshopByEmail(email));
    }

    @PatchMapping("/{idPetshop}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<PetshopDto> updatePetshop(@PathVariable Integer idPetshop,
                                                    @RequestBody PetshopDto petshopDto) {
        return ResponseEntity.ok(petshopService.updatePetshop(idPetshop, petshopDto));
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

    @PostMapping("/adicionarHoraFuncionamento/{idPetshop}")
    public ResponseEntity<Void> addWorkingHours(@PathVariable Integer idPetshop, @RequestBody HorariosDto horarios){
        petshopService.adicionarHoraFuncionamento(horarios.horaAbertura(),horarios.horaFechamento(), idPetshop);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/adicionarDiasFuncionais/{idPetshop}")
    public ResponseEntity<Void> addWorkingDays(@PathVariable Integer idPetshop, @RequestBody DiaSemanaDto diasDaSemana){
        petshopService.adicionarDiasFuncionais(diasDaSemana.diasFuncionais(), idPetshop);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checarAberto/{idPetshop}")
    public ResponseEntity<Boolean> checkOpenHour(@PathVariable Integer idPetshop){
        Boolean aberto = petshopService.estaAberto(idPetshop);
        return ResponseEntity.ok(aberto);
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

    @GetMapping("/media-avaliacao")
    public ResponseEntity<List<PetshopAvaliacaoDto>> getMediaAvaliacao() {
        return ResponseEntity.ok(petshopService.getMediaAvaliacao());
    }
}
