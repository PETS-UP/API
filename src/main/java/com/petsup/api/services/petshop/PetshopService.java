package com.petsup.api.services.petshop;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.ServicoDto;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.dto.petshop.UsuarioPetshopDto;
import com.petsup.api.mapper.AgendamentoMapper;
import com.petsup.api.mapper.ServicoMapper;
import com.petsup.api.mapper.UsuarioMapper;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.ClienteSubscriber;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.enums.NomeServico;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.AvaliacaoRepository;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.ClienteSubscriberRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.util.ListaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;

@Service
public class PetshopService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteSubscriberRepository clienteSubscriberRepository;

    @Autowired
    private JavaMailSender enviador;

    public void criarPetshop(UsuarioPetshopDto usuarioDto) {
        final Usuario novoUsuario = UsuarioMapper.ofPetshop(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
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

    public List<UsuarioPetshopDto> listarPetshops(){
        List<UsuarioPetshop> petshops = this.petshopRepository.findAll();

        return UsuarioMapper.ofListUsuarioPetshopDto(petshops);
    }

    public UsuarioPetshopDto getPetshopById(Integer id){
        UsuarioPetshop petshop = petshopRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        return UsuarioMapper.ofPetshopDto(petshop);
    }

    public List<UsuarioPetshopDto> listarPetshopsPorNome(String nome){
        List<UsuarioPetshop> petshops = petshopRepository.findAllByNomeLike(nome);

        return UsuarioMapper.ofListUsuarioPetshopDto(petshops);
    }

    public UsuarioPetshopDto getPetshopByEmail(String email){
        UsuarioPetshop petshop = petshopRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        return UsuarioMapper.ofPetshopDto(petshop);
    }

    public void deleteById(Integer id){
        if(!petshopRepository.existsById(id)){
            throw new ResponseStatusException(404, "Petshop não encontrado", null);
        }

        petshopRepository.deleteById(id);
    }

    public UsuarioPetshopDto updatePetshop(Integer idPetshop, UsuarioPetshopDto usuarioPetshopDto){
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        UsuarioPetshop petshopAtt = UsuarioMapper.ofPetshop(usuarioPetshopDto, petshop);
        petshopRepository.save(petshopAtt);

        return UsuarioMapper.ofPetshopDto(petshopAtt);
    }

    public ServicoRespostaDto atualizarServico(ServicoDto servicoAtt, Integer idServico, Integer idPetshop){
        Servico servico = servicoRepository.findById(idServico).orElseThrow(
                () -> new ResponseStatusException(404, "Serviço não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        // Observer
        if (servico.getPreco() > servicoAtt.getPreco()) {
            for (int i = 0; i < petshop.getInscritos().size(); i++) {
                petshop.atualiza(enviador, petshop.getInscritos().get(i).getFkCliente().getEmail(),
                        petshop.getEmail(), servicoAtt.getPreco()); // Chamada do método de atualização na
                // entidade observada (publisher)
            }
        }
        // Observer

        servico.setNome(NomeServico.valueOf(servicoAtt.getNome()));
        servico.setPreco(servicoAtt.getPreco());
        servico.setDescricao(servicoAtt.getDescricao());
        servicoRepository.save(servico);

        return ServicoMapper.ofServicoRespostaDto(servico);
    }

//    public void gerarRelatorioCsv(int id){
//        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(id);
//
//        if (as.isEmpty()){
//            throw new RuntimeException("Petshop não possui agendamentos");
//        }
//
//        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());
//
//        for (int i = 0; i < as.size(); i++) {
//            listaLocal.adiciona(as.get(i));
//        }
//        GeradorCsv.gravaArquivoCsv(listaLocal);
//    }

    public void subscribeToPetshop(Integer idPetshop, Integer idCliente){
        UsuarioCliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        ClienteSubscriber inscrito = new ClienteSubscriber();
        inscrito.setFkCliente(cliente);
        inscrito.setFkPetshop(petshop);

        clienteSubscriberRepository.save(inscrito);
    }

    public ListaObj<AgendamentoRespostaDto> orderAgendamentosByDate(Integer idPetshop){
        UsuarioPetshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(idPetshop);
        ListaObj<Agendamento> listaLocal = ordenaListaAgendamento(listaAgendamentos);

        return AgendamentoMapper.ofListaObjAgendamentoRespostaDto(listaLocal);
    }

}
