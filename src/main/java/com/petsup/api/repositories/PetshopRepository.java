package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetshopRepository  extends JpaRepository<UsuarioPetshop, Integer> {

//    List<Agendamento> findByAgendamentos (Usuario usuario);
    Optional<UsuarioPetshop> findByEmail(String email);

    //Necessário criar uma forma de padronizar os preços (Como no ifood)
    @Query("SELECT * FROM usuarioPetshop p ORDER BY (GET AVG(p.preco) FROM usuarioPetshop p) asc")
    List<UsuarioPetshop> ordenarPorPreco();

    //Preciso pegar do front a distancia e criar uma função que compara e organiza os petshops em ordem
    @Query("SELECT * FROM usuarioPetshop p ORDER BY p.distancia asc")
    List<UsuarioPetshop> ordenarPorDistancia();

    @Query("SELECT * FRON usuarioPetshop ORDER BY (GET AVG(p.avaliacao) FROM usuarioPetshop p)")
    List<UsuarioPetshop> ordenarPorAvaliacao();
}
