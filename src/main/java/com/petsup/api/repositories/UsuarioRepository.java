package com.petsup.api.repositories;

import com.petsup.api.entities.usurario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findById(Integer id);
    void deleteById(Integer id);
    Optional<Usuario> findByNome(String nome);
}
