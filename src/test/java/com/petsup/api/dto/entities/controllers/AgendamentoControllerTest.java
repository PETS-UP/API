package com.petsup.api.dto.entities.controllers;

import com.petsup.api.builder.*;
import com.petsup.api.controllers.AgendamentoController;
import com.petsup.api.models.Agendamento;
import com.petsup.api.repositories.*;
import com.petsup.api.repositories.cliente.ClienteRepository;
import com.petsup.api.repositories.cliente.PetRepository;
import com.petsup.api.repositories.petshop.PetshopRepository;
import com.petsup.api.repositories.petshop.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
public class AgendamentoControllerTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PetshopRepository petshopRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();
    }

//    @Test
//    void getAgendamentosByIdPetshopRetornaListaVazia() throws Exception {
//        when(agendamentoRepository.findByFkPetshopId(anyInt())).thenReturn(emptyList());
//        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));
//
//        mockMvc.perform(get("/agendamentos/petshop").param("idPetshop", "1"))
//                .andExpect(status().isNoContent());
//
//        assertEquals(emptyList(), agendamentoRepository.findByFkPetshopId(1));
//    }
//
//    @Test
//    void getAgendamentosByIdPetshopRetornaListaDeTamanho3() throws Exception {
//        List<Agendamento> lista = AgendamentoBuilder.buildListaAgendamento();
//
//        when(agendamentoRepository.findByFkPetshopId(anyInt())).thenReturn(lista);
//        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));
//
//        mockMvc.perform(get("/agendamentos/petshop").param("idPetshop", "1"))
//                .andExpect(status().isOk());
//
//        assertEquals(3, agendamentoRepository.findByFkPetshopId(1).size());
//    }
//
//    @Test
//    void getAgendamentosByIdClienteRetornaListaVazia() throws Exception {
//        when(agendamentoRepository.findByFkClienteId(anyInt())).thenReturn(emptyList());
//        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));
//
//        mockMvc.perform(get("/agendamentos/cliente").param("idCliente", "1"))
//                .andExpect(status().isNoContent());
//
//        assertEquals(emptyList(), agendamentoRepository.findByFkClienteId(1));
//    }
//
//    @Test
//    void getAgendamentosByIdClienteRetornaListaDeTamanho3() throws Exception {
//        List<Agendamento> lista = AgendamentoBuilder.buildListaAgendamento();
//
//        when(agendamentoRepository.findByFkClienteId(anyInt())).thenReturn(lista);
//        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));
//
//        mockMvc.perform(get("/agendamentos/cliente").param("idCliente", "1"))
//                .andExpect(status().isOk());
//
//        assertEquals(3, agendamentoRepository.findByFkClienteId(1).size());
//    }
//
//    @Test
//    void getAgendamentoByIdRetornaAgendamentoDeId1() throws Exception {
//        Integer id = 1;
//        Agendamento agendamento = AgendamentoBuilder.buildAgendamento();
//
//        when(agendamentoRepository.findById(anyInt())).thenReturn(Optional.of(agendamento));
//
//        mockMvc.perform(get("/agendamentos/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(agendamento.getId()));
//
//        assertEquals(agendamento.getId(), agendamentoRepository.findById(1).get().getId());
//    }
//
//    @Test
//    void getAgendamentoByIdLancaExcecao() {
//        when(agendamentoRepository.findById(anyInt())).thenThrow(new RuntimeException("Agendamento não encontrado"));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> agendamentoRepository.findById(999));
//
//        assertEquals("Agendamento não encontrado", exception.getMessage());
//    }

//    @Test
//    void postAgendamentoRetornaStatus201Created() {
//        Agendamento agendamento = AgendamentoBuilder.buildAgendamento();
//
//        when(agendamentoRepository.save(any())).thenReturn(agendamento);
//        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioClienteBuilder.buildUsuarioCliente()));
//        when(petshopRepository.findById(anyInt())).thenReturn(Optional.of(UsuarioPetshopBuilder.buildUsuarioPetshop()));
//        when(petRepository.findById(anyInt())).thenReturn(Optional.of(PetBuilder.buildPet()));
//        when(servicoRepository.findById(anyInt())).thenReturn(Optional.of(ServicoBuilder.buildServico()));
//
//        HttpStatus status = (HttpStatus) agendamentoController.postAgendamento(agendamento,1,1,1,1).getStatusCode();
//
//        assertEquals(HttpStatus.CREATED, status);
//    }
}
