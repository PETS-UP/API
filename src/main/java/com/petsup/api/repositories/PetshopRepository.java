package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetshopRepository  extends JpaRepository<UsuarioPetshop, Integer> {
    List<Agendamento> findByAgendamentos (UsuarioPetshop usuario);
}
