package com.bookpoint.transferencia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.transferencia.dto.TransferenciaDTO;
import com.bookpoint.transferencia.model.Transferencia;
import com.bookpoint.transferencia.repository.TransferenciaRepository;
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
class TransferenciaControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void limpiar() {
        transferenciaRepository.deleteAll();
    }

    @Test
    void testCrearYListar() throws Exception {
        TransferenciaDTO dto = new TransferenciaDTO(null, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        mockMvc.perform(post("/api/v1/transferencias").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/transferencias")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Transferencia guardado = transferenciaRepository
                .save(new Transferencia(null, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now()));
        mockMvc.perform(delete("/api/v1/transferencias/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/transferencias/" + guardado.getId())).andExpect(status().isNotFound());
    }
}
