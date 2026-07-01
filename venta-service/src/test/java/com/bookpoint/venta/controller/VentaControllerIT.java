package com.bookpoint.venta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookpoint.venta.dto.VentaDTO;
import com.bookpoint.venta.dto.VentaDetalleDTO;
import com.bookpoint.venta.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VentaControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @SuppressWarnings("removal")
    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void limpiar() {
        ventaRepository.deleteAll();
        doNothing().when(restTemplate).put(anyString(), any());
    }

    @Test
    void testCrearVentaConMultiplesProductos() throws Exception {
        VentaDetalleDTO d1 = new VentaDetalleDTO(null, 1L, 2, 15990.0, null);
        VentaDetalleDTO d2 = new VentaDetalleDTO(null, 2L, 1, 9990.0, null);
        VentaDTO dto = new VentaDTO(null, 1L, 1L, LocalDate.now(), null, List.of(d1, d2));
        mockMvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.total").value(41970.0))
                .andExpect(jsonPath("$.detalles").isArray());
    }

    @Test
    void testEliminarVenta() throws Exception {
        VentaDetalleDTO d = new VentaDetalleDTO(null, 1L, 1, 5000.0, null);
        VentaDTO dto = new VentaDTO(null, 1L, 1L, LocalDate.now(), null, List.of(d));
        String resp = mockMvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(resp).get("id").asLong();
        mockMvc.perform(delete("/api/v1/ventas/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/ventas/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void testVentaDetalleDTOSubtotalNullCalculaAutomatico() {
        // Creamos un DTO dejando el subtotal explícitamente en null
        VentaDetalleDTO dto = new VentaDetalleDTO(null, 1L, 2, 10000.0, null);

        // Ejecutamos el método que tiene la línea roja
        com.bookpoint.venta.model.VentaDetalle entity = dto.toEntity();

        // Verificamos que la multiplicación interna (2 * 10000.0) se haya ejecutado con
        // éxito
        org.assertj.core.api.Assertions.assertThat(entity.getSubtotal()).isEqualTo(20000.0);
    }
}
