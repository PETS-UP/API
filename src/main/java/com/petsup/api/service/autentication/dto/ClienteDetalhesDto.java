package com.petsup.api.service.autentication.dto;

import com.petsup.api.entities.usuario.UsuarioCliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ClienteDetalhesDto implements UserDetails {
    private final String nome;
    private final String email;
    private final String senha;
    private final String cep;
    private final String telefone;
    private final String cpf;

    public ClienteDetalhesDto(UsuarioCliente usuarioCliente) {
        this.nome = usuarioCliente.getNome();
        this.email = usuarioCliente.getEmail();
        this.senha = usuarioCliente.getSenha();
        this.cep = usuarioCliente.getCep();
        this.telefone = usuarioCliente.getTelefone();
        this.cpf = usuarioCliente.getCpf();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
