package com.petsup.api.controllers;

import com.google.maps.model.GeocodingResult;
import com.petsup.api.entities.AvaliacaoPetshop;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.service.GeocodingService;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.autentication.dto.ClienteLoginDto;
import com.petsup.api.service.autentication.dto.ClienteTokenDto;
import com.petsup.api.service.autentication.dto.PetshopDeatlhesDto;
import com.petsup.api.service.dto.UsuarioClienteDto;
import com.petsup.api.service.dto.UsuarioClienteLocalizacaoDto;
import com.petsup.api.service.dto.UsuarioMapper;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Clientes", description = "Requisições relacionadas a clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetshopRepository petshopRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GeocodingService geocodingService;

    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.")
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> postUserCliente(@RequestBody @Valid UsuarioClienteDto usuarioDto) {
        this.usuarioService.criarCliente(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenDto> login(@RequestBody ClienteLoginDto usuarioLoginDto) {
        ClienteTokenDto usuarioTokenDto = this.usuarioService.autenticarCliente(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso não haja clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<UsuarioClienteDto>> getClientes() {
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();
        List<UsuarioClienteDto> usuariosDto = new ArrayList<>();

        for (UsuarioCliente usuario : usuarios) {
            usuariosDto.add(UsuarioMapper.ofClienteDto(usuario));
        }

        return usuariosDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(usuariosDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCliente> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        ));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.clienteRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/avaliar")
    public ResponseEntity<AvaliacaoPetshop> postAvaliacao(@RequestBody @Valid AvaliacaoPetshop avl) {
        this.usuarioService.avaliarPetshop(avl);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioClienteDto> updateById(
            @RequestBody @Valid UsuarioClienteDto usuarioDto,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(usuarioService.atualizarClientePorId(usuarioDto, id));
    }

    @PatchMapping("/latitude-longitude/{id}")
    public ResponseEntity<Void> updateLocalizacaoAtual(@PathVariable Integer id,
                                                       @RequestBody UsuarioClienteLocalizacaoDto usuarioClienteLocalizacaoDto) {
        UsuarioCliente usuarioCliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(usuarioClienteLocalizacaoDto, usuarioCliente);
        clienteRepository.save(usuarioAtt);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/petshops-proximos")
    public ResponseEntity<List<UsuarioPetshopDto>> retornaPetshopsNoBairroDoCliente(@PathVariable Integer id,
                                                                                    @RequestBody UsuarioClienteLocalizacaoDto
                                                                                             usuarioClienteLocalizacaoDto) {
        String bairro = "";

        UsuarioCliente usuarioCliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        // Conversao reversa (lat/long -> endereco)
//        GeocodingResult[] results = geocodingService.reverseGeocode(usuarioCliente.getLatitude(),
//                                                                    usuarioCliente.getLongitude());
//
//        if (results.length == 0) {
//            return ResponseEntity.noContent().build();
//        }
//
//        for (GeocodingResult result : results) {
//            System.out.println(geocodingService.extrairBairro(result)) ;
//            // Faça o que for necessário com o bairro (neighborhood) obtido
//        }
//
//        for (GeocodingResult result : results) {
//            bairro = geocodingService.extrairBairro(result);
//            // Faça o que for necessário com o bairro (neighborhood) obtido
//        }
//        List<UsuarioPetshop> petshops = petshopRepository.findAllByBairro(bairro);
//        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();
//        for (int i = 0; i < petshops.size(); i++){
//        petshopsDto.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
//        }

//        return ResponseEntity.ok().body(petshopsDto);
        return ResponseEntity.ok().build();

    }

    @Scheduled(cron = "5/5 * * * * *")
    public void aaa(){
        String results = geocodingService.reverseGeocode(7.193871,
                -4.925752);
//        for (String result : results) {
            System.out.println(results);
            // Faça o que for necessário com o bairro (neighborhood) obtido
        //}
    }
}
