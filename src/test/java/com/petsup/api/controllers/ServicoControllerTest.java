package com.petsup.api.controllers;

import com.petsup.api.builder.ServicoBuilder;
import com.petsup.api.builder.UsuarioPetshopBuilder;
import com.petsup.api.entities.Servico;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.repositories.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ServicoControllerTest {

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private PetshopRepository petshopRepository;

    @InjectMocks
    private ServicoController servicoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(servicoController).build();
    }

    @Test
    void getServicosByIdPetshopRetornaListaVazia() throws Exception {
        when(servicoRepository.findAllByFkPetshopId(anyInt())).thenReturn(emptyList());
        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));

        mockMvc.perform(get("/servicos").param("idPetshop", "1"))
                .andExpect(status().isNoContent());

        assertEquals(emptyList(), servicoRepository.findAllByFkPetshopId(1));
    }

    @Test
    void getServicosByIdPetshopRetornaListaDeTamanho3() throws Exception {
        List<Servico> lista = ServicoBuilder.buildListaServico();

        when(servicoRepository.findAllByFkPetshopId(anyInt())).thenReturn(lista);
        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));

        mockMvc.perform(get("/servicos").param("idPetshop", "1"))
                .andExpect(status().isOk());

        assertEquals(3, servicoRepository.findAllByFkPetshopId(1).size());
    }

    @Test
    void getServicoByIdLancaExcecao() {
        when(servicoRepository.findById(anyInt())).thenThrow(new RuntimeException("Serviço não encontrado."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicoRepository.findById(999));

        assertEquals("Serviço não encontrado.", exception.getMessage());
    }

    @Test
    void postServicoRetornaStatus201Created() {
        Servico servico = ServicoBuilder.buildServico();

        when(servicoRepository.save(any())).thenReturn(servico);
        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));

        HttpStatus status = (HttpStatus) servicoController.postServico(servico, 1).getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }
}
