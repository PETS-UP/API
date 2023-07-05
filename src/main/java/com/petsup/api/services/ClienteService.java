package com.petsup.api.services;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.DetalhesEnderecoDto;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.cliente.UsuarioClienteDto;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.util.FilaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GeocodingService geocodingService;

    FilaObj<AvaliacaoPetshop> filaAvaliacao = new FilaObj(1000);

    public void criarCliente(UsuarioClienteDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.ofCliente(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
    }

    public ClienteTokenDto autenticarCliente(ClienteLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        UsuarioCliente usuarioAutenticado =
                clienteRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.ofCliente(usuarioAutenticado, token);
    }

    public UsuarioClienteDto atualizarClientePorId(UsuarioClienteDto usuarioClienteDto, Integer id) {
        Optional<UsuarioCliente> usuarioCliente = clienteRepository.findById(id);
        if (usuarioCliente.isEmpty()){
            return null;
        }

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(usuarioClienteDto, usuarioCliente.get());
        clienteRepository.save(usuarioAtt);

        return UsuarioMapper.ofClienteDto(usuarioAtt);
    }

    public List<UsuarioClienteDto> buscarClientes(){
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();

        List<UsuarioClienteDto> usuariosDto = new ArrayList<>();

        for (UsuarioCliente usuario : usuarios) {
            usuariosDto.add(UsuarioMapper.ofClienteDto(usuario));
        }

        return usuariosDto;
    }

    public AvaliacaoPetshop avaliarPetshop(AvaliacaoPetshop avl, UsuarioCliente cliente, UsuarioPetshop petshop){
        avl.setFkPetshop(petshop);
        avl.setFkCliente(cliente);

        filaAvaliacao.insert(avl);
        return avl;
    }

    @Scheduled(cron = "5/5 * * * * *")
    public void gravarAvaliacoes(){
//        System.out.println("TESTE");
        for (int i = 0; i < filaAvaliacao.getTamanho(); i++){
            avaliacaoRepository.save(filaAvaliacao.poll());
        }
    }

    public List<UsuarioPetshopDto> retornaPetshopsNoBairroDoCliente(Optional<UsuarioCliente> clienteOptional){
        String bairro = "";

        // Conversao reversa (lat/long -> endereco)
        String results = geocodingService.reverseGeocode(clienteOptional.get().getLatitude(),
                clienteOptional.get().getLongitude());

        if (results.isBlank()) {
            return null;
        }

        System.out.println(results);

        DetalhesEnderecoDto detalhesEnderecoDto = geocodingService.extrairBairroCidade(results);

        System.out.println(detalhesEnderecoDto.getNeighborhood() + detalhesEnderecoDto.getCity());

        List<UsuarioPetshop> petshops = petshopRepository.findAllByBairroAndCidade(detalhesEnderecoDto.getNeighborhood(),
                detalhesEnderecoDto.getCity());

        if (petshops.isEmpty()) {
            return null;
        }

        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();
        for (int i = 0; i < petshops.size(); i++) {
            petshopsDto.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }

        return petshopsDto;
    }
}
