package com.bookpoint.transferencia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.transferencia.dto.TransferenciaDTO;
import com.bookpoint.transferencia.service.TransferenciaService;
import com.bookpoint.transferencia.model.Transferencia;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferenciaController.class)
@ActiveProfiles("test")
public class TransferenciaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SuppressWarnings("removal")
    @MockBean
    private TransferenciaService transferenciaService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(transferenciaService.listarTransferencias())
                .thenReturn(Arrays.asList(new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now())));
        mockMvc.perform(get("/api/v1/transferencias")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        TransferenciaDTO dto = new TransferenciaDTO(null, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Transferencia guardado = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Mockito.when(transferenciaService.guardarTransferencia(ArgumentMatchers.<Transferencia>any()))
                .thenReturn(guardado);
        mockMvc.perform(post("/api/v1/transferencias").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Transferencia obj = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Mockito.when(transferenciaService.obtenerTransferenciaPorId(1L)).thenReturn(Optional.of(obj));
        mockMvc.perform(get("/api/v1/transferencias/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(transferenciaService.obtenerTransferenciaPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/transferencias/99")).andExpect(status().isNotFound());
    }

    @Test
    void testActualizar() throws Exception {
        Transferencia actualizado = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Mockito.when(transferenciaService.actualizarTransferencia(eq(1L), ArgumentMatchers.<Transferencia>any()))
                .thenReturn(actualizado);
        mockMvc.perform(put("/api/v1/transferencias/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new TransferenciaDTO(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now()))))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(transferenciaService).eliminarTransferencia(1L);
        mockMvc.perform(delete("/api/v1/transferencias/1")).andExpect(status().isNoContent());
    }

    @Test
    void testActualizarTransferenciaNoExistenteDevuelveNotFound() throws Exception {
        TransferenciaDTO req = new TransferenciaDTO(null, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());

        Mockito.when(transferenciaService.actualizarTransferencia(eq(99L), ArgumentMatchers.any()))
                .thenThrow(new RuntimeException("No existe transferencia con id: 99"));

        mockMvc.perform(put("/api/v1/transferencias/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    // Cobertura extra para TransferenciaDTO (Mapeos con datos)
    @Test
    void testTransferenciaDTOToEntityAndFromEntity() {
        TransferenciaDTO dto = new TransferenciaDTO(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Transferencia entity = dto.toEntity();
        org.assertj.core.api.Assertions.assertThat(entity).isNotNull();

        TransferenciaDTO nuevoDto = TransferenciaDTO.fromEntity(entity);
        org.assertj.core.api.Assertions.assertThat(nuevoDto).isNotNull();
    }
}