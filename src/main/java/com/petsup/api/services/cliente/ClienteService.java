package com.petsup.api.services.cliente;

import com.azure.core.http.rest.Response;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.petsup.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.petsup.api.dto.AvaliacaoDto;
import com.petsup.api.dto.cliente.DetalhesEnderecoDto;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.petshop.PetshopExibicaoDto;
import com.petsup.api.dto.petshop.PetshopMediaPrecoDto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<PetshopAvaliacaoDto> petshopAvaliacaoDtos = petshopRepository.ordenarMediaAvaliacao();
        petshopAvaliacaoDtos.stream().forEach(
                petshopMediaPrecoDto ->
                        petshopMediaPrecoDto.setOpen(
                                LocalTime.now().isAfter(petshopMediaPrecoDto.getHoraAbertura())
                                        && LocalTime.now().isBefore(petshopMediaPrecoDto.getHoraFechamento())
                        )
        );
        return petshopAvaliacaoDtos;
    }

    public List<PetshopMediaPrecoDto> getPetshopsOrderByPrecoAsc() {
        List<PetshopMediaPrecoDto> petshopMediaPrecoDtos = petshopRepository.ordenarPorPreco();
        petshopMediaPrecoDtos.stream().forEach(
                petshopMediaPrecoDto -> {
                    Optional<PetshopAvaliacaoDto> petshopAvaliacaoDto = Optional.of(petshopRepository.encontrarMediaAvaliacao(petshopMediaPrecoDto.getId()));
                    petshopMediaPrecoDto.setNota(
                            petshopAvaliacaoDto.isEmpty() ? 0.0 : petshopAvaliacaoDto.get().getNota()
                    );
                    petshopMediaPrecoDto.setOpen(
                            (petshopMediaPrecoDto.getHoraAbertura() != null && petshopMediaPrecoDto.getHoraFechamento() != null)
                                    ? (LocalTime.now().isAfter(petshopMediaPrecoDto.getHoraAbertura())
                                    && LocalTime.now().isBefore(petshopMediaPrecoDto.getHoraFechamento()))
                                    : false
                    );
                }
        );
        return petshopMediaPrecoDtos;
    }

    public void updateLocalizacaoAtual(Integer idCliente, double latitude, double longitude) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        Cliente usuarioAtt = ClienteMapper.ofCliente(latitude, longitude, cliente);
        clienteRepository.save(usuarioAtt);
    }

    public List<PetshopExibicaoDto> getPetshopsByClienteBairro(Integer idCliente) {
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

        List<PetshopAvaliacaoDto> petshopAvaliacaoDtos = petshopRepository.listarMediaAvaliacao();

        return PetshopMapper.ofListPetshopExibicaoDto(petshops, petshopAvaliacaoDtos);
    }

    public Boolean postProfilePicture(int idCliente, MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new IOException("Conteúdo vazio");
        }

        byte[] bytes = image.getBytes();
        if (bytes.length == 0) {
            throw new IOException("Conteúdo vazio");
        }

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
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

        cliente.setImagemPerfil(fileName);

        clienteRepository.save(cliente);

        return true;
    }

    public byte[] getProfilePicture(int idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        String blobName = cliente.getImagemPerfil();

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

    public String getImage(int idCliente) {

        //String pathBase = "https://ezscheduleusersimages.blob.core.windows.net/ezschedules/";
        String pathBase = "";

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        if (cliente.getImagemPerfil() == null || cliente.getImagemPerfil() == "") {
            throw new ResponseStatusException(404, "Imagem não encontrada", null);
        }

        String blobName = cliente.getImagemPerfil();

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

        String pathImage = pathBase + cliente.getImagemPerfil();

        return pathImage;
    }

    public Boolean updateImage(int idCliente, MultipartFile image) throws IOException {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

//        if (cliente.getImagemPerfil() == null || cliente.getImagemPerfil() == "") {
//            throw new ResponseStatusException(404, "Imagem não encontrada", null);
//        }

        if (image.isEmpty()) {
            throw new ResponseStatusException(404, "Imagem não encontrada", null);
        }

        String nameBlobOriginal = cliente.getImagemPerfil();

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

            cliente.setImagemPerfil(nameUpdate);

            clienteRepository.save(cliente);

            return true;

        }

        return postProfilePicture(idCliente, image);
    }

    public Boolean deleteImage(int idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );

        if (cliente.getImagemPerfil().isEmpty()) {
            throw new ResponseStatusException(404, "Imagem não encontrada", null);
        }

        String nameBlobOriginal = cliente.getImagemPerfil();

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

            cliente.setImagemPerfil(null);

            clienteRepository.save(cliente);

            return true;
        }

        return false;
    }
}