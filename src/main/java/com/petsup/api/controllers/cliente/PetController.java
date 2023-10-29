package com.petsup.api.controllers.cliente;

import com.petsup.api.models.cliente.Pet;
import com.petsup.api.dto.pet.PetRespostaDto;
import com.petsup.api.services.cliente.PetService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@RequestMapping("/api/tk/pets")
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

    @PostMapping("/adicionar-pfp/{idPet}")
    public ResponseEntity<Boolean> postProfilePicture(@PathVariable Integer idPet,
                                                      @RequestParam MultipartFile image) throws IOException {
        if (this.petService.postProfilePicture(idPet, image)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/retornar-blob/{idPet}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int idPet) {
        byte[] response = this.petService.getProfilePicture(idPet);

        if (response.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/retornar-imagem/{idPet}")
    public ResponseEntity<String> getImage(@PathVariable int idPet) {
        return ResponseEntity.ok(petService.getImage(idPet));
    }

    @PutMapping("/atualizar-imagem/{idPet}")
    public ResponseEntity<Boolean> updateImage(@PathVariable int idPet,
                                               @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(petService.updateImage(idPet, image));
    }

    @DeleteMapping("/deletar-imagem/{idPet}")
    public ResponseEntity<String> deleteImage(@PathVariable int idPet) {
        if (petService.deleteImage(idPet)){
            return ResponseEntity.ok("Imagem deletada");
        }

        return ResponseEntity.internalServerError().body("Erro ao deletar imagem");
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
