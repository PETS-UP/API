package com.petsup.api.controllers.petshop;

import com.petsup.api.models.petshop.Servico;
import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.services.PetshopService;
import com.petsup.api.services.petshop.ServicoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "Requisições relacionadas a serviços.")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private PetshopService petshopService;

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PostMapping
    public ResponseEntity<Void> postServico(@RequestBody @Valid Servico servico, @RequestParam Integer idPetshop) {
        servicoService.postServico(servico, idPetshop);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description = "Retorna uma lista de agendamentos atrelados ao pet shop.")
    @ApiResponse(responseCode = "204", description = "Retorna uma lista vazia caso o pet shop não tenha agendamentos.")
    @ApiResponse(responseCode = "404", description = "Pet shop não encontrado")
    @GetMapping
    public ResponseEntity<List<ServicoRespostaDto>> getServicosByIdPetshop(@RequestParam Integer idPetshop) {
        List<ServicoRespostaDto> servicos = servicoService.getServicosByIdPetshop(idPetshop);

        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @ApiResponse(responseCode = "200", description = "Retorna o serviço a partir do id.")
    @ApiResponse(responseCode = "404", description = "Retorna Not Found caso o id não seja encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoRespostaDto> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.ok(servicoService.getServicoById(id));
    }
}
