package com.bookpoint.proveedor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.proveedor.dto.ProveedorDTO;
import com.bookpoint.proveedor.service.ProveedorService;
import com.bookpoint.proveedor.model.Proveedor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProveedorController.class)
@ActiveProfiles("test")
public class ProveedorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SuppressWarnings("removal")
    @MockBean
    private ProveedorService proveedorService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodos() throws Exception {
        ProveedorDTO dto = new ProveedorDTO(1L, "nombre", "email", "telefono", "editorial", true);
        Mockito.when(proveedorService.listarProveedors())
                .thenReturn(Arrays.asList(new Proveedor(1L, "nombre", "email", "telefono", "editorial", true)));
        Mockito.when(proveedorService.listarProveedors())
                .thenReturn(Arrays.asList(new Proveedor(1L, "nombre", "email", "telefono", "editorial", true)));
        mockMvc.perform(get("/api/v1/proveedores")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCrear() throws Exception {
        ProveedorDTO dto = new ProveedorDTO(null, "nombre", "email", "telefono", "editorial", true);
        Proveedor guardado = new Proveedor(1L, "nombre", "email", "telefono", "editorial", true);
        Mockito.when(proveedorService.guardarProveedor(ArgumentMatchers.<Proveedor>any())).thenReturn(guardado);
        mockMvc.perform(post("/api/v1/proveedores").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Proveedor obj = new Proveedor(1L, "nombre", "email", "telefono", "editorial", true);
        Mockito.when(proveedorService.obtenerProveedorPorId(1L)).thenReturn(Optional.of(obj));
        mockMvc.perform(get("/api/v1/proveedores/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(proveedorService.obtenerProveedorPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/proveedores/99")).andExpect(status().isNotFound());
    }

    @Test
    void testActualizar() throws Exception {
        Proveedor actualizado = new Proveedor(1L, "nombre", "email", "telefono", "editorial", true);
        Mockito.when(proveedorService.actualizarProveedor(eq(1L), ArgumentMatchers.<Proveedor>any()))
                .thenReturn(actualizado);
        mockMvc.perform(put("/api/v1/proveedores/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(new ProveedorDTO(1L, "nombre", "email", "telefono", "editorial", true))))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(proveedorService).eliminarProveedor(1L);
        mockMvc.perform(delete("/api/v1/proveedores/1")).andExpect(status().isNoContent());
    }

    @Test
    void testActualizarProveedorNoExistenteDevuelveNotFound() throws Exception {
        // Inicializamos el DTO con setters válidos para evitar problemas de compilación o respuestas 200 OK
        com.bookpoint.proveedor.dto.ProveedorDTO req = new com.bookpoint.proveedor.dto.ProveedorDTO();
        req.setNombre("Proveedor Falso");
        req.setEmail("test@proveedor.com");
        req.setTelefono("912345678");

        // Simulamos que el servicio lanza la RuntimeException al buscar el ID 99
        org.mockito.Mockito
                .when(proveedorService.actualizarProveedor(org.mockito.Mockito.eq(99L), org.mockito.Mockito.any()))
                .thenThrow(new RuntimeException("No existe proveedor con id: 99"));

        // Ejecutamos la petición PUT esperando un 404 Not Found
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/proveedores/99")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNotFound());
    }
}
