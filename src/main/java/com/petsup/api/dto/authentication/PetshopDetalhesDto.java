package com.petsup.api.dto.authentication;

import com.petsup.api.models.petshop.Petshop;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PetshopDetalhesDto implements UserDetails {
    private final String nome;
    private final String email;
    private final String senha;
    private final String cep;
    private final String telefone;
    private final String cnpj;
    private String razaoSocial;

    public PetshopDetalhesDto(Petshop petshop) {
        this.nome = petshop.getNome();
        this.email = petshop.getEmail();
        this.senha = petshop.getSenha();
        this.cep = petshop.getCep();
        this.telefone = petshop.getTelefone();
        this.cnpj = petshop.getCnpj();
        this.razaoSocial = petshop.getRazaoSocial();
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
