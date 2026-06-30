package com.bookpoint.despacho.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.despacho.dto.DespachoDTO;
import com.bookpoint.despacho.model.Despacho;
import com.bookpoint.despacho.repository.DespachoRepository;
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
class DespachoControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private DespachoRepository despachoRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { despachoRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        DespachoDTO dto = new DespachoDTO(null, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino");
        mockMvc.perform(post("/api/v1/despachos").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/despachos")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Despacho guardado = despachoRepository.save(new Despacho(null, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino"));
        mockMvc.perform(delete("/api/v1/despachos/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/despachos/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un despacho en BD con despachoRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/despachos/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
