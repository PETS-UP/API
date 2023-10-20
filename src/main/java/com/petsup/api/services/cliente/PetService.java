package com.petsup.api.services.cliente;

import com.azure.core.http.rest.Response;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.petsup.api.dto.pet.PetRespostaDto;
import com.petsup.api.mapper.PetMapper;
import com.petsup.api.models.cliente.Pet;
import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.enums.Especie;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.PetRepository;
import com.petsup.api.util.GeradorTxt;
import com.petsup.api.util.PilhaObj;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private PilhaObj<String> pilhaObj = new PilhaObj<String>(3);

    public void postPet(Pet pet, Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        pet.setFkCliente(cliente);
        petRepository.save(pet);
    }

    public Boolean postProfilePicture(int idPet, MultipartFile image) throws java.io.IOException {

        if (image.isEmpty()) {
            throw new java.io.IOException("Conteúdo vazio");
        }

        byte[] bytes = image.getBytes();
        if (bytes.length == 0) {
            throw new java.io.IOException("Conteúdo vazio");
        }

        Cliente cliente = clienteRepository.findById(idPet).orElseThrow(
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
            throw new java.io.IOException("request failed");
        }

        cliente.setImagemPerfil(fileName);

        clienteRepository.save(cliente);

        return true;
    }

    public byte[] getProfilePicture(int idPet) {
        Cliente cliente = clienteRepository.findById(idPet).orElseThrow(
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

    public String getImage(int idPet) {

        //String pathBase = "https://ezscheduleusersimages.blob.core.windows.net/ezschedules/";
        String pathBase = "";

        Cliente cliente = clienteRepository.findById(idPet).orElseThrow(
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

    public Boolean updateImage(int idPet, MultipartFile image) throws java.io.IOException {

        Cliente cliente = clienteRepository.findById(idPet).orElseThrow(
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

        return postProfilePicture(idPet, image);
    }

    public Boolean deleteImage(int idPet) {

        Cliente cliente = clienteRepository.findById(idPet).orElseThrow(
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

    public List<PetRespostaDto> getPetsByIdCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        List<PetRespostaDto> pets = PetMapper.ofListaPetRespostaDto(petRepository.findByFkClienteId(idCliente));
        return pets;
    }

    public PetRespostaDto getPetById(Integer idPet) {
        Pet pet = petRepository.findById(idPet).orElseThrow(
                () -> new ResponseStatusException(404, "Pet não encontrado", null)
        );
        PetRespostaDto petRespostaDto = PetMapper.ofPetRespostaDto(pet);
        return petRespostaDto;
    }

    public void adicionarNaPilha(String obj) {
        pilhaObj.push(obj);
    }

    public String getInfosAndPop() {
        return pilhaObj.pop();
    }

    public void limparPilha() {
        while(!pilhaObj.isEmpty()){
            pilhaObj.pop();
        }
    }

    public void postPilha(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        String nome = pilhaObj.pop();
        String sexo = pilhaObj.pop();
        Especie especie = Especie.valueOf(pilhaObj.pop());

        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setSexo(sexo);
        pet.setEspecie(especie);
        pet.setFkCliente(cliente);

        petRepository.save(pet);
    }

    public void uploadByTxt(MultipartFile arquivo, Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(404, "Cliente não encontrado", null)
        );
        try{
            List<Pet> pets = GeradorTxt.leArquivoTxt(arquivo).getBody();
            for (int i = 0; i < pets.size(); i++) {
                postPet(pets.get(i), idCliente);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Integer idPet) {
        Pet pet = petRepository.findById(idPet).orElseThrow(
                () -> new ResponseStatusException(404, "Pet não encontrado", null)
        );
        petRepository.deleteById(idPet);
    }
}
