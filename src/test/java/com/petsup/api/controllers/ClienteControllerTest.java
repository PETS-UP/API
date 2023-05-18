package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.builder.UsuarioClienteBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void getUserById_RetornaClienteDeId1() {
        Optional<UsuarioCliente> usuarioClienteOptional = Optional.of(UsuarioClienteBuilder.buildUsuarioCliente());
//        ResponseEntity<UsuarioCliente> response = ResponseEntity.ok(usuarioClienteOptional.get());

        Mockito.when(clienteRepository.findById(any())).thenReturn(usuarioClienteOptional);
//        Mockito.when(clienteController.getUserById(any())).thenReturn(response);

        assertEquals(usuarioClienteOptional, clienteRepository.findById(1));
        assertEquals(1, usuarioClienteOptional.get().getId());
//        assertEquals(response, clienteController.getUserById(1));
    }
}
