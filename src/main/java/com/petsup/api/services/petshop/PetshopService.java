package com.petsup.api.services.petshop;

import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.ServicoDto;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.mapper.AgendamentoMapper;
import com.petsup.api.mapper.ServicoMapper;
import com.petsup.api.mapper.PetshopMapper;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.cliente.ClienteSubscriber;
import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.enums.NomeServico;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.repositories.AvaliacaoRepository;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static com.petsup.api.util.OrdenacaoAgendametos.ordenaListaAgendamento;

@Service
public class PetshopService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

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

    public void postPetshop(PetshopDto usuarioDto) {
        final Petshop petshop = PetshopMapper.ofPetshop(usuarioDto);

        String senhaCriptografada = passwordEncoder.encode(petshop.getSenha());
        petshop.setSenha(senhaCriptografada);

        this.petshopRepository.save(petshop);
    }

    public PetshopTokenDto authenticatePetshop(PetshopLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Petshop usuarioAutenticado =
                petshopRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return PetshopMapper.ofPetshop(usuarioAutenticado, token);
    }

    public List<PetshopDto> listPetshops(){
        List<Petshop> petshops = this.petshopRepository.findAll();

        return PetshopMapper.ofListUsuarioPetshopDto(petshops);
    }

    public PetshopDto getPetshopById(Integer id){
        Petshop petshop = petshopRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        return PetshopMapper.ofPetshopDto(petshop);
    }

    public List<PetshopDto> getPetshopsByNome(String nome) {
        List<Petshop> petshops = petshopRepository.findAllByNomeLike(nome);
        return PetshopMapper.ofListUsuarioPetshopDto(petshops);
    }

    public PetshopDto getPetshopByEmail(String email){
        Petshop petshop = petshopRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        return PetshopMapper.ofPetshopDto(petshop);
    }

    public void deleteById(Integer id){
        if(!petshopRepository.existsById(id)){
            throw new ResponseStatusException(404, "Petshop não encontrado", null);
        }

        petshopRepository.deleteById(id);
    }

    public PetshopDto updatePetshop(Integer idPetshop, PetshopDto petshopDto){
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        Petshop petshopAtt = PetshopMapper.ofPetshop(petshopDto, petshop);
        petshopRepository.save(petshopAtt);

        return PetshopMapper.ofPetshopDto(petshopAtt);
    }

    public ServicoRespostaDto updateServico(ServicoDto servicoAtt, Integer idServico, Integer idPetshop){
        Servico servico = servicoRepository.findById(idServico).orElseThrow(
                () -> new ResponseStatusException(404, "Serviço não encontrado", null)
        );

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
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
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        ClienteSubscriber inscrito = new ClienteSubscriber();
        inscrito.setFkCliente(cliente);
        inscrito.setFkPetshop(petshop);

        clienteSubscriberRepository.save(inscrito);
    }

    public ListaObj<AgendamentoRespostaDto> orderAgendamentosByDate(Integer idPetshop){
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(idPetshop);
        ListaObj<Agendamento> listaLocal = ordenaListaAgendamento(listaAgendamentos);

        return AgendamentoMapper.ofListaObjAgendamentoRespostaDto(listaLocal);
    }

    public void adicionarHoraFuncionamento(LocalTime horaAbertura, LocalTime horaFechamento, Integer idPetshop){
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        petshop.setHoraAbertura(horaAbertura);
        petshop.setHoraFechamento(horaFechamento);
    }

    public void adicionarDiasFuncionais(List<DayOfWeek> dias, Integer idPetshop){
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        petshop.getDiasFuncionais().clear();
        for (int i = 0; i < dias.size(); i++) {
            petshop.getDiasFuncionais().add(dias.get(i));
        }
    }

    public Boolean estaAberto(Integer idPetshop){
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );
        LocalTime horaAbrir = petshop.getHoraAbertura();
        LocalTime horaFechar = petshop.getHoraFechamento();
        LocalTime agora = LocalTime.now();
        return agora.isAfter(horaAbrir) && agora.isBefore(horaFechar);
    }

}
