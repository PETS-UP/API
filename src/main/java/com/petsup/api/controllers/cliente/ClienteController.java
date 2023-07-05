package com.petsup.api.controllers.cliente;

import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.dto.DetalhesEnderecoDto;
import com.petsup.api.dto.PetshopAvaliacaoDto;
import com.petsup.api.dto.PetshopMediaPrecoDto;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.UsuarioClienteDto;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.mapper.AvaliacaoMapper;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.ClienteService;
import com.petsup.api.services.GeocodingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        this.clienteService.criarCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenDto> login(@RequestBody ClienteLoginDto usuarioLoginDto) {
        ClienteTokenDto usuarioTokenDto = this.clienteService.autenticarCliente(usuarioLoginDto);

        return ResponseEntity.ok().body(usuarioTokenDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso não haja clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<UsuarioClienteDto>> getClientes() {
        List<UsuarioClienteDto> usuarioClienteDtos = this.clienteService.buscarClientes();

        if (usuarioClienteDtos.isEmpty() || usuarioClienteDtos == null) {
            return ResponseEntity.status(204).body(usuarioClienteDtos);
        }

        return ResponseEntity.ok(usuarioClienteDtos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{idCliente}")
    public ResponseEntity<UsuarioClienteDto> getUserById(@PathVariable Integer idCliente) {
        Optional<UsuarioCliente> usuarioClienteOptional = clienteRepository.findById(idCliente);

        if (usuarioClienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioClienteDto usuarioClienteDto = UsuarioMapper.ofClienteDto(usuarioClienteOptional.get());

        return ResponseEntity.ok(usuarioClienteDto);
    }

    @GetMapping("/busca-email/{email}")
    public ResponseEntity<UsuarioClienteDto> getUserByEmail(@PathVariable String email) {

        Optional<UsuarioCliente> usuarioClienteOptional = clienteRepository.findByEmail(email);

        if (usuarioClienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioClienteDto usuarioClienteDto = UsuarioMapper.ofClienteDto(usuarioClienteOptional.get());

        return ResponseEntity.ok(usuarioClienteDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @PatchMapping("/{idCliente}")
    public ResponseEntity<UsuarioClienteDto> updateById(@RequestBody UsuarioClienteDto usuarioDto,
                                                        @PathVariable Integer idCliente) {
//        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
//
//        if (clienteOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }

        UsuarioClienteDto usuarioClienteDto = clienteService.atualizarClientePorId(usuarioDto, idCliente);
        if (usuarioClienteDto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioClienteDto);
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
    public ResponseEntity<Void> postAvaliacao(@RequestBody @Valid AvaliacaoPetshop avl,
                                              @PathVariable int idCliente, @PathVariable int idPetshop) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);

        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (petshopOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clienteService.avaliarPetshop(avl, clienteOptional.get(), petshopOptional.get());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/avaliacao/{idCliente}/{idPetshop}")
    public ResponseEntity<AvaliacaoDto> retornaAvaliacaoCliente(@PathVariable int idCliente,
                                                                @PathVariable int idPetshop) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);

        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (petshopOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<AvaliacaoPetshop> avaliacaoPetshop = avaliacaoRepository
                .findByFkClienteIdAndFkPetshopId(idCliente, idPetshop);

        if (avaliacaoPetshop.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(AvaliacaoMapper.ofAvaliacaoDto(avaliacaoPetshop.get()));
    }

    @GetMapping("/ordenar-media-avaliacao")
    public ResponseEntity<List<PetshopAvaliacaoDto>> getPetshopsPorMedia() {
        List<PetshopAvaliacaoDto> avaliacoes = petshopRepository.ordenarMediaAvaliacao();

        if (avaliacoes.isEmpty() || avaliacoes == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(avaliacoes);
    }

    @GetMapping("/ordenar-media-preco")
    public ResponseEntity<List<PetshopMediaPrecoDto>> getPetshopsPorMenorPreco() {
        List<PetshopMediaPrecoDto> petshops = petshopRepository.ordenarPorPreco();

        if (petshops.isEmpty() || petshops == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(petshops);
    }

    @PatchMapping("/latitude-longitude/{idCliente}/{latitude}/{longitude}")
    public ResponseEntity<Void> updateLocalizacaoAtual(@PathVariable Integer idCliente, @PathVariable double latitude,
                                                       @PathVariable double longitude) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty() || clienteOptional == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(latitude, longitude, clienteOptional.get());
        clienteRepository.save(usuarioAtt);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/petshops-proximos/{idCliente}")
    public ResponseEntity<List<UsuarioPetshopDto>> retornaPetshopsNoBairroDoCliente(@PathVariable Integer idCliente) {

        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<UsuarioPetshopDto> usuarioPetshopDtos = clienteService.retornaPetshopsNoBairroDoCliente(clienteOptional);

        if (usuarioPetshopDtos.isEmpty() || usuarioPetshopDtos == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(usuarioPetshopDtos);
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
