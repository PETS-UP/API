package com.petsup.api.service.autentication.dto;

import com.petsup.api.entities.usuario.UsuarioPetshop;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PetshopDeatlhesDto implements UserDetails {
    private final String nome;
    private final String email;
    private final String senha;
    private final String cep;
    private final String telefone;
    private final String cnpj;
    private String razaoSocial;

    public PetshopDeatlhesDto(UsuarioPetshop usuarioPetshop) {
        this.nome = usuarioPetshop.getNome();
        this.email = usuarioPetshop.getEmail();
        this.senha = usuarioPetshop.getSenha();
        this.cep = usuarioPetshop.getCep();
        this.telefone = usuarioPetshop.getTelefone();
        this.cnpj = usuarioPetshop.getCnpj();
        this.razaoSocial = usuarioPetshop.getRazaoSocial();
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
