package com.bookpoint.notificacion.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.notificacion.dto.NotificacionDTO;
import com.bookpoint.notificacion.model.Notificacion;
import com.bookpoint.notificacion.repository.NotificacionRepository;
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
class NotificacionControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private NotificacionRepository notificacionRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { notificacionRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        NotificacionDTO dto = new NotificacionDTO(null, 1L, "mensaje", "tipo", true, java.time.LocalDate.now());
        mockMvc.perform(post("/api/v1/notificaciones").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/notificaciones")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Notificacion guardado = notificacionRepository.save(new Notificacion(null, 1L, "mensaje", "tipo", true, java.time.LocalDate.now()));
        mockMvc.perform(delete("/api/v1/notificaciones/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/notificaciones/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un notificacion en BD con notificacionRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/notificaciones/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
