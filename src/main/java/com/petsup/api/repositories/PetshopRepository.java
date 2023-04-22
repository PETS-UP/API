package com.petsup.api.repositories;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetshopRepository  extends JpaRepository<UsuarioPetshop, Integer> {
    List<Agendamento> findByAgendamentos (Usuario usuario);
}
