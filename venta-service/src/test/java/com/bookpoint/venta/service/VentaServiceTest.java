package com.bookpoint.venta.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.bookpoint.venta.model.Venta;
import com.bookpoint.venta.model.VentaDetalle;
import com.bookpoint.venta.repository.VentaRepository;

public class VentaServiceTest {

    @Mock private VentaRepository ventaRepository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private VentaService ventaService;

    @BeforeEach 
    void setUp() {
        MockitoAnnotations.openMocks(this);
        org.springframework.test.util.ReflectionTestUtils.setField(
            ventaService, "inventarioUrl", "http://localhost:8085");
    }

    private Venta ventaConDetalle() {
        Venta v = new Venta();
        v.setUsuarioId(1L); 
        v.setSucursalId(1L); 
        v.setFechaVenta(LocalDate.now());
        
        VentaDetalle d = new VentaDetalle();
        d.setProductoId(1L); 
        d.setCantidad(2); 
        d.setPrecioUnitario(15990.0);
        
        v.setDetalles(new ArrayList<>(Arrays.asList(d)));
        return v;
    }

    @Test
    void testGuardarVentaConUnProducto() {
        Venta nueva = ventaConDetalle();
        Venta guardada = ventaConDetalle();
        guardada.setId(1L); 
        guardada.setTotal(31980.0);
        
        when(ventaRepository.save(any(Venta.class))).thenReturn(guardada);
        doNothing().when(restTemplate).put(anyString(), any());

        Venta resultado = ventaService.guardarVenta(nueva);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getDetalles()).hasSize(1);
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void testGuardarVentaCalculaTotal() {
        Venta nueva = new Venta();
        nueva.setUsuarioId(1L); 
        nueva.setSucursalId(1L); 
        nueva.setFechaVenta(LocalDate.now());
        
        VentaDetalle d1 = new VentaDetalle(); d1.setProductoId(1L); d1.setCantidad(2); d1.setPrecioUnitario(10000.0);
        VentaDetalle d2 = new VentaDetalle(); d2.setProductoId(2L); d2.setCantidad(1); d2.setPrecioUnitario(5000.0);
        
        nueva.setDetalles(new ArrayList<>(Arrays.asList(d1, d2)));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> { 
            Venta v = i.getArgument(0); 
            v.setId(1L); 
            return v; 
        });
        doNothing().when(restTemplate).put(anyString(), any());

        Venta resultado = ventaService.guardarVenta(nueva);

        assertThat(resultado.getTotal()).isEqualTo(25000.0);
    }

    @Test
    void testGuardarVentaCuandoInventarioFalla() {
        Venta nueva = ventaConDetalle();
        Venta guardada = ventaConDetalle();
        guardada.setId(1L);
        
        when(ventaRepository.save(any(Venta.class))).thenReturn(guardada);
        // Forzamos a que el restTemplate lance un error para probar el bloque catch
        doThrow(new RuntimeException("Error de conexión")).when(restTemplate).put(anyString(), any());

        Venta resultado = ventaService.guardarVenta(nueva);

        assertThat(resultado.getId()).isEqualTo(1L);
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void testListarVentas() {
        Venta v = ventaConDetalle(); v.setId(1L);
        when(ventaRepository.findAll()).thenReturn(Arrays.asList(v));
        
        List<Venta> resultado = ventaService.listarVentas();
        
        assertThat(resultado).hasSize(1);
        verify(ventaRepository).findAll();
    }

    @Test
    void testObtenerVentaPorIdExistente() {
        Venta v = ventaConDetalle(); v.setId(1L);
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(v));
        
        Optional<Venta> resultado = ventaService.obtenerVentaPorId(1L);
        
        assertThat(resultado).isPresent();
        verify(ventaRepository).findById(1L);
    }

    @Test
    void testObtenerVentaPorIdNoExistente() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());
        
        Optional<Venta> resultado = ventaService.obtenerVentaPorId(99L);
        
        assertThat(resultado).isEmpty();
    }

    @Test
    void testActualizarVentaExistente() {
        Venta existente = ventaConDetalle();
        existente.setId(1L);
        Venta datosNuevos = ventaConDetalle();
        datosNuevos.setTotal(50000.0);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArgument(0));

        Venta resultado = ventaService.actualizarVenta(1L, datosNuevos);

        assertThat(resultado.getTotal()).isEqualTo(50000.0);
        verify(ventaRepository).save(existente);
    }

    @Test
    void testActualizarVentaNoExistenteLanzaExcepcion() {
        Venta datosNuevos = ventaConDetalle();
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            ventaService.actualizarVenta(99L, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No existe venta con id: 99");
    }

    @Test
    void testEliminarVenta() {
        doNothing().when(ventaRepository).deleteById(1L);
        
        ventaService.eliminarVenta(1L);
        
        verify(ventaRepository).deleteById(1L);
    }
}