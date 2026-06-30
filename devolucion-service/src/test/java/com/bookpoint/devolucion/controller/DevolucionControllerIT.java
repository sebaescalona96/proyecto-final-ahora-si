package com.bookpoint.devolucion.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.devolucion.dto.DevolucionDTO;
import com.bookpoint.devolucion.model.Devolucion;
import com.bookpoint.devolucion.repository.DevolucionRepository;
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
class DevolucionControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private DevolucionRepository devolucionRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { devolucionRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        DevolucionDTO dto = new DevolucionDTO(null, 1L, 1L, "motivo", "estado", java.time.LocalDate.now());
        mockMvc.perform(post("/api/v1/devoluciones").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/devoluciones")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Devolucion guardado = devolucionRepository.save(new Devolucion(null, 1L, 1L, "motivo", "estado", java.time.LocalDate.now()));
        mockMvc.perform(delete("/api/v1/devoluciones/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/devoluciones/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un devolucion en BD con devolucionRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/devoluciones/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
