package com.petsup.api.controllers;

import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

    @InjectMocks
    private final ClienteController clienteController;

    public ClienteControllerTest(ClienteController clienteController) {
        this.clienteController = clienteController;
    }

    public void getUserById_RetornaStatus200() {
        clienteController.getUserById(1);
    }
}
