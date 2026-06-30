package com.bookpoint.proveedor.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.proveedor.dto.ProveedorDTO;
import com.bookpoint.proveedor.model.Proveedor;
import com.bookpoint.proveedor.repository.ProveedorRepository;
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
class ProveedorControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private ProveedorRepository proveedorRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach void limpiar() { proveedorRepository.deleteAll(); }

    @Test
    void testCrearYListar() throws Exception {
        ProveedorDTO dto = new ProveedorDTO(null, "nombre", "email", "telefono", "editorial", true);
        mockMvc.perform(post("/api/v1/proveedores").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
        mockMvc.perform(get("/api/v1/proveedores")).andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testEliminar() throws Exception {
        Proveedor guardado = proveedorRepository.save(new Proveedor(null, "nombre", "email", "telefono", "editorial", true));
        mockMvc.perform(delete("/api/v1/proveedores/" + guardado.getId())).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/proveedores/" + guardado.getId())).andExpect(status().isNotFound());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizar
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. Guardar un proveedor en BD con proveedorRepository.save(...)
    //   2. Crear un DTO con datos diferentes
    //   3. PUT /api/v1/proveedores/id con el DTO como body
    //   4. Verificar status().isOk()
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testActualizar() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
