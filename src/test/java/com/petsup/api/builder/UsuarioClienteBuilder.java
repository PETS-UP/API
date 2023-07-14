package com.petsup.api.builder;

import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.ClienteDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClienteBuilder {
    public static Cliente buildUsuarioCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Ana Beatriz");
        cliente.setEmail("ana@gmail.com");
        cliente.setSenha("#Teste12");
        cliente.setDataNasc(LocalDate.of(2000, 1, 1));
        cliente.setCpf("12345678901");
        cliente.setCep("01414001");
        cliente.setEstado("SP");
        cliente.setCidade("São Paulo");
        cliente.setBairro("Cerqueira César");
        cliente.setRua("Rua Haddock Lobo");
        cliente.setNumero("595");

        return cliente;
    }

    public static List<Cliente> buildListaUsuarioCliente() {
        List<Cliente> lista = new ArrayList<>();
        Cliente cliente1 = buildUsuarioCliente();
        Cliente cliente2 = buildUsuarioCliente();
        Cliente cliente3 = buildUsuarioCliente();
        cliente2.setId(2);
        cliente3.setId(3);

        lista.add(cliente1);
        lista.add(cliente2);
        lista.add(cliente3);

        return lista;
    }

    public static ClienteDto buildUsuarioClienteDto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNome("Ana Beatriz");
        clienteDto.setEmail("ana@gmail.com");
        clienteDto.setSenha("#Teste12");
        clienteDto.setDataNasc(LocalDate.of(2000, 1, 1));
        clienteDto.setCpf("12345678901");
        clienteDto.setCep("01414001");
        clienteDto.setEstado("SP");
        clienteDto.setCidade("São Paulo");
        clienteDto.setBairro("Cerqueira César");
        clienteDto.setRua("Rua Haddock Lobo");
        clienteDto.setNumero("595");

        return clienteDto;
    }

    public static ClienteLoginDto buildClienteLoginDto() {
        ClienteLoginDto clienteLoginDto = new ClienteLoginDto();
        clienteLoginDto.setEmail("ana@gmail.com");
        clienteLoginDto.setSenha("#Teste12");

        return clienteLoginDto;
    }

    public static ClienteTokenDto buildClienteTokenDto() {
        ClienteTokenDto clienteTokenDto = new ClienteTokenDto();
        clienteTokenDto.setUserId(1);
        clienteTokenDto.setNome("Ana Beatriz");
        clienteTokenDto.setEmail("ana@gmail.com");
        clienteTokenDto.setToken("token");

        return clienteTokenDto;
    }
}
