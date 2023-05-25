package com.petsup.api.controllers;

import com.petsup.api.builder.UsuarioPetshopBuilder;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetRepository;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    @Test
    void getPetsByUserIdRetornaListaVazia() throws Exception {
        when(petRepository.findAll()).thenReturn(emptyList());

        mockMvc.perform(get("/pets"))
                .andExpect(status().isNoContent());

        assertEquals(emptyList(), petRepository.findAll());
    }

    @Test
    void getPetshopsRetornaListaDeTamanho3() throws Exception {
        List<UsuarioPetshop> lista = UsuarioPetshopBuilder.buildListaUsuarioPetshop();

        when(petRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk());

        assertEquals(3, petRepository.findAll().size());
    }

    @Test
    void getUserByIdRetornaPetshopDeId1() throws Exception {
        Integer id = 1;
        UsuarioPetshop usuarioPetshop = UsuarioPetshopBuilder.buildUsuarioPetshop();

        when(petRepository.findById(Mockito.any())).thenReturn(Optional.of(usuarioPetshop));

        mockMvc.perform(get("/pets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioPetshop.getId()));

        assertEquals(usuarioPetshop.getId(), petRepository.findById(1).get().getId());
    }

    @Test
    void getUserByIdLancaExcecao() {
        when(petRepository.findById(any())).thenThrow(new RuntimeException("Petshop não encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> petRepository.findById(999));

        assertEquals("Petshop não encontrado", exception.getMessage());
    }

    @Test
    void postUserPetshopRetornaStatus201Created() {
        UsuarioPetshopDto usuarioPetshopDto = UsuarioPetshopBuilder.buildUsuarioPetshopDto();

        doNothing().when(usuarioService).criarPetshop(usuarioPetshopDto);

        HttpStatus status = (HttpStatus) petController.postUserPetshop(usuarioPetshopDto).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }
}
