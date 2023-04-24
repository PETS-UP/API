package com.petsup.api.repositories;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<UsuarioCliente, Integer> {
    Optional<UsuarioCliente> findByNome(String nome);
    Optional<UsuarioCliente> findByEmail(String email);
}
