package com.petsup.api.controllers.cliente;

import com.petsup.api.models.cliente.Pet;
import com.petsup.api.models.enums.Especie;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.PetRepository;
import com.petsup.api.mapper.PetMapper;
import com.petsup.api.dto.cliente.PetRespostaDto;
import com.petsup.api.util.GeradorTxt;
import com.petsup.api.util.PilhaObj;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/*
 POST:   /pets
 GET:    /pets
 GET:    /pets/{idPet}
 POST:   /pets/adicionar-pilha/{obj}
 GET:    /pets/pop-pilha
 POST:   /pets/limpa-pilha
 POST:   /pets/cadastrar-pilha
 POST:   /pets/upload
 DELETE: /pets/{idPet}
*/

@Tag(name = "Pets", description = "Requisições relacionadas a pets.")
@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private PilhaObj<String> pilhaObj = new PilhaObj<String>(3);


    @ApiResponse(responseCode = "201", description = "Pet cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postPet(@RequestBody @Valid Pet pet, @RequestParam Integer idCliente) {
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioCliente cliente = clienteOptional.get();
        pet.setFkCliente(cliente);
        petRepository.save(pet);
        return ResponseEntity.status(201).build();
    }


    @ApiResponse(responseCode = "200", description = "Retorna uma lista de pets atrelados ao cliente.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o cliente não tenha pets cadastrados.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping
    public ResponseEntity<List<PetRespostaDto>> getPetsByIdCliente(@RequestParam Integer idCliente) {

        if (clienteRepository.findById(idCliente).isPresent()) {
            List<Pet> pets = petRepository.findByFkClienteId(idCliente);
            List<PetRespostaDto> petsDto = PetMapper.ofListaPetRespostaDto(pets);

            return petsDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(petsDto);
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna o pet a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{idPet}")
    public ResponseEntity<PetRespostaDto> getPetById(@PathVariable Integer idPet) {
        return ResponseEntity.ok(PetMapper.ofPetRespostaDto(petRepository.findById(idPet).orElseThrow(
                () -> new RuntimeException("Pet não encontrado"))
        ));
    }

    @PostMapping("/adicionar-pilha/{obj}")
    public ResponseEntity<Void> adicionarNaPilha(@PathVariable String obj){
        pilhaObj.push(obj);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pop-pilha")
    public ResponseEntity<String> getInfosAndPop(){
        return ResponseEntity.ok().body(pilhaObj.pop());
    }

    @PostMapping("/limpa-pilha")
    public ResponseEntity<Void> limparPilha(){
            while(!pilhaObj.isEmpty()){
                pilhaObj.pop();
            }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastrar-pilha")
    public ResponseEntity<Void> postPilha(@RequestParam Integer idCliente){
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        UsuarioCliente usuarioCliente = clienteOptional.get();

        if (clienteOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        System.out.println(pilhaObj.peek());
        String nome = pilhaObj.pop();
        System.out.println(pilhaObj.peek());
        String sexo = pilhaObj.pop();
        System.out.println(pilhaObj.peek());
        Especie especie = Especie.valueOf(pilhaObj.pop());

        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setSexo(sexo);
        pet.setEspecie(especie);
        pet.setFkCliente(usuarioCliente);

        petRepository.save(pet);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadByTxt(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam Integer idCliente){
        Optional<UsuarioCliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        try{
            List<Pet> pets = GeradorTxt.leArquivoTxt(arquivo).getBody();
            for (int i = 0; i < pets.size(); i++) {
                postPet(pets.get(i), idCliente);
            }
            return ResponseEntity.ok().build();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<Void> deletarById(@PathVariable Integer idPet){
        if(petRepository.findById(idPet).isPresent()){
            petRepository.deleteById(idPet);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
