package com.bookpoint.descuento.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.descuento.dto.DescuentoDTO;
import com.bookpoint.descuento.model.Descuento;
import com.bookpoint.descuento.repository.DescuentoRepository;
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
class DescuentoControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private DescuentoRepository descuentoRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { descuentoRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        DescuentoDTO dto = new DescuentoDTO(null, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now());
        mockMvc.perform(post("/api/v1/descuentos").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/descuentos")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Descuento guardado = descuentoRepository.save(new Descuento(null, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now()));
        mockMvc.perform(delete("/api/v1/descuentos/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/descuentos/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un descuento en BD con descuentoRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/descuentos/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
