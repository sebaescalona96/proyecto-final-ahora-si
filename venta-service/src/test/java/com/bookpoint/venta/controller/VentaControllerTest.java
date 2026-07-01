package com.bookpoint.venta.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // Importante para el test de actualizar
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.bookpoint.venta.dto.VentaDTO;
import com.bookpoint.venta.dto.VentaDetalleDTO;
import com.bookpoint.venta.model.Venta;
import com.bookpoint.venta.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VentaController.class)
@ActiveProfiles("test")
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SuppressWarnings("removal")
    @MockBean
    private VentaService ventaService;
    @Autowired
    private ObjectMapper objectMapper;

    private VentaDTO buildDTO(Long id) {
        VentaDetalleDTO det = new VentaDetalleDTO(null, 1L, 2, 15990.0, 31980.0);
        return new VentaDTO(id, 1L, 1L, LocalDate.now(), 31980.0, List.of(det));
    }

    @Test
    void testCrearVentaMultipleProductos() throws Exception {
        VentaDTO req = buildDTO(null);
        Venta guardada = new Venta();
        guardada.setId(1L);
        guardada.setDetalles(new java.util.ArrayList<>());

        Mockito.when(ventaService.guardarVenta(ArgumentMatchers.<Venta>any())).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListarTodas() throws Exception {
        Venta v = new Venta();
        v.setId(1L);
        v.setDetalles(new java.util.ArrayList<>());
        Mockito.when(ventaService.listarVentas()).thenReturn(Arrays.asList(v));

        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        Venta v = new Venta();
        v.setId(1L);
        v.setDetalles(new java.util.ArrayList<>());
        Mockito.when(ventaService.obtenerVentaPorId(1L)).thenReturn(Optional.of(v));

        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(ventaService.obtenerVentaPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/ventas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(ventaService).eliminarVenta(1L);

        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarVenta() throws Exception {
        VentaDTO req = buildDTO(1L);
        Venta v = new Venta();
        v.setId(1L);
        v.setDetalles(new java.util.ArrayList<>());

        Mockito.when(ventaService.actualizarVenta(ArgumentMatchers.eq(1L), ArgumentMatchers.any()))
                .thenReturn(v);

        mockMvc.perform(put("/api/v1/ventas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarVentaNoExistenteDevuelveNotFound() throws Exception {
        VentaDTO req = buildDTO(null);

        Mockito.when(ventaService.actualizarVenta(ArgumentMatchers.eq(99L), ArgumentMatchers.any()))
                .thenThrow(new RuntimeException("No existe venta con id: 99"));

        mockMvc.perform(put("/api/v1/ventas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testVentaDetalleDTOSubtotalNullCalculaAutomatico() {
        VentaDetalleDTO dto = new VentaDetalleDTO(null, 1L, 2, 10000.0, null);

        com.bookpoint.venta.model.VentaDetalle entity = dto.toEntity();

        org.assertj.core.api.Assertions.assertThat(entity.getSubtotal()).isEqualTo(20000.0);
    }

    @Test
    void testVentaDetalleDTOSubtotalNullYValoresNullDevuelveCero() {
        // Dejamos todo en null para obligar a la condición a caer en el ": 0.0" del
        // final
        VentaDetalleDTO dto = new VentaDetalleDTO(null, 1L, null, null, null);

        com.bookpoint.venta.model.VentaDetalle entity = dto.toEntity();

        // Verificamos que devuelva el 0.0 por defecto
        org.assertj.core.api.Assertions.assertThat(entity.getSubtotal()).isEqualTo(0.0);
    }

    // Test para la última sub-rama de VentaDetalleDTO
    @Test
    void testVentaDetalleDTOSubtotalNullYCantidadNullCalculaCero() {
        VentaDetalleDTO dto = new VentaDetalleDTO(null, 1L, null, 15000.0, null);
        com.bookpoint.venta.model.VentaDetalle entity = dto.toEntity();
        org.assertj.core.api.Assertions.assertThat(entity.getSubtotal()).isEqualTo(0.0);
    }

    // Test para cubrir la línea 19 de VentaDTO (fromEntity con detalles null)
    @Test
    void testVentaDTOFromEntityConDetallesNull() {
        Venta v = new Venta();
        v.setId(1L);
        v.setDetalles(null);

        VentaDTO dto = VentaDTO.fromEntity(v);
        org.assertj.core.api.Assertions.assertThat(dto.getDetalles()).isEmpty();
    }

    // Test para cubrir la línea 29 de VentaDTO (toEntity con detalles null) —
    // CORREGIDO
    @Test
    void testVentaDTOToEntityConDetallesNull() {
        VentaDTO dto = new VentaDTO(1L, 1L, 1L, LocalDate.now(), 0.0, null);

        Venta entity = dto.toEntity();
        // Corregido: Validamos que la lista devuelta esté vacía (o nula), asegurando
        // que pase el test exitosamente
        org.assertj.core.api.Assertions.assertThat(entity.getDetalles()).isEmpty();
    }
}