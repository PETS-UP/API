package com.petsup.api.controllers.petshop;

import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.UsuarioPetshop;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.mapper.ServicoMapper;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name = "Serviços", description = "Requisições relacionadas a serviços.")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postServico(@RequestBody @Valid Servico servico, @RequestParam Integer idPetshop) {
        Optional<UsuarioPetshop> petshopOptional = petshopRepository.findById(idPetshop);

        if (petshopOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioPetshop petshop = petshopOptional.get();
        servico.setFkPetshop(petshop);
        servicoRepository.save(servico);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao pet shop.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o pet shop não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado")
    @GetMapping
    public ResponseEntity<List<ServicoRespostaDto>> getServicosByIdPetshop(@RequestParam Integer idPetshop) {

        if (petshopRepository.findById(idPetshop).isPresent()) {
            List<Servico> servicos = servicoRepository.findAllByFkPetshopId(idPetshop);
            List<ServicoRespostaDto> servicosDto = ServicoMapper.ofListaServicoRespostaDto(servicos);

            return servicosDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(servicosDto);
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna o serviço a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoRespostaDto> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.ok(ServicoMapper.ofServicoRespostaDto(servicoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Serviço não encontrado."))
        ));
    }
}
