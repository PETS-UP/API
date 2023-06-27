package com.petsup.api.services;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
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

    FilaObj<AvaliacaoPetshop> filaAvaliacao = new FilaObj(1000);

    public void criarCliente(UsuarioClienteDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.ofCliente(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
    }

    public void criarPetshop(UsuarioPetshopDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.ofPetshop(usuarioDto);

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

    public PetshopTokenDto autenticarPetshop(PetshopLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        UsuarioPetshop usuarioAutenticado =
                petshopRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.ofPetshop(usuarioAutenticado, token);
    }
  
    public UsuarioClienteDto atualizarClientePorId(UsuarioClienteDto usuarioClienteDto, Integer id) {
        UsuarioCliente usuarioCliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(usuarioClienteDto, usuarioCliente);
        clienteRepository.save(usuarioAtt);
        return UsuarioMapper.ofClienteDto(usuarioAtt);
    }

    public AvaliacaoPetshop avaliarPetshop(AvaliacaoPetshop avl){
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
}
