package com.bookpoint.devolucion.service;
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
import com.bookpoint.devolucion.model.Devolucion;
import com.bookpoint.devolucion.repository.DevolucionRepository;

public class DevolucionServiceTest {
    @Mock private DevolucionRepository devolucionRepository;
    @InjectMocks private DevolucionService devolucionService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarDevolucion() {
        Devolucion nuevo   = new Devolucion(null, 1L, 1L, "motivo", "estado", java.time.LocalDate.now());
        Devolucion guardado = new Devolucion(1L, 1L, 1L, "motivo", "estado", java.time.LocalDate.now());
        when(devolucionRepository.save(nuevo)).thenReturn(guardado);
        Devolucion resultado = devolucionService.guardarDevolucion(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(devolucionRepository).save(nuevo);
    }

    @Test
    void testListarDevolucions() {
        List<Devolucion> lista = Arrays.asList(new Devolucion(1L, 1L, 1L, "motivo", "estado", java.time.LocalDate.now()));
        when(devolucionRepository.findAll()).thenReturn(lista);
        List<Devolucion> resultado = devolucionService.listarDevolucions();
        assertThat(resultado).hasSize(1);
        verify(devolucionRepository).findAll();
    }

    @Test
    void testObtenerDevolucionPorIdExistente() {
        Devolucion obj = new Devolucion(1L, 1L, 1L, "motivo", "estado", java.time.LocalDate.now());
        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Devolucion> resultado = devolucionService.obtenerDevolucionPorId(1L);
        assertThat(resultado).isPresent();
        verify(devolucionRepository).findById(1L);
    }

    @Test
    void testObtenerDevolucionPorIdNoExistente() {
        when(devolucionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Devolucion> resultado = devolucionService.obtenerDevolucionPorId(99L);
        assertThat(resultado).isEmpty();
        verify(devolucionRepository).findById(99L);
    }

    @Test
    void testActualizarDevolucion() {
        Devolucion existente = new Devolucion(1L, 1L, 1L, "motivo", "estado", java.time.LocalDate.now());
        Devolucion nuevo     = new Devolucion(null, 1L, 1L, "alt-motivo", "alt-estado", java.time.LocalDate.now());
        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(devolucionRepository.save(any(Devolucion.class))).thenAnswer(i -> i.getArgument(0));
        Devolucion resultado = devolucionService.actualizarDevolucion(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(devolucionRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarDevolucion
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(devolucionRepository).deleteById(1L);
    //   2. devolucionService.eliminarDevolucion(1L);
    //   3. verify(devolucionRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarDevolucion() {
        // TODO: implementar esta prueba en vivo
    }
}
