package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.service.autentication.dto.PetshopDeatlhesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetshopRepository  extends JpaRepository<UsuarioPetshop, Integer> {

//    List<Agendamento> findByAgendamentos (Usuario usuario);
    Optional<UsuarioPetshop> findByEmail(String email);

    List<UsuarioPetshop> findAllByNomeLike(String nome);

    //Necessário criar uma forma de padronizar os preços (Como no ifood)
    @Query("SELECT u FROM UsuarioPetshop u JOIN u.servicos s GROUP BY u.id ORDER BY AVG(s.preco) ASC")
    List<UsuarioPetshop> ordenarPorPreco();

    //Preciso pegar do front a distancia e criar uma função que compara e organiza os petshops em ordem
//    @Query("SELECT * FROM usuarioPetshop p ORDER BY p.distancia asc")
//    List<UsuarioPetshop> ordenarPorDistancia();

    @Query("SELECT u FROM UsuarioPetshop u LEFT JOIN u.avaliacoes a GROUP BY u.id ORDER BY AVG(a.nota) DESC")
    List<UsuarioPetshop> ordenarPorAvaliacao();

    List<UsuarioPetshop> findAllByBairroAndCidade(String bairro, String cidade);
}
