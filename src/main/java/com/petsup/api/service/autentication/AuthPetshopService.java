package com.petsup.api.service.autentication;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.service.autentication.dto.ClienteDetalhesDto;
import com.petsup.api.service.autentication.dto.PetshopDeatlhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AuthPetshopService implements UserDetailsService{

    @Autowired
    private PetshopRepository petshopRepository;

    // Método da interface implementada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioPetshop> usuarioOpt = petshopRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new PetshopDeatlhesDto(usuarioOpt.get());
    }
}
