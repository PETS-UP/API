package com.petsup.api.builder;

import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.dto.authentication.PetshopLoginDto;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.PetshopDto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioPetshopBuilder {

    public static Petshop buildUsuarioPetshop() {
        Petshop petshop = new Petshop();
        petshop.setId(1);
        petshop.setNome("Petshop Exemplo");
        petshop.setEmail("petshop@gmail.com");
        petshop.setSenha("#Teste12");
        petshop.setCnpj("12345678901234");
        petshop.setRazaoSocial("Petshop Exemplo LTDA");
        petshop.setCep("01414001");
        petshop.setEstado("SP");
        petshop.setCidade("São Paulo");
        petshop.setBairro("Cerqueira César");
        petshop.setRua("Rua Haddock Lobo");
        petshop.setNumero("595");

        return petshop;
    }

    public static List<Petshop> buildListaUsuarioPetshop() {
        List<Petshop> lista = new ArrayList<>();
        Petshop petshop1 = new Petshop();
        Petshop petshop2 = new Petshop();
        Petshop petshop3 = new Petshop();
        petshop2.setId(2);
        petshop3.setId(3);

        lista.add(petshop1);
        lista.add(petshop2);
        lista.add(petshop3);

        return lista;
    }

    public static PetshopDto buildUsuarioPetshopDto() {
        PetshopDto petshopDto = new PetshopDto();
        petshopDto.setNome("Petshop Exemplo");
        petshopDto.setEmail("petshop@gmail.com");
        petshopDto.setSenha("#Teste12");
        petshopDto.setCnpj("12345678901234");
        petshopDto.setRazaoSocial("Petshop Exemplo LTDA");
        petshopDto.setCep("01414001");
        petshopDto.setEstado("SP");
        petshopDto.setCidade("São Paulo");
        petshopDto.setBairro("Cerqueira César");
        petshopDto.setRua("Rua Haddock Lobo");
        petshopDto.setNumero("595");

        return petshopDto;
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
