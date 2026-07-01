package com.bookpoint.inventario.service;

import com.bookpoint.inventario.model.Inventario;
import com.bookpoint.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarInventario() {
        Inventario nuevo = new Inventario(null, 1L, 1L, 5, 5);
        Inventario guardado = new Inventario(1L, 1L, 1L, 5, 5);
        when(inventarioRepository.save(nuevo)).thenReturn(guardado);
        Inventario resultado = inventarioService.guardarInventario(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(inventarioRepository).save(nuevo);
    }

    @Test
    void testListarInventarios() {
        List<Inventario> lista = Arrays.asList(new Inventario(1L, 1L, 1L, 5, 5));
        when(inventarioRepository.findAll()).thenReturn(lista);
        List<Inventario> resultado = inventarioService.listarInventarios();
        assertThat(resultado).hasSize(1);
        verify(inventarioRepository).findAll();
    }

    @Test
    void testObtenerInventarioPorIdExistente() {
        Inventario obj = new Inventario(1L, 1L, 1L, 5, 5);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Inventario> resultado = inventarioService.obtenerInventarioPorId(1L);
        assertThat(resultado).isPresent();
        verify(inventarioRepository).findById(1L);
    }

    @Test
    void testObtenerInventarioPorIdNoExistente() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Inventario> resultado = inventarioService.obtenerInventarioPorId(99L);
        assertThat(resultado).isEmpty();
        verify(inventarioRepository).findById(99L);
    }

    @Test
    void testActualizarInventario() {
        Inventario existente = new Inventario(1L, 1L, 1L, 5, 5);
        Inventario nuevo = new Inventario(null, 1L, 1L, 10, 10);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));
        Inventario resultado = inventarioService.actualizarInventario(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(inventarioRepository).save(existente);
    }

    @Test
    void testActualizarInventarioNoExistenteLanzaExcepcion() {
        Long idInexistente = 99L;
        com.bookpoint.inventario.model.Inventario datosNuevos = new com.bookpoint.inventario.model.Inventario();
        datosNuevos.setId(idInexistente);

        Mockito.when(inventarioRepository.findById(idInexistente))
                .thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> {
            inventarioService.actualizarInventario(idInexistente, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe inventario con id: " + idInexistente);
    }

    @Test
    void testObtenerInventarioPorProductoIdLanzaExceptionMetodoNoImplementado() {
        assertThatThrownBy(() -> {
            inventarioService.obtainerInventarioPorProductoId(5L);
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Unimplemented method 'obtainerInventarioPorProductoId'");
    }

    @Test
    void testDescontarStockExitoso() {
        Inventario inv = new Inventario(1L, 1L, 1L, 20, 5);
        when(inventarioRepository.findByProductoIdAndSucursalId(1L, 1L)).thenReturn(Optional.of(inv));

        inventarioService.descontarStock(1L, 1L, 5);

        assertThat(inv.getStock()).isEqualTo(15);
        verify(inventarioRepository).save(inv);
    }

    @Test
    void testDescontarStockInexistenteLanzaExcepcion() {
        when(inventarioRepository.findByProductoIdAndSucursalId(1L, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            inventarioService.descontarStock(1L, 1L, 5);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No hay inventario para productoId=1 sucursalId=1");
    }

    @Test
    void testDescontarStockInsuficienteLanzaExcepcion() {
        Inventario inv = new Inventario(1L, 1L, 1L, 3, 1);
        when(inventarioRepository.findByProductoIdAndSucursalId(1L, 1L)).thenReturn(Optional.of(inv));

        assertThatThrownBy(() -> {
            inventarioService.descontarStock(1L, 1L, 10);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Stock insuficiente. Disponible: 3, requerido: 10");
    }
}