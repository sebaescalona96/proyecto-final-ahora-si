package com.bookpoint.venta.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.venta.dto.VentaDTO;
import com.bookpoint.venta.dto.VentaDetalleDTO;
import com.bookpoint.venta.service.VentaService;
import com.bookpoint.venta.model.Venta;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
@ActiveProfiles("test")
public class VentaControllerTest {
    @Autowired private MockMvc mockMvc;
    @SuppressWarnings("removal") @MockBean private VentaService ventaService;
    @Autowired private ObjectMapper objectMapper;

    private VentaDTO buildDTO(Long id) {
        VentaDetalleDTO det = new VentaDetalleDTO(null, 1L, 2, 15990.0, 31980.0);
        return new VentaDTO(id, 1L, 1L, LocalDate.now(), 31980.0, List.of(det));
    }

    @Test void testCrearVentaMultipleProductos() throws Exception {
        VentaDTO req = buildDTO(null);
        Venta guardada = new Venta(); guardada.setId(1L);
        guardada.setDetalles(new java.util.ArrayList<>());
        Mockito.when(ventaService.guardarVenta(ArgumentMatchers.<Venta>any())).thenReturn(guardada);
        mockMvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test void testListarTodas() throws Exception {
        Venta v = new Venta(); v.setId(1L); v.setDetalles(new java.util.ArrayList<>());
        Mockito.when(ventaService.listarVentas()).thenReturn(Arrays.asList(v));
        mockMvc.perform(get("/api/v1/ventas")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void testObtenerPorIdExistente() throws Exception {
        Venta v = new Venta(); v.setId(1L); v.setDetalles(new java.util.ArrayList<>());
        Mockito.when(ventaService.obtenerVentaPorId(1L)).thenReturn(Optional.of(v));
        mockMvc.perform(get("/api/v1/ventas/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(ventaService.obtenerVentaPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/ventas/99")).andExpect(status().isNotFound());
    }

    @Test void testEliminar() throws Exception {
        Mockito.doNothing().when(ventaService).eliminarVenta(1L);
        mockMvc.perform(delete("/api/v1/ventas/1")).andExpect(status().isNoContent());
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testActualizarVenta
    // Dificultad: ⭐⭐ (MEDIO) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // ══════════════════════════════════════════════════════════════════
    @Test void testActualizarVenta() throws Exception {
        // TODO: implementar esta prueba en vivo
    }
}
