package com.bookpoint.inventario.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.bookpoint.inventario.model.Inventario;
import com.bookpoint.inventario.repository.InventarioRepository;

public class InventarioServiceTest {
    @Mock private InventarioRepository inventarioRepository;
    @InjectMocks private InventarioService inventarioService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarInventario() {
        Inventario nuevo   = new Inventario(null, 1L, 1L, 5, 5);
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
        Inventario nuevo     = new Inventario(null, 1L, 1L, 10, 10);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));
        Inventario resultado = inventarioService.actualizarInventario(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(inventarioRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarInventario
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(inventarioRepository).deleteById(1L);
    //   2. inventarioService.eliminarInventario(1L);
    //   3. verify(inventarioRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarInventario() {
        // TODO: implementar esta prueba en vivo
    }
}
