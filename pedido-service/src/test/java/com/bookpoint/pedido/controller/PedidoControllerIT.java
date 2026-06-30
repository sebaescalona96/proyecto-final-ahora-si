package com.bookpoint.pedido.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.pedido.dto.PedidoDTO;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.repository.PedidoRepository;
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
class PedidoControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { pedidoRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        PedidoDTO dto = new PedidoDTO(null, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega");
        mockMvc.perform(post("/api/v1/pedidos").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/pedidos")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Pedido guardado = pedidoRepository.save(new Pedido(null, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega"));
        mockMvc.perform(delete("/api/v1/pedidos/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/pedidos/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un pedido en BD con pedidoRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/pedidos/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
