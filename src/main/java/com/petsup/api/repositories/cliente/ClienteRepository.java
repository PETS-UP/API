package com.petsup.api.repositories.cliente;

import com.petsup.api.models.cliente.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<UsuarioCliente, Integer> {
    Optional<UsuarioCliente> findByNome(String nome);
    Optional<UsuarioCliente> findByEmail(String email);
}
