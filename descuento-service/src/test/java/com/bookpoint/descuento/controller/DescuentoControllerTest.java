package com.bookpoint.descuento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.descuento.dto.DescuentoDTO;
import com.bookpoint.descuento.model.Descuento;
import com.bookpoint.descuento.service.DescuentoService;
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

@WebMvcTest(DescuentoController.class)
@ActiveProfiles("test")
public class DescuentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private DescuentoService descuentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(descuentoService.listarDescuentos())
                .thenReturn(Arrays.asList(new Descuento()));

        mockMvc.perform(get("/api/v1/descuentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        DescuentoDTO dto = new DescuentoDTO();
        Descuento guardado = new Descuento();
        guardado.setId(1L);

        Mockito.when(descuentoService.guardarDescuento(ArgumentMatchers.any(Descuento.class)))
                .thenReturn(guardado);

        mockMvc.perform(post("/api/v1/descuentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Descuento obj = new Descuento();
        obj.setId(1L);

        Mockito.when(descuentoService.obtenerDescuentoPorId(1L))
                .thenReturn(Optional.of(obj));

        mockMvc.perform(get("/api/v1/descuentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(descuentoService.obtenerDescuentoPorId(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/descuentos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarExitoso() throws Exception {
        Descuento actualizado = new Descuento();
        actualizado.setId(1L);

        Mockito.when(descuentoService.actualizarDescuento(eq(1L), ArgumentMatchers.any(Descuento.class)))
                .thenReturn(actualizado);

        DescuentoDTO dto = new DescuentoDTO();

        mockMvc.perform(put("/api/v1/descuentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarErrorDevuelveNotFound() throws Exception {
        Mockito.when(descuentoService.actualizarDescuento(eq(99L), ArgumentMatchers.any(Descuento.class)))
                .thenThrow(new RuntimeException("No existe"));

        DescuentoDTO dto = new DescuentoDTO();

        mockMvc.perform(put("/api/v1/descuentos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(descuentoService).eliminarDescuento(1L);

        mockMvc.perform(delete("/api/v1/descuentos/1"))
                .andExpect(status().isNoContent());
    }
}