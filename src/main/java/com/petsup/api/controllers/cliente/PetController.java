package com.petsup.api.controllers.cliente;

import com.petsup.api.models.cliente.Pet;
import com.petsup.api.dto.cliente.PetRespostaDto;
import com.petsup.api.services.cliente.PetService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
@CrossOrigin(maxAge = 3600)
public class PetController {
    @Autowired
    private PetService petService;

    @ApiResponse(responseCode = "201", description = "Pet cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postPet(@RequestBody @Valid Pet pet, @RequestParam Integer idCliente) {
        petService.postPet(pet, idCliente);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de pets atrelados ao cliente.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o cliente não tenha pets cadastrados.")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping
    public ResponseEntity<List<PetRespostaDto>> getPetsByIdCliente(@RequestParam Integer idCliente) {
        List<PetRespostaDto> pets = petService.getPetsByIdCliente(idCliente);
        if (pets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pets);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o pet a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{idPet}")
    public ResponseEntity<PetRespostaDto> getPetById(@PathVariable Integer idPet) {
        return ResponseEntity.ok(petService.getPetById(idPet));
    }

    @PostMapping("/adicionar-pilha/{obj}")
    public ResponseEntity<Void> adicionarNaPilha(@PathVariable String obj){
        petService.adicionarNaPilha(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pop-pilha")
    public ResponseEntity<String> getInfosAndPop(){
        return ResponseEntity.ok(petService.getInfosAndPop());
    }

    @PostMapping("/limpa-pilha")
    public ResponseEntity<Void> limparPilha(){
        petService.limparPilha();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastrar-pilha")
    public ResponseEntity<Void> postPilha(@RequestParam Integer idCliente){
        petService.postPilha(idCliente);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadByTxt(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam Integer idCliente){
        petService.uploadByTxt(arquivo, idCliente);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idPet){
        petService.deleteById(idPet);
        return ResponseEntity.noContent().build();
    }
}
