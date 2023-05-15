package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.stub.UsuarioClienteStub;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteControllerTest {

    @Mock
    private ClienteController clienteController;

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeAll
    public void init() {
        clienteController = mock();
        usuarioService = mock();
        clienteRepository = mock();
    }

    @Spy
    private UsuarioCliente usuarioClienteStub;

    @Test
    public void getUserById_RetornaStatus200() {
        clienteController.getUserById(1);
        assertEquals(ResponseEntity.ok(usuarioClienteStub), clienteController.getUserById(1));
    }
}
