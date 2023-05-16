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
import static org.mockito.Mockito.*;

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
        clienteController = mock(ClienteController.class);
        usuarioService = mock(UsuarioService.class);
        clienteRepository = mock(ClienteRepository.class);
    }

    @Spy
    private UsuarioClienteStub usuarioClienteStub;

    @Test
    public void getUserById_RetornaStatus200() {
        when(clienteController.getUserById(1)).thenReturn(ResponseEntity.ok(usuarioClienteStub));
        verify(clienteController).getUserById(1);
    }
}
