package com.petsup.api.controllers.cliente;

import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.petshop.PetshopMediaPrecoDto;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.ClienteDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.services.GeocodingService;
import com.petsup.api.services.cliente.ClienteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 POST:   /clientes
 POST:   /clientes/login
 GET:    /clientes
 GET:    /clientes/{idCliente}
 GET:    /clientes/busca-email/{email}
 PATCH:  /clientes/{idCliente}
 DELETE: /clientes/{idCliente}
 POST:   /clientes/avaliar/{idCliente}/{idPetshop}
 GET:    /clientes/avaliar/{idCliente}/{idPetshop}
 GET:    /clientes/ordenar-media-avaliacao
 GET:    /clientes/ordenar-media-preco
 PATCH:  /clientes/latitude-longitude/{idCliente}/{latitude}/{longitude}
 GET:    /clientes/petshops-proximos/{idCliente}
*/

@Tag(name = "Clientes", description = "Requisições relacionadas a clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private GeocodingService geocodingService;

    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid ClienteDto usuarioDto) {
        this.clienteService.postCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenDto> login(@RequestBody ClienteLoginDto usuarioLoginDto) {
        return ResponseEntity.ok().body(clienteService.authenticateCliente(usuarioLoginDto));
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso não haja clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> getClientes() {
        List<ClienteDto> clienteDtos = clienteService.findClientes();

        if (clienteDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(clienteDtos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/tk/{idCliente}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(clienteService.getClienteById(idCliente));
    }

    @PostMapping("/tk/adicionar-pfp/{idCliente}")
    public ResponseEntity<Boolean> postProfilePicture(@PathVariable Integer idCliente,
                                                      @RequestParam MultipartFile image) throws IOException {
        if (this.clienteService.postProfilePicture(idCliente, image)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/tk/retornar-blob/{idCliente}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int idCliente) {
        byte[] response = this.clienteService.getProfilePicture(idCliente);

        if (response.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/tk/retornar-imagem/{idCliente}")
    public ResponseEntity<String> getImage(@PathVariable int idCliente) {
        return ResponseEntity.ok(clienteService.getImage(idCliente));
    }

    @PutMapping("/tk/atualizar-imagem/{idCliente}")
    public ResponseEntity<Boolean> updateImage(@PathVariable int idCliente,
                                               @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(clienteService.updateImage(idCliente, image));
    }

    @DeleteMapping("/tk/deletar-imagem/{idCliente}")
    public ResponseEntity<String> deleteImage(@PathVariable int idCliente) {
        if (clienteService.deleteImage(idCliente)){
            return ResponseEntity.ok("Imagem deletada");
        }

        return ResponseEntity.internalServerError().body("Erro ao deletar imagem");
    }

    @GetMapping("/tk/busca-email/{email}")
    public ResponseEntity<ClienteDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.getUserByEmail(email));
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @PatchMapping("/tk/{idCliente}")
    public ResponseEntity<ClienteDto> updateClienteById(@RequestBody ClienteDto usuarioDto,
                                                        @PathVariable Integer idCliente) {
        return ResponseEntity.ok(clienteService.updateClienteById(usuarioDto, idCliente));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping("/tk/{idCliente}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idCliente) {
        clienteService.deleteById(idCliente);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tk/avaliar/{idCliente}/{idPetshop}")
    public ResponseEntity<Void> postAvaliacao(@RequestBody @Valid AvaliacaoPetshop avl, @PathVariable int idCliente,
                                              @PathVariable int idPetshop) {
        clienteService.postAvaliacao(avl, idCliente, idPetshop);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/tk/avaliacao/{idCliente}/{idPetshop}")
    public ResponseEntity<AvaliacaoDto> getAvaliacaoCliente(@PathVariable int idCliente,
                                                            @PathVariable int idPetshop) {
        return ResponseEntity.ok(clienteService.getAvaliacaoCliente(idCliente, idPetshop));
    }

    @GetMapping("/tk/ordenar-media-avaliacao")
    public ResponseEntity<List<PetshopAvaliacaoDto>> getPetshopsOrderByMedia() {
        List<PetshopAvaliacaoDto> petshopAvaliacaoDtos = clienteService.getPetshopsOrderByMedia();

        if (petshopAvaliacaoDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(petshopAvaliacaoDtos);
    }

    @GetMapping("/tk/ordenar-media-preco")
    public ResponseEntity<List<PetshopMediaPrecoDto>> getPetshopsOrderByPrecoAsc() {
        List<PetshopMediaPrecoDto> petshops = clienteService.getPetshopsOrderByPrecoAsc();

        if (petshops.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(petshops);
    }

    @PatchMapping("/tk/latitude-longitude/{idCliente}/{latitude}/{longitude}")
    public ResponseEntity<Void> updateLocalizacaoAtual(@PathVariable Integer idCliente, @PathVariable double latitude,
                                                       @PathVariable double longitude) {
        clienteService.updateLocalizacaoAtual(idCliente, latitude, longitude);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tk/petshops-proximos/{idCliente}")
    public ResponseEntity<List<PetshopDto>> getPetshopsByClienteBairro(@PathVariable Integer idCliente) {
        List<PetshopDto> petshopDtos = clienteService.getPetshopsByClienteBairro(idCliente);

        if (petshopDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(petshopDtos);
    }

// Método de teste API Maps
//    @Scheduled(cron = "5/5 * * * * *")
//    public void aaa(){
//        String results = geocodingService.reverseGeocode(7.193871,
//                -4.925752);
////        for (String result : results) {
//        geocodingService.extrairBairroCidade(results);
//            // Faça o que for necessário com o bairro (neighborhood) obtido
//        //}
//    }
}
