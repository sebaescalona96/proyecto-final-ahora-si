package com.bookpoint.resena.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.resena.dto.ResenaDTO;
import com.bookpoint.resena.model.Resena;
import com.bookpoint.resena.repository.ResenaRepository;
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
class ResenaControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private ResenaRepository resenaRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { resenaRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        ResenaDTO dto = new ResenaDTO(null, 1L, 1L, "comentario", 5, java.time.LocalDate.now());
        mockMvc.perform(post("/api/v1/resenas").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/resenas")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Resena guardado = resenaRepository.save(new Resena(null, 1L, 1L, "comentario", 5, java.time.LocalDate.now()));
        mockMvc.perform(delete("/api/v1/resenas/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/resenas/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un resena en BD con resenaRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/resenas/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
