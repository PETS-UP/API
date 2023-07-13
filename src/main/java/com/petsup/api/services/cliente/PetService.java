package com.petsup.api.services.cliente;

import com.petsup.api.dto.cliente.PetRespostaDto;
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

import java.util.List;

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
