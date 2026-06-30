package com.bookpoint.producto.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.producto.dto.ProductoDTO;
import com.bookpoint.producto.model.Producto;
import com.bookpoint.producto.repository.ProductoRepository;
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
class ProductoControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { productoRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        ProductoDTO dto = new ProductoDTO(null, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial");
        mockMvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/productos")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Producto guardado = productoRepository.save(new Producto(null, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial"));
        mockMvc.perform(delete("/api/v1/productos/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/productos/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un producto en BD con productoRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/productos/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
