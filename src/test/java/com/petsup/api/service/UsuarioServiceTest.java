package com.petsup.api.service;

import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PetshopRepository petshopRepository;

    @InjectMocks
    private UsuarioService usuarioService;

}