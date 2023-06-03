package com.petsup.api.builder;

import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.service.autentication.dto.PetshopLoginDto;
import com.petsup.api.service.autentication.dto.PetshopTokenDto;
import com.petsup.api.service.dto.UsuarioPetshopDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPetshopBuilder {

    public static UsuarioPetshop buildUsuarioPetshop() {
        UsuarioPetshop usuarioPetshop = new UsuarioPetshop();
        usuarioPetshop.setId(1);
        usuarioPetshop.setNome("Petshop Exemplo");
        usuarioPetshop.setEmail("petshop@gmail.com");
        usuarioPetshop.setSenha("#Teste12");
        usuarioPetshop.setCnpj("12345678901234");
        usuarioPetshop.setRazaoSocial("Petshop Exemplo LTDA");
        usuarioPetshop.setCep("01414001");
        usuarioPetshop.setEstado("SP");
        usuarioPetshop.setCidade("São Paulo");
        usuarioPetshop.setBairro("Cerqueira César");
        usuarioPetshop.setRua("Rua Haddock Lobo");
        usuarioPetshop.setNumero("595");

        return usuarioPetshop;
    }

    public static List<UsuarioPetshop> buildListaUsuarioPetshop() {
        List<UsuarioPetshop> lista = new ArrayList<>();
        UsuarioPetshop petshop1 = new UsuarioPetshop();
        UsuarioPetshop petshop2 = new UsuarioPetshop();
        UsuarioPetshop petshop3 = new UsuarioPetshop();
        petshop2.setId(2);
        petshop3.setId(3);

        lista.add(petshop1);
        lista.add(petshop2);
        lista.add(petshop3);

        return lista;
    }

    public static UsuarioPetshopDto buildUsuarioPetshopDto() {
        UsuarioPetshopDto usuarioPetshopDto = new UsuarioPetshopDto();
        usuarioPetshopDto.setNome("Petshop Exemplo");
        usuarioPetshopDto.setEmail("petshop@gmail.com");
        usuarioPetshopDto.setSenha("#Teste12");
        usuarioPetshopDto.setCnpj("12345678901234");
        usuarioPetshopDto.setRazaoSocial("Petshop Exemplo LTDA");
        usuarioPetshopDto.setCep("01414001");
        usuarioPetshopDto.setEstado("SP");
        usuarioPetshopDto.setCidade("São Paulo");
        usuarioPetshopDto.setBairro("Cerqueira César");
        usuarioPetshopDto.setRua("Rua Haddock Lobo");
        usuarioPetshopDto.setNumero("595");

        return usuarioPetshopDto;
    }

    public static PetshopLoginDto buildPetshopLoginDto() {
        PetshopLoginDto petshopLoginDto = new PetshopLoginDto();
        petshopLoginDto.setEmail("petshop@gmail.com");
        petshopLoginDto.setSenha("#Teste12");

        return petshopLoginDto;
    }

    public static PetshopTokenDto buildPetshopTokenDto() {
        PetshopTokenDto petshopTokenDto = new PetshopTokenDto();
        petshopTokenDto.setUserId(1);
        petshopTokenDto.setNome("Petshop Exemplo");
        petshopTokenDto.setEmail("petshop@gmail.com");
        petshopTokenDto.setToken("token");

        return petshopTokenDto;
    }
}
