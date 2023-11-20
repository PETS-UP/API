package com.petsup.api.repositories.petshop;

import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.petshop.PetshopMediaPrecoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetshopRepository extends JpaRepository<Petshop, Integer> {

    Optional<Petshop> findByEmail(String email);

    List<Petshop> findAllByNomeLike(String nome);

    @Query("SELECT new com.petsup.api.dto.petshop.PetshopMediaPrecoDto(p.id, p.nome, AVG(s.preco), p.imagemPerfil, p.horaAbertura, p.horaFechamento, p.rua, p.numero) FROM Petshop p JOIN p.servicos s GROUP BY p.id, p.nome, p.imagemPerfil, p.horaAbertura, p.horaFechamento, p.rua, p.numero ORDER BY AVG(s.preco)")
    List<PetshopMediaPrecoDto> ordenarPorPreco();

    @Query("SELECT new com.petsup.api.dto.petshop.PetshopAvaliacaoDto(a.fkPetshop.id, AVG(a.nota), a.fkPetshop.nome, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero) FROM AvaliacaoPetshop a GROUP BY a.fkPetshop.id, a.fkPetshop.nome, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero ORDER BY AVG(a.nota) DESC")
    List<PetshopAvaliacaoDto> ordenarMediaAvaliacao();

    @Query("SELECT new com.petsup.api.dto.petshop.PetshopAvaliacaoDto(a.fkPetshop.id, AVG(a.nota), a.fkPetshop.nome, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero) FROM AvaliacaoPetshop a GROUP BY a.fkPetshop.id, a.fkPetshop.nome, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero")
    List<PetshopAvaliacaoDto> listarMediaAvaliacao();

    @Query("SELECT new com.petsup.api.dto.petshop.PetshopAvaliacaoDto(a.fkPetshop.id, AVG(a.nota), a.fkPetshop.nome, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero) FROM AvaliacaoPetshop a WHERE a.fkPetshop.id = :idPetshop GROUP BY a.fkPetshop.id, a.fkPetshop.imagemPerfil, a.fkPetshop.horaAbertura, a.fkPetshop.horaFechamento, a.fkPetshop.rua, a.fkPetshop.numero")
    Optional<PetshopAvaliacaoDto> encontrarMediaAvaliacao(int idPetshop);

    List<Petshop> findAllByBairroAndCidade(String bairro, String cidade);
}
