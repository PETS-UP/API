package com.petsup.api.controllers;

import com.google.maps.model.GeocodingResult;
import com.petsup.api.entities.AvaliacaoPetshop;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.service.GeocodingService;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.autentication.dto.ClienteLoginDto;
import com.petsup.api.service.autentication.dto.ClienteTokenDto;
import com.petsup.api.service.autentication.dto.PetshopDeatlhesDto;
import com.petsup.api.service.dto.*;
import com.petsup.api.util.DetalhesEndereco;
import com.petsup.api.util.PetshopAvaliacao;
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
import java.util.Optional;

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

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

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

        return ResponseEntity.ok().body(usuarioTokenDto);
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

        return usuariosDto.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(usuariosDto);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o cliente a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioClienteDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioMapper.ofClienteDto(this.clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        )));
    }

    @GetMapping("/busca-email/{email}")
    public ResponseEntity<UsuarioClienteDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(UsuarioMapper.ofClienteDto(this.clienteRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        )));
    }

    @ApiResponse(responseCode = "204", description = "Retorna conteúdo vazio após deletar o cliente.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/avaliar/{idCliente}/{idPetshop}")
    public ResponseEntity<AvaliacaoPetshop> postAvaliacao(@RequestBody @Valid AvaliacaoPetshop avl,
                                                          @PathVariable int idCliente, @PathVariable int idPetshop) {


        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);
        UsuarioCliente cliente = clienteOptional.get();
        UsuarioPetshop petshop = petshopOptional.get();
        avl.setFkPetshop(petshop);
        avl.setFkCliente(cliente);

        this.usuarioService.avaliarPetshop(avl);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/avaliacao/{idCliente}/{idPetshop}")
    public ResponseEntity<AvaliacaoDto> retornaAvaliacaoCliente(@PathVariable int idCliente,
                                                                @PathVariable int idPetshop){
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new RuntimeException("Petshop não encontrado")
        );

        Optional<AvaliacaoPetshop> avaliacaoPetshop = avaliacaoRepository
                .findByFkClienteAndFkPetshop(idCliente, idPetshop);

        if (avaliacaoPetshop.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(AvaliacaoMapper.ofAvaliacaoDto(avaliacaoPetshop.get()));
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

    @PatchMapping("/latitude-longitude/{id}/{latitude}/{longitude}")
    public ResponseEntity<Void> updateLocalizacaoAtual(@PathVariable Integer id,
                                                       @PathVariable double latitude, @PathVariable double longitude) {
        UsuarioCliente usuarioCliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(latitude, longitude, usuarioCliente);
        clienteRepository.save(usuarioAtt);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/petshops-proximos/{id}")
    public ResponseEntity<List<UsuarioPetshopDto>> retornaPetshopsNoBairroDoCliente(@PathVariable Integer id) {
        String bairro = "";

        UsuarioCliente usuarioCliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        // Conversao reversa (lat/long -> endereco)
        String results = geocodingService.reverseGeocode(usuarioCliente.getLatitude(),
                                                                    usuarioCliente.getLongitude());

        if (results.isBlank()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(results);

        DetalhesEndereco detalhesEndereco = geocodingService.extrairBairroCidade(results);

        System.out.println(detalhesEndereco.getNeighborhood() + detalhesEndereco.getCity());

        List<UsuarioPetshop> petshops = petshopRepository.findAllByBairroAndCidade(detalhesEndereco.getNeighborhood(),
                detalhesEndereco.getCity());
        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();
        for (int i = 0; i < petshops.size(); i++){
        petshopsDto.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }

        return ResponseEntity.ok().body(petshopsDto);
        //return ResponseEntity.ok().build();

    }

    @GetMapping("/ordenar-media-avaliacao")
    public ResponseEntity<List<PetshopAvaliacao>> getPetshopsPorMedia(){
        List<PetshopAvaliacao> avaliacoes = petshopRepository.ordenarMediaAvaliacao();

        if (avaliacoes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
//        avaliacoes.get(0).getFkPetshop()
//        for (int i = 0;){
//
//        }

        return ResponseEntity.ok().body(avaliacoes);
    }

    @GetMapping("/ordenar-media-preco")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshopsPorMenorPreco(){
        List<UsuarioPetshop> petshops = petshopRepository.ordenarPorPreco();
        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();

        if (petshops.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        for (int i = 0; i < petshops.size(); i++){
            petshopsDto.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
            System.out.println(petshopsDto.get(i).getNome());
        }

        return ResponseEntity.ok().body(petshopsDto);
    }


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
