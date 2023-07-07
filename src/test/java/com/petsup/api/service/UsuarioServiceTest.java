package com.petsup.api.service;

import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.services.UsuarioService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

//    @Test
//    void criarClienteRetornaMesmoUsuario() {
//        UsuarioCliente usuarioClienteEsperado = UsuarioClienteBuilder.buildUsuarioCliente();
//
//        when(usuarioRepository.save(any())).thenReturn(usuarioClienteEsperado);
//        doNothing().when(usuarioService).postCliente(UsuarioClienteBuilder.buildUsuarioClienteDto());
//
//        verify(usuarioService, times(1)).postCliente(UsuarioClienteBuilder.buildUsuarioClienteDto());
//    }
}