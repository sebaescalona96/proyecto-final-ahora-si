package com.bookpoint.inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.inventario.dto.InventarioDTO;
import com.bookpoint.inventario.dto.DescontarStockDTO;
import com.bookpoint.inventario.model.Inventario;
import com.bookpoint.inventario.service.InventarioService;
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

@WebMvcTest(InventarioController.class)
@ActiveProfiles("test")
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(inventarioService.listarInventarios())
                .thenReturn(Arrays.asList(new Inventario()));

        mockMvc.perform(get("/api/v1/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1L);
        dto.setStock(5);

        Inventario guardado = new Inventario();
        guardado.setId(1L);

        Mockito.when(inventarioService.guardarInventario(ArgumentMatchers.<Inventario>any()))
                .thenReturn(guardado);

        mockMvc.perform(post("/api/v1/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Inventario obj = new Inventario();
        obj.setId(1L);

        Mockito.when(inventarioService.obtenerInventarioPorId(1L))
                .thenReturn(Optional.of(obj));

        mockMvc.perform(get("/api/v1/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(inventarioService.obtenerInventarioPorId(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/inventario/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizar() throws Exception {
        Inventario actualizado = new Inventario();
        actualizado.setId(1L);

        Mockito.when(inventarioService.actualizarInventario(eq(1L), ArgumentMatchers.<Inventario>any()))
                .thenReturn(actualizado);

        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1L);
        dto.setStock(5);

        mockMvc.perform(put("/api/v1/inventario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(inventarioService).eliminarInventario(1L);

        mockMvc.perform(delete("/api/v1/inventario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarInventarioNoExistenteDevuelveNotFound() throws Exception {
        com.bookpoint.inventario.dto.InventarioDTO req = new com.bookpoint.inventario.dto.InventarioDTO();
        req.setProductoId(1L);
        req.setStock(10);

        Mockito.when(inventarioService.actualizarInventario(eq(99L), ArgumentMatchers.<Inventario>any()))
                .thenThrow(new RuntimeException("No existe inventario con id: 99"));

        mockMvc.perform(put("/api/v1/inventario/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDescontarStockExitoso() throws Exception {
        DescontarStockDTO dto = new DescontarStockDTO(1L, 1L, 5);
        Mockito.doNothing().when(inventarioService).descontarStock(1L, 1L, 5);

        mockMvc.perform(put("/api/v1/inventario/descontar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDescontarStockErrorDevuelveBadRequest() throws Exception {
        DescontarStockDTO dto = new DescontarStockDTO(1L, 1L, 100);
        Mockito.doThrow(new RuntimeException("Stock insuficiente"))
                .when(inventarioService).descontarStock(1L, 1L, 100);

        mockMvc.perform(put("/api/v1/inventario/descontar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}