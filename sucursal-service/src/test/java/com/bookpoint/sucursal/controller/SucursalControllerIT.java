package com.bookpoint.sucursal.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.sucursal.dto.SucursalDTO;
import com.bookpoint.sucursal.model.Sucursal;
import com.bookpoint.sucursal.repository.SucursalRepository;
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

@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class SucursalControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private SucursalRepository sucursalRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { sucursalRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        SucursalDTO dto = new SucursalDTO(null, "nombre", "direccion", "ciudad", "telefono", "horario");
        mockMvc.perform(post("/api/v1/sucursales").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/sucursales")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Sucursal guardado = sucursalRepository.save(new Sucursal(null, "nombre", "direccion", "ciudad", "telefono", "horario"));
        mockMvc.perform(delete("/api/v1/sucursales/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/sucursales/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un sucursal en BD con sucursalRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/sucursales/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
