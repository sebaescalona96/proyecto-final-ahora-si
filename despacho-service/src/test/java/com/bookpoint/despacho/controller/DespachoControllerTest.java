package com.bookpoint.despacho.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.despacho.dto.DespachoDTO;
import com.bookpoint.despacho.model.Despacho;
import com.bookpoint.despacho.service.DespachoService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DespachoController.class)
@ActiveProfiles("test")
public class DespachoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private DespachoService despachoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(despachoService.listarDespachos())
                .thenReturn(Arrays.asList(new Despacho()));

        mockMvc.perform(get("/api/v1/despachos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        DespachoDTO dto = new DespachoDTO();
        Despacho guardado = new Despacho();
        guardado.setId(1L);

        Mockito.when(despachoService.guardarDespacho(ArgumentMatchers.any(Despacho.class)))
                .thenReturn(guardado);

        mockMvc.perform(post("/api/v1/despachos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Despacho obj = new Despacho();
        obj.setId(1L);

        Mockito.when(despachoService.obtenerDespachoPorId(1L))
                .thenReturn(Optional.of(obj));

        mockMvc.perform(get("/api/v1/despachos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(despachoService.obtenerDespachoPorId(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/despachos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarExitoso() throws Exception {
        Despacho actualizado = new Despacho();
        actualizado.setId(1L);

        Mockito.when(despachoService.actualizarDespacho(eq(1L), ArgumentMatchers.any(Despacho.class)))
                .thenReturn(actualizado);

        DespachoDTO dto = new DespachoDTO();

        mockMvc.perform(put("/api/v1/despachos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarErrorDevuelveNotFound() throws Exception {
        Mockito.when(despachoService.actualizarDespacho(eq(99L), ArgumentMatchers.any(Despacho.class)))
                .thenThrow(new RuntimeException("No existe"));

        DespachoDTO dto = new DespachoDTO();

        mockMvc.perform(put("/api/v1/despachos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(despachoService).eliminarDespacho(1L);

        mockMvc.perform(delete("/api/v1/despachos/1"))
                .andExpect(status().isNoContent());
    }
}