package com.bookpoint.inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.inventario.dto.InventarioDTO;
import com.bookpoint.inventario.model.Inventario;
import com.bookpoint.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InventarioControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void limpiar() {
        inventarioRepository.deleteAll();
    }

    @Test
    void testCrearYListar() throws Exception {
        InventarioDTO dto = new InventarioDTO(null, 1L, 1L, 5, 5);

        mockMvc.perform(post("/api/v1/inventario").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/api/v1/inventario")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Inventario guardado = inventarioRepository.save(new Inventario(null, 1L, 1L, 5, 5));
        mockMvc.perform(delete("/api/v1/inventario/" + guardado.getId())).andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/inventario/" + guardado.getId())).andExpect(status().isNotFound());
    }

    @Test
    void testActualizar() throws Exception {
        // 1. Guardar un inventario en BD
        Inventario guardado = inventarioRepository.save(new Inventario(null, 1L, 1L, 5, 5));

        // 2. Crear un DTO con datos diferentes
        InventarioDTO dtoModificado = new InventarioDTO(guardado.getId(), 1L, 1L, 25, 5);

        // 3. PUT /api/v1/inventario/id con el DTO como body
        mockMvc.perform(put("/api/v1/inventario/" + guardado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoModificado)))
                // 4. Verificar status().isOk()
                .andExpect(status().isOk());
    }
}
