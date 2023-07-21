package com.petsup.api.services;

import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.dto.authentication.ClienteDetalhesDto;
import com.petsup.api.dto.authentication.PetshopDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PetshopRepository petshopRepository;

    // Método da interface implementada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Petshop> usuarioOpt = petshopRepository.findByEmail(username);
        if (usuarioOpt.isEmpty()) {
            Optional<Cliente> usuarioOptCli = clienteRepository.findByEmail(username);
            if (usuarioOptCli.isEmpty()) {
                throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
            } else {
                return new ClienteDetalhesDto(usuarioOptCli.get());
            }
        } else {
            return new PetshopDetalhesDto(usuarioOpt.get());
        }
    }
}
