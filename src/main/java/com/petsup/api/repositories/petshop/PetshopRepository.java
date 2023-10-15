package com.petsup.api.repositories.petshop;

import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.dto.PetshopAvaliacaoDto;
import com.petsup.api.dto.PetshopMediaPrecoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetshopRepository  extends JpaRepository<Petshop, Integer> {

    Optional<Petshop> findByEmail(String email);

    List<Petshop> findAllByNomeLike(String nome);

    @Query("SELECT new com.petsup.api.dto.PetshopMediaPrecoDto(p.id, p.nome, AVG(s.preco)) FROM Petshop p JOIN p.servicos s GROUP BY p.id, p.nome ORDER BY AVG(s.preco)")
    List<PetshopMediaPrecoDto> ordenarPorPreco();

    @Query("SELECT new com.petsup.api.dto.PetshopAvaliacaoDto(a.fkPetshop.id, AVG(a.nota), a.fkPetshop.nome) FROM AvaliacaoPetshop a GROUP BY a.fkPetshop.id, a.fkPetshop.nome ORDER BY AVG(a.nota) DESC")
    List<PetshopAvaliacaoDto> ordenarMediaAvaliacao();

    @Query("SELECT new com.petsup.api.dto.PetshopAvaliacaoDto(a.fkPetshop.id, AVG(a.nota), a.fkPetshop.nome) FROM AvaliacaoPetshop a GROUP BY a.fkPetshop.id, a.fkPetshop.nome")
    List<PetshopAvaliacaoDto> listarMediaAvaliacao();
    List<Petshop> findAllByBairroAndCidade(String bairro, String cidade);
}
