package com.petsup.api.dto.entities.controllers;

import com.petsup.api.builder.UsuarioPetshopBuilder;
import com.petsup.api.controllers.petshop.PetshopController;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.repositories.*;
import com.petsup.api.dto.authentication.PetshopTokenDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.ClienteSubscriberRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import com.petsup.api.services.petshop.PetshopService;
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
public class PetshopControllerTest {

    @Mock
    private PetshopRepository petshopRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private PetshopService petshopService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteSubscriberRepository clienteSubscriberRepository;

    @InjectMocks
    private PetshopController petshopController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(petshopController).build();
    }

//    @Test
//    void getPetshopsRetornaListaVazia() throws Exception {
//        when(petshopRepository.findAll()).thenReturn(emptyList());
//
//        mockMvc.perform(get("/petshops"))
//                .andExpect(status().isNoContent());
//
//        assertEquals(emptyList(), petshopRepository.findAll());
//    }
//
////    @Test
////    void getPetshopsRetornaListaDeTamanho3() throws Exception {
////        List<UsuarioPetshop> lista = UsuarioPetshopBuilder.buildListaUsuarioPetshop();
////
////        when(petshopRepository.findAll()).thenReturn(lista);
////
////        mockMvc.perform(get("/petshops"))
////                .andExpect(status().isOk());
////
////        assertEquals(3, petshopRepository.findAll().size());
////    }
//
//    @Test
//    void getUserByIdRetornaPetshopDeId1() throws Exception {
//        Integer id = 1;
//        Petshop petshop = UsuarioPetshopBuilder.buildUsuarioPetshop();
//
//        when(petshopRepository.findById(Mockito.any())).thenReturn(Optional.of(petshop));
//
//        mockMvc.perform(get("/petshops/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(petshop.getId()));
//
//        assertEquals(petshop.getId(), petshopRepository.findById(1).get().getId());
//    }
//
//    @Test
//    void getUserByIdLancaExcecao() {
//        when(petshopRepository.findById(any())).thenThrow(new RuntimeException("Petshop não encontrado"));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> petshopRepository.findById(999));
//
//        assertEquals("Petshop não encontrado", exception.getMessage());
//    }
//
//    @Test
//    void postUserPetshopRetornaStatus201Created() {
//        PetshopDto petshopDto = UsuarioPetshopBuilder.buildUsuarioPetshopDto();
//
//        doNothing().when(petshopService).postPetshop(petshopDto);
//
//        HttpStatus status = (HttpStatus) petshopController.postPetshop(petshopDto).getStatusCode();
//
//        assertEquals(HttpStatus.CREATED, status);
//    }
//
//    @Test
//    void deleteByIdRetornaStatus204NoContent() {
//        doNothing().when(petshopRepository).deleteById(any());
//
//        HttpStatus status = (HttpStatus) petshopController.deleteById(1).getStatusCode();
//
//        assertEquals(HttpStatus.NO_CONTENT, status);
//    }
//
//    @Test
//    void deleteByIdLancaExcecao() {
//        RuntimeException exception = new RuntimeException("Petshop não encontrado");
//
//        when(petshopController.deleteById(999)).thenThrow(exception);
//
//        assertThrows(RuntimeException.class,
//                () -> petshopController.deleteById(999));
//        assertEquals("Petshop não encontrado", exception.getMessage());
//    }
//
//    @Test
//    void loginRetornaStatus200OkEPetshopEsperado() {
//        PetshopTokenDto petshopEsperado = UsuarioPetshopBuilder.buildPetshopTokenDto();
//
//        when(petshopService.authenticatePetshop(any()))
//                .thenReturn(UsuarioPetshopBuilder.buildPetshopTokenDto());
//
//        PetshopTokenDto petshop = petshopController.login(UsuarioPetshopBuilder.buildPetshopLoginDto()).getBody();
//
//        assertEquals(HttpStatus.OK, petshopController.login(UsuarioPetshopBuilder.buildPetshopLoginDto()).getStatusCode());
//        assertEquals(petshopEsperado.getUserId(), petshop.getUserId());
//        assertEquals(petshopEsperado.getNome(), petshop.getNome());
//        assertEquals(petshopEsperado.getEmail(), petshop.getEmail());
//        assertEquals(petshopEsperado.getToken(), petshop.getToken());
//    }
}
