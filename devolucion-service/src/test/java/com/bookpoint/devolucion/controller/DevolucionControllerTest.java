package com.bookpoint.devolucion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.devolucion.dto.DevolucionDTO;
import com.bookpoint.devolucion.model.Devolucion;
import com.bookpoint.devolucion.service.DevolucionService;
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

@WebMvcTest(DevolucionController.class)
@ActiveProfiles("test")
public class DevolucionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private DevolucionService devolucionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(devolucionService.listarDevoluciones())
                .thenReturn(Arrays.asList(new Devolucion()));

        mockMvc.perform(get("/api/v1/devoluciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        DevolucionDTO dto = new DevolucionDTO();
        Devolucion guardado = new Devolucion();
        guardado.setId(1L);

        Mockito.when(devolucionService.guardarDevolucion(ArgumentMatchers.any(Devolucion.class)))
                .thenReturn(guardado);

        mockMvc.perform(post("/api/v1/devoluciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Devolucion obj = new Devolucion();
        obj.setId(1L);

        Mockito.when(devolucionService.obtenerDevolucionPorId(1L))
                .thenReturn(Optional.of(obj));

        mockMvc.perform(get("/api/v1/devoluciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(devolucionService.obtenerDevolucionPorId(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/devoluciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarExitoso() throws Exception {
        Devolucion actualizado = new Devolucion();
        actualizado.setId(1L);

        Mockito.when(devolucionService.actualizarDevolucion(eq(1L), ArgumentMatchers.any(Devolucion.class)))
                .thenReturn(actualizado);

        DevolucionDTO dto = new DevolucionDTO();

        mockMvc.perform(put("/api/v1/devoluciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarErrorDevuelveNotFound() throws Exception {
        Mockito.when(devolucionService.actualizarDevolucion(eq(99L), ArgumentMatchers.any(Devolucion.class)))
                .thenThrow(new RuntimeException("No existe"));

        DevolucionDTO dto = new DevolucionDTO();

        mockMvc.perform(put("/api/v1/devoluciones/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(devolucionService).eliminarDevolucion(1L);

        mockMvc.perform(delete("/api/v1/devoluciones/1"))
                .andExpect(status().isNoContent());
    }
}