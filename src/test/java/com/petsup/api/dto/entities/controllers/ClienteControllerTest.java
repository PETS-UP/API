package com.petsup.api.dto.entities.controllers;

import com.petsup.api.controllers.cliente.ClienteController;
import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.ClienteDto;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.builder.UsuarioClienteBuilder;
import com.petsup.api.services.cliente.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

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

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isNoContent());

        assertEquals(emptyList(), clienteRepository.findAll());
    }

    @Test
    void getClientesRetornaListaDeTamanho3() throws Exception {
        List<Cliente> lista = UsuarioClienteBuilder.buildListaUsuarioCliente();

        when(clienteRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());

        assertEquals(3, clienteRepository.findAll().size());
    }

//    @Test
//    void getUserByIdRetornaClienteDeId1() throws Exception {
//        Integer id = 1;
//        UsuarioCliente usuarioCliente = UsuarioClienteBuilder.buildUsuarioCliente();
//
//        when(clienteRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioCliente));
//
//        mockMvc.perform(get("/clientes/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(usuarioCliente.getId()));
//
//        assertEquals(usuarioCliente.getId(), clienteRepository.findById(1).get().getId());
//    }

    @Test
    void getUserByIdLancaExcecao() {
        when(clienteRepository.findById(any())).thenThrow(new RuntimeException("Cliente não encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clienteRepository.findById(999));

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void postUserClienteRetornaStatus201Created() {
        ClienteDto clienteDto = UsuarioClienteBuilder.buildUsuarioClienteDto();

        doNothing().when(clienteService).postCliente(clienteDto);

        HttpStatus status = (HttpStatus) clienteController.postUserCliente(clienteDto).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    void deleteByIdRetornaStatus204NoContent() {
        doNothing().when(clienteRepository).deleteById(any());

        HttpStatus status = (HttpStatus) clienteController.deleteById(1).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, status);
    }

    @Test
    void deleteByIdLancaExcecao() {
        RuntimeException exception = new RuntimeException("Cliente não encontrado");

        when(clienteController.deleteById(999)).thenThrow(exception);

        assertThrows(RuntimeException.class,
                () -> clienteController.deleteById(999));
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void loginRetornaStatus200OkEClienteEsperado() {
        ClienteTokenDto clienteEsperado = UsuarioClienteBuilder.buildClienteTokenDto();

        when(clienteService.authenticateCliente(any()))
                .thenReturn(UsuarioClienteBuilder.buildClienteTokenDto());

        ClienteTokenDto cliente = clienteController.login(UsuarioClienteBuilder.buildClienteLoginDto()).getBody();

        assertEquals(HttpStatus.OK, clienteController.login(UsuarioClienteBuilder.buildClienteLoginDto()).getStatusCode());
        assertEquals(clienteEsperado.getClienteId(), cliente.getClienteId());
        assertEquals(clienteEsperado.getNome(), cliente.getNome());
        assertEquals(clienteEsperado.getEmail(), cliente.getEmail());
        assertEquals(clienteEsperado.getToken(), cliente.getToken());
    }
}