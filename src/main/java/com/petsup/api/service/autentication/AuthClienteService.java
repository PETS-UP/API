package com.petsup.api.service.autentication;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.autentication.dto.ClienteDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AuthClienteService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioCliente> usuarioOpt = clienteRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new ClienteDetalhesDto(usuarioOpt.get());
    }
}
