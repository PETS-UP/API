package com.petsup.api.services.petshop;

import com.azure.core.http.rest.Response;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.AgendamentoRespostaDto;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.PetshopAbertoDto;
import com.petsup.api.dto.petshop.PetshopExibicaoDto;
import com.petsup.api.dto.servico.ServicoDto;
import com.petsup.api.dto.servico.ServicoRespostaDto;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<PetshopExibicaoDto> listPetshops() {
        List<Petshop> petshops = this.petshopRepository.findAll();
        List<PetshopAvaliacaoDto> petshopAvaliacaoDtos = this.petshopRepository.listarMediaAvaliacao();

        return PetshopMapper.ofListPetshopExibicaoDto(petshops, petshopAvaliacaoDtos);
    }

    public PetshopExibicaoDto getPetshopById(Integer id) {
        Petshop petshop = petshopRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );
        Optional<PetshopAvaliacaoDto> petshopAvaliacaoDto = petshopRepository.encontrarMediaAvaliacao(id);

        if (petshopAvaliacaoDto.isEmpty()) {
            PetshopAvaliacaoDto emptyPetshopAvaliacao = new PetshopAvaliacaoDto();
            return PetshopMapper.ofPetshopExibicaoDto(petshop, emptyPetshopAvaliacao);
        }

        return PetshopMapper.ofPetshopExibicaoDto(petshop, petshopAvaliacaoDto.get());
    }

    public List<PetshopDto> getPetshopsByNome(String nome) {
        List<Petshop> petshops = petshopRepository.findAllByNomeLike(nome);
        return PetshopMapper.ofListUsuarioPetshopDto(petshops);
    }

    public PetshopDto getPetshopByEmail(String email) {
        Petshop petshop = petshopRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        return PetshopMapper.ofPetshopDto(petshop);
    }

    public void deleteById(Integer id) {
        if (!petshopRepository.existsById(id)) {
            throw new ResponseStatusException(404, "Petshop não encontrado", null);
        }

        petshopRepository.deleteById(id);
    }

    public PetshopDto updatePetshop(Integer idPetshop, PetshopDto petshopDto) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        Petshop petshopAtt = PetshopMapper.ofPetshop(petshopDto, petshop);
        petshopAtt.setImagemPerfil(petshop.getImagemPerfil());
        petshopRepository.save(petshopAtt);

        return PetshopMapper.ofPetshopDto(petshopAtt);
    }

    public ServicoRespostaDto updateServico(ServicoDto servicoAtt, Integer idServico, Integer idPetshop) {
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

    public void subscribeToPetshop(Integer idPetshop, Integer idCliente) {
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

    public ListaObj<AgendamentoRespostaDto> orderAgendamentosByDate(Integer idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        List<Agendamento> listaAgendamentos = agendamentoRepository.findByFkPetshopId(idPetshop);
        ListaObj<Agendamento> listaLocal = ordenaListaAgendamento(listaAgendamentos);

        return AgendamentoMapper.ofListaObjAgendamentoRespostaDto(listaLocal);
    }

    public void adicionarHoraFuncionamento(LocalTime horaAbertura, LocalTime horaFechamento, Integer idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        petshop.setHoraAbertura(horaAbertura);
        petshop.setHoraFechamento(horaFechamento);
        petshopRepository.save(petshop);
    }

    public void adicionarDiasFuncionais(@NotNull List<DayOfWeek> dias, Integer idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );
        List<DayOfWeek> aux = new ArrayList<>();
        for (int i = 0; i < dias.size(); i++) {
            aux.add(dias.get(i));
        }
        petshop.setDiasFuncionais(aux);
        petshopRepository.save(petshop);
    }

    public List<PetshopAbertoDto> estaAberto() {
        List<Petshop> petshops = petshopRepository.findAll();
        List<PetshopAbertoDto> statusList = new ArrayList<>();
        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        LocalTime agora = LocalTime.now();
        LocalTime horaAbrir;
        LocalTime horaFechar;
        List<DayOfWeek> diasAbertos;
        Petshop petshopAtual;
        for (int i = 0; i < petshops.size(); i++) {
            petshopAtual = petshops.get(i);
            horaAbrir = petshopAtual.getHoraAbertura();
            horaFechar = petshopAtual.getHoraFechamento();
            diasAbertos = petshopAtual.getDiasFuncionais();

            if (agora.isAfter(horaAbrir) && agora.isBefore(horaFechar) && diasAbertos.contains(hoje)) {
                statusList.add(PetshopMapper.ofPetshopAbertoDto(petshopAtual, true));
            } else {
                statusList.add(PetshopMapper.ofPetshopAbertoDto(petshopAtual, false));
            }
        }

        return statusList;
//        if(petshop.getHoraAbertura() == null || petshop.getHoraFechamento() == null || petshop.getDiasFuncionais() == null){
//            return false;
//        }
//        return agora.isAfter(horaAbrir) && agora.isBefore(horaFechar) && diasAbertos.contains(hoje);
    }

    public Boolean postProfilePicture(int idPetshop, MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new IOException("Conteúdo vazio");
        }

        byte[] bytes = image.getBytes();
        if (bytes.length == 0) {
            throw new IOException("Conteúdo vazio");
        }

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        String fileName = LocalDateTime.now() + image.getOriginalFilename();

        String accessKey = "DefaultEndpointsProtocol=https;" +
                "AccountName=petsupstorage;" +
                "AccountKey=4ClVfz8iLUJyqdWBSgqT2Nt45MVvjMqNAnUYz8qIft0xqSu2nxZ0QX1flS1OykoJcl13z0pUMXzO+AStmsWYgw==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(accessKey)
                .containerName("imagesstorage")
                .buildClient();

        BlobClient blob = container.getBlobClient(fileName);

        Response<BlockBlobItem> response =
                blob.uploadWithResponse(
                        new BlobParallelUploadOptions(new ByteArrayInputStream(bytes), bytes.length),
                        Duration.ofHours(5),
                        null);

        if (response.getStatusCode() != 201) {
            throw new IOException("request failed");
        }

        petshop.setImagemPerfil(fileName);

        petshopRepository.save(petshop);

        return true;
    }

    public byte[] getProfilePicture(int idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        String blobName = petshop.getImagemPerfil();

        String accessKey = "DefaultEndpointsProtocol=https;" +
                "AccountName=petsupstorage;" +
                "AccountKey=4ClVfz8iLUJyqdWBSgqT2Nt45MVvjMqNAnUYz8qIft0xqSu2nxZ0QX1flS1OykoJcl13z0pUMXzO+AStmsWYgw==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(accessKey)
                .containerName("imagesstorage")
                .buildClient();


        BlobClient blob = container.getBlobClient(blobName);

        BinaryData binary = blob.downloadContent();
        byte[] byteImage = binary.toBytes();

        return byteImage;
    }

    public String getImage(int idPetshop) {

        String pathBase = "https://petsupstorage.blob.core.windows.net/imagesstorage/";

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        if (petshop.getImagemPerfil() == null || petshop.getImagemPerfil() == "") {
            return pathBase;
        }

        String blobName = petshop.getImagemPerfil();

        String accessKey = "DefaultEndpointsProtocol=https;" +
                "AccountName=petsupstorage;" +
                "AccountKey=4ClVfz8iLUJyqdWBSgqT2Nt45MVvjMqNAnUYz8qIft0xqSu2nxZ0QX1flS1OykoJcl13z0pUMXzO+AStmsWYgw==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(accessKey)
                .containerName("imagesstorage")
                .buildClient();

        Optional<BlobClient> blob = Optional.of(container.getBlobClient(blobName));

        if (!blob.get().exists()) {
            throw new ResponseStatusException(404, "Conteúdo blob vazio", null);
        }

        String pathImage = pathBase + petshop.getImagemPerfil();

        return pathImage;
    }

    public Boolean updateImage(int idPetshop, MultipartFile image) throws IOException {

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

//        if (petshop.getImagemPerfil() == null || petshop.getImagemPerfil() == "") {
//            throw new ResponseStatusException(404, "Imagem não encontrada", null);
//        }

        if (image.isEmpty()) {
            throw new ResponseStatusException(404, "Imagem não encontrada", null);
        }

        String nameBlobOriginal = petshop.getImagemPerfil();

        if (nameBlobOriginal != null) {

            String accessKey = "DefaultEndpointsProtocol=https;" +
                    "AccountName=petsupstorage;" +
                    "AccountKey=4ClVfz8iLUJyqdWBSgqT2Nt45MVvjMqNAnUYz8qIft0xqSu2nxZ0QX1flS1OykoJcl13z0pUMXzO+AStmsWYgw==;" +
                    "EndpointSuffix=core.windows.net";

            BlobContainerClient container = new BlobContainerClientBuilder()
                    .connectionString(accessKey)
                    .containerName("imagesstorage")
                    .buildClient();

            Optional<BlobClient> blob = Optional.of(container.getBlobClient(nameBlobOriginal));

            boolean delete = blob.get().deleteIfExists();

            if (delete) {

                String nameUpdate = LocalDateTime.now() + image.getOriginalFilename();

                byte[] imageNewBytes = image.getBytes();

                Optional<BlobClient> blobUpdate = Optional.of(container.getBlobClient(nameUpdate));

                Response<BlockBlobItem> response =
                        blobUpdate.get().uploadWithResponse(
                                new BlobParallelUploadOptions(new ByteArrayInputStream(imageNewBytes), imageNewBytes.length),
                                Duration.ofHours(5),
                                null);

                if (response.getStatusCode() != 201) {
                    throw new ResponseStatusException(400, "Falha na atualização", null);
                }

                petshop.setImagemPerfil(nameUpdate);

                petshopRepository.save(petshop);

                return true;

            }
        }

        return postProfilePicture(idPetshop, image);
    }

    public Boolean deleteImage(int idPetshop) {

        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado", null)
        );

        if (petshop.getImagemPerfil().isEmpty()) {
            throw new ResponseStatusException(404, "Imagem não encontrada", null);
        }

        String nameBlobOriginal = petshop.getImagemPerfil();

        String accessKey = "DefaultEndpointsProtocol=https;" +
                "AccountName=petsupstorage;" +
                "AccountKey=4ClVfz8iLUJyqdWBSgqT2Nt45MVvjMqNAnUYz8qIft0xqSu2nxZ0QX1flS1OykoJcl13z0pUMXzO+AStmsWYgw==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(accessKey)
                .containerName("imagesstorage")
                .buildClient();

        Optional<BlobClient> blob = Optional.of(container.getBlobClient(nameBlobOriginal));

        boolean delete = blob.get().deleteIfExists();

        if (delete) {

            petshop.setImagemPerfil(null);

            petshopRepository.save(petshop);

            return true;
        }

        return false;
    }

    public List<PetshopAvaliacaoDto> getMediaAvaliacao() {
        List<PetshopAvaliacaoDto> petshops = petshopRepository.listarMediaAvaliacao();
        petshops.forEach(petshop ->
                petshop.setImagemPerfil("https://petsupstorage.blob.core.windows.net/imagesstorage/" + petshop.getImagemPerfil())
        );
        return petshops;
    }
}
