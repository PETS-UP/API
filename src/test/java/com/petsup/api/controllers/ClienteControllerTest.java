package com.petsup.api.controllers;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.service.dto.UsuarioClienteDto;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.builder.UsuarioClienteBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void getClientesRetornaListaVazia() throws Exception {
        when(clienteRepository.findAll()).thenReturn(emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(status().isNoContent());

        assertEquals(emptyList(), clienteRepository.findAll());
    }

    @Test
    void getClientesRetornaListaDeTamanho3() throws Exception {
        List<UsuarioCliente> lista = UsuarioClienteBuilder.buildListaUsuarioCliente();

        when(clienteRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());

        assertEquals(3, clienteRepository.findAll().size());
    }

    @Test
    void getUserByIdRetornaClienteDeId1() throws Exception {
        Integer id = 1;
        UsuarioCliente usuarioCliente = UsuarioClienteBuilder.buildUsuarioCliente();

        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioCliente));

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioCliente.getId()));

        assertEquals(usuarioCliente.getId(), clienteRepository.findById(1).get().getId());
    }

    @Test
    void getUserByIdLancaExcecao() throws Exception {
        Integer id = 999;

        when(clienteRepository.findById(id)).thenThrow(new RuntimeException());

        try {
            mockMvc.perform(get("/clientes/{id}", id));
            fail("Expected RuntimeException to be thrown");
        } catch (NestedServletException e) {
            Throwable rootCause = e.getRootCause();
            assertThat(rootCause).isInstanceOf(RuntimeException.class);
        }
    }

}