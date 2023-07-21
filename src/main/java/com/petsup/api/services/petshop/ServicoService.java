package com.petsup.api.services.petshop;

import com.petsup.api.dto.petshop.ServicoRespostaDto;
import com.petsup.api.mapper.ServicoMapper;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PetshopRepository petshopRepository;

    public void postServico(Servico servico, Integer idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado.", null)
        );
        servico.setFkPetshop(petshop);
        servicoRepository.save(servico);
    }

    public List<ServicoRespostaDto> getServicosByIdPetshop(Integer idPetshop) {
        Petshop petshop = petshopRepository.findById(idPetshop).orElseThrow(
                () -> new ResponseStatusException(404, "Petshop não encontrado.", null)
        );
        return ServicoMapper.ofListaServicoRespostaDto(servicoRepository.findAllByFkPetshopId(idPetshop));
    }

    public ServicoRespostaDto getServicoById(Integer id) {
        Servico servico = servicoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(404, "Serviço não encontrado", null)
        );
        return ServicoMapper.ofServicoRespostaDto(servico);
    }
}
