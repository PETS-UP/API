package com.petsup.api.repositories.cliente;

import com.petsup.api.models.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNome(String nome);
    Optional<Cliente> findByEmail(String email);
}
