package com.petsup.api.controllers;

import com.petsup.api.builder.PetBuilder;
import com.petsup.api.builder.UsuarioClienteBuilder;
import com.petsup.api.builder.UsuarioPetshopBuilder;
import com.petsup.api.entities.Pet;
import com.petsup.api.repositories.ClienteRepository;
import com.petsup.api.repositories.PetRepository;
import com.petsup.api.service.dto.PetDto;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void getPetsByUserIdRetornaListaVazia() throws Exception {
        when(petRepository.findByFkClienteId(anyInt())).thenReturn(emptyList());
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));

        mockMvc.perform(get("/pets").param("idCliente", "1"))
                .andExpect(status().isNoContent());

        assertEquals(emptyList(), petRepository.findByFkClienteId(1));
    }

    @Test
    void getPetsByUserIdRetornaListaDeTamanho3() throws Exception {
        List<Pet> lista = PetBuilder.buildListaPet();

        when(petRepository.findByFkClienteId(anyInt())).thenReturn(lista);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));

        mockMvc.perform(get("/pets").param("idCliente", "1"))
                .andExpect(status().isOk());

        assertEquals(3, petRepository.findByFkClienteId(1).size());
    }

    @Test
    void getPetByIdRetornaPetDeId1() throws Exception {
        Integer id = 1;
        Pet pet = PetBuilder.buildPet();

        when(petRepository.findById(anyInt())).thenReturn(Optional.of(pet));
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));

        mockMvc.perform(get("/pets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pet.getId()));

        assertEquals(pet.getId(), petRepository.findById(1).get().getId());
    }

    @Test
    void getPetByIdLancaExcecao() {
        when(petRepository.findById(anyInt())).thenThrow(new RuntimeException("Pet não encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> petRepository.findById(999));

        assertEquals("Pet não encontrado", exception.getMessage());
    }

    @Test
    void postPetRetornaStatus201Created() {
        Pet pet = PetBuilder.buildPet();

        doNothing().when(petRepository).save(any());

        HttpStatus status = (HttpStatus) petController.postPet(pet, 1).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }
}
