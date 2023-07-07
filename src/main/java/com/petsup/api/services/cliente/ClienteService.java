package com.petsup.api.services.cliente;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
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
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.GeocodingService;
import com.petsup.api.util.FilaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    FilaObj<AvaliacaoPetshop> filaAvaliacao = new FilaObj(1000);
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

    public void postCliente(UsuarioClienteDto usuarioDto) {
        final Usuario novoUsuario = UsuarioMapper.ofCliente(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
    }

    public ClienteTokenDto authenticateCliente(ClienteLoginDto usuarioLoginDto) {

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

    public UsuarioClienteDto updateClienteById(UsuarioClienteDto usuarioClienteDto, Integer idCliente) {
        UsuarioCliente usuarioCliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(usuarioClienteDto, usuarioCliente);
        clienteRepository.save(usuarioAtt);

        return UsuarioMapper.ofClienteDto(usuarioAtt);
    }

    public List<UsuarioClienteDto> findClientes() {
        List<UsuarioCliente> usuarios = this.clienteRepository.findAll();

        List<UsuarioClienteDto> usuariosDto = new ArrayList<>();

        for (UsuarioCliente usuario : usuarios) {
            usuariosDto.add(UsuarioMapper.ofClienteDto(usuario));
        }

        return usuariosDto;
    }

    public UsuarioClienteDto getClienteById(Integer idCliente) {
        UsuarioCliente usuarioCliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioClienteDto usuarioClienteDto = UsuarioMapper.ofClienteDto(usuarioCliente);

        return usuarioClienteDto;
    }

    public UsuarioClienteDto getUserByEmail(String email) {
        UsuarioCliente usuarioCliente = clienteRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioClienteDto usuarioClienteDto = UsuarioMapper.ofClienteDto(usuarioCliente);

        return usuarioClienteDto;
    }

    public void deleteById(Integer idCliente) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        this.clienteRepository.deleteById(idCliente);
    }


        public AvaliacaoPetshop postAvaliacao(AvaliacaoPetshop avl, int idCliente, int idPetshop) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        avl.setFkPetshop(petshop);
        avl.setFkCliente(cliente);

        avaliacaoRepository.save(avl);
        return avl;
    }

    public AvaliacaoDto getAvaliacaoCliente(int idCliente, int idPetshop) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        AvaliacaoPetshop avaliacaoPetshop = avaliacaoRepository
                .findByFkClienteIdAndFkPetshopId(idCliente, idPetshop).orElseThrow(
                        () -> new ResponseStatusException(404, "Petshop não encontrado", null)
                );

        return AvaliacaoMapper.ofAvaliacaoDto(avaliacaoPetshop);
    }

//    @Scheduled(cron = "5/5 * * * * *")
//    public void gravarAvaliacoes(){
////        System.out.println("TESTE");
//        for (int i = 0; i < filaAvaliacao.getTamanho(); i++){
//            avaliacaoRepository.save(filaAvaliacao.poll());
//        }
//    }

    public List<PetshopAvaliacaoDto> getPetshopsOrderByMedia() {
        return petshopRepository.ordenarMediaAvaliacao();
    }

    public List<PetshopMediaPrecoDto> getPetshopsOrderByPrecoAsc() {
        List<PetshopMediaPrecoDto> petshopMediaPrecoDtos = petshopRepository.ordenarPorPreco();

        return petshopMediaPrecoDtos;
    }

    public void updateLocalizacaoAtual(Integer idCliente, double latitude, double longitude) {
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioCliente usuarioAtt = UsuarioMapper.ofCliente(latitude, longitude, cliente);
        clienteRepository.save(usuarioAtt);
    }

    public List<UsuarioPetshopDto> getPetshopsByClienteBairro(Integer idCliente) {
        String bairro = "";

        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        // Conversao reversa (lat/long -> endereco)
        String results = geocodingService.reverseGeocode(cliente.getLatitude(),
                cliente.getLongitude());

        if (results.isBlank()) {
            throw new ResponseStatusException(400, "Erro de conversão lat/long -> endereco", null);
        }

        System.out.println(results);

        DetalhesEnderecoDto detalhesEnderecoDto = geocodingService.extrairBairroCidade(results);

        System.out.println(detalhesEnderecoDto.getNeighborhood() + detalhesEnderecoDto.getCity());

        List<UsuarioPetshop> petshops = petshopRepository
                .findAllByBairroAndCidade(detalhesEnderecoDto.getNeighborhood(), detalhesEnderecoDto.getCity());

        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();

        for (int i = 0; i < petshops.size(); i++) {
            petshopsDto.add(UsuarioMapper.ofPetshopDto(petshops.get(i)));
        }

        return petshopsDto;
    }
}
