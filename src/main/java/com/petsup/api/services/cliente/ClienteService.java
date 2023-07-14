package com.petsup.api.services.cliente;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.dto.DetalhesEnderecoDto;
import com.petsup.api.dto.PetshopAvaliacaoDto;
import com.petsup.api.dto.PetshopMediaPrecoDto;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.ClienteDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.mapper.AvaliacaoMapper;
import com.petsup.api.mapper.ClienteMapper;
import com.petsup.api.mapper.PetshopMapper;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.services.GeocodingService;
import com.petsup.api.util.FilaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    FilaObj<AvaliacaoPetshop> filaAvaliacao = new FilaObj(1000);
    @Autowired
    private AgendamentoRepository agendamentoRepository;
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

    public void postCliente(ClienteDto usuarioDto) {
        Cliente cliente = ClienteMapper.ofCliente(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        this.clienteRepository.save(cliente);
    }

    public ClienteTokenDto authenticateCliente(ClienteLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Cliente usuarioAutenticado =
                clienteRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return ClienteMapper.ofCliente(usuarioAutenticado, token);
    }

    public ClienteDto updateClienteById(ClienteDto clienteDto, Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Cliente usuarioAtt = ClienteMapper.ofCliente(clienteDto, cliente);
        clienteRepository.save(usuarioAtt);

        return ClienteMapper.ofClienteDto(usuarioAtt);
    }

    public List<ClienteDto> findClientes() {
        List<Cliente> usuarios = this.clienteRepository.findAll();

        List<ClienteDto> usuariosDto = new ArrayList<>();

        for (Cliente usuario : usuarios) {
            usuariosDto.add(ClienteMapper.ofClienteDto(usuario));
        }

        return usuariosDto;
    }

    public ClienteDto getClienteById(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        ClienteDto clienteDto = ClienteMapper.ofClienteDto(cliente);

        return clienteDto;
    }

    public ClienteDto getUserByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        ClienteDto clienteDto = ClienteMapper.ofClienteDto(cliente);

        return clienteDto;
    }

    public void deleteById(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        this.clienteRepository.deleteById(idCliente);
    }

        public AvaliacaoPetshop postAvaliacao(AvaliacaoPetshop avl, int idCliente, int idPetshop) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        avl.setFkPetshop(petshop);
        avl.setFkCliente(cliente);

        avaliacaoRepository.save(avl);
        return avl;
    }

    public AvaliacaoDto getAvaliacaoCliente(int idCliente, int idPetshop) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
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
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Cliente usuarioAtt = ClienteMapper.ofCliente(latitude, longitude, cliente);
        clienteRepository.save(usuarioAtt);
    }

    public List<PetshopDto> getPetshopsByClienteBairro(Integer idCliente) {
        String bairro = "";

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
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

        List<Petshop> petshops = petshopRepository
                .findAllByBairroAndCidade(detalhesEnderecoDto.getNeighborhood(), detalhesEnderecoDto.getCity());

        List<PetshopDto> petshopsDto = new ArrayList<>();

        for (int i = 0; i < petshops.size(); i++) {
            petshopsDto.add(PetshopMapper.ofPetshopDto(petshops.get(i)));
        }

        return petshopsDto;
    }
}