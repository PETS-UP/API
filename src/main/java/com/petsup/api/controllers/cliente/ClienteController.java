package com.petsup.api.controllers.cliente;

import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.dto.PetshopAvaliacaoDto;
import com.petsup.api.dto.PetshopMediaPrecoDto;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.UsuarioClienteDto;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.cliente.ClienteService;
import com.petsup.api.services.GeocodingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 POST:   /clientes
 POST:   /clientes/login
 GET:    /clientes
 GET:    /clientes/{idCliente}
 GET:    /clientes/busca-email/{email}
 PATCH:  /clientes/{idCliente}
 DELETE: /clientes
 POST:   /clientes/avaliar/{idCliente}/{idPetshop}
 GET:    /clientes/avaliar/{idCliente}/{idPetshop}
 GET:    /clientes/ordenar-media-avaliacao
 GET:    /clientes/ordenar-media-preco
 PATCH:  /clientes/latitude-longitude/{idCliente}/{latitude}/{longitude}
 GET:    /clientes/petshops-proximos/{idCliente}
*/



@Tag(name = "Clientes", description = "Requisições relacionadas a clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid UsuarioClienteDto usuarioDto) {
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
    public ResponseEntity<List<UsuarioClienteDto>> getClientes() {
        List<UsuarioClienteDto> usuarioClienteDtos = clienteService.findClientes();

        if (usuarioClienteDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(usuarioClienteDtos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{idCliente}")
    public ResponseEntity<UsuarioClienteDto> getClienteById(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(clienteService.getClienteById(idCliente));
    }

    @GetMapping("/busca-email/{email}")
    public ResponseEntity<UsuarioClienteDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.getUserByEmail(email));
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @PatchMapping("/{idCliente}")
    public ResponseEntity<UsuarioClienteDto> updateClienteById(@RequestBody UsuarioClienteDto usuarioDto,
                                                               @PathVariable Integer idCliente) {
        return ResponseEntity.ok(clienteService.updateClienteById(usuarioDto, idCliente));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idCliente) {
        Optional<UsuarioCliente> usuarioClienteOptional = clienteRepository.findById(idCliente);

        if (usuarioClienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            this.clienteRepository.deleteById(idCliente);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/avaliar/{idCliente}/{idPetshop}")
    public ResponseEntity<Void> postAvaliacao(@RequestBody @Valid AvaliacaoPetshop avl, @PathVariable int idCliente,
                                              @PathVariable int idPetshop) {
        clienteService.postAvaliacao(avl, idCliente, idPetshop);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/avaliacao/{idCliente}/{idPetshop}")
    public ResponseEntity<AvaliacaoDto> getAvaliacaoCliente(@PathVariable int idCliente,
                                                            @PathVariable int idPetshop) {
        return ResponseEntity.ok(clienteService.getAvaliacaoCliente(idCliente, idPetshop));
    }

    @GetMapping("/ordenar-media-avaliacao")
    public ResponseEntity<List<PetshopAvaliacaoDto>> getPetshopsOrderByMedia() {
        List<PetshopAvaliacaoDto> petshopAvaliacaoDtos = clienteService.getPetshopsOrderByMedia();

        if (petshopAvaliacaoDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(petshopAvaliacaoDtos);
    }

    @GetMapping("/ordenar-media-preco")
    public ResponseEntity<List<PetshopMediaPrecoDto>> getPetshopsOrderByPrecoAsc() {
        List<PetshopMediaPrecoDto> petshops = clienteService.getPetshopsOrderByPrecoAsc();

        if (petshops.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(petshops);
    }

    @PatchMapping("/latitude-longitude/{idCliente}/{latitude}/{longitude}")
    public ResponseEntity<Void> updateLocalizacaoAtual(@PathVariable Integer idCliente, @PathVariable double latitude,
                                                       @PathVariable double longitude) {
        clienteService.updateLocalizacaoAtual(idCliente, latitude, longitude);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/petshops-proximos/{idCliente}")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshopsByClienteBairro(@PathVariable Integer idCliente) {
        List<UsuarioPetshopDto> usuarioPetshopDtos = clienteService.getPetshopsByClienteBairro(idCliente);

        if (usuarioPetshopDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarioPetshopDtos);
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
