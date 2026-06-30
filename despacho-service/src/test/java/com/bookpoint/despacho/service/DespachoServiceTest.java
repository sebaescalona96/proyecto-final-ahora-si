package com.bookpoint.despacho.service;
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
import com.bookpoint.despacho.model.Despacho;
import com.bookpoint.despacho.repository.DespachoRepository;

public class DespachoServiceTest {
    @Mock private DespachoRepository despachoRepository;
    @InjectMocks private DespachoService despachoService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarDespacho() {
        Despacho nuevo   = new Despacho(null, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino");
        Despacho guardado = new Despacho(1L, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino");
        when(despachoRepository.save(nuevo)).thenReturn(guardado);
        Despacho resultado = despachoService.guardarDespacho(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(despachoRepository).save(nuevo);
    }

    @Test
    void testListarDespachos() {
        List<Despacho> lista = Arrays.asList(new Despacho(1L, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino"));
        when(despachoRepository.findAll()).thenReturn(lista);
        List<Despacho> resultado = despachoService.listarDespachos();
        assertThat(resultado).hasSize(1);
        verify(despachoRepository).findAll();
    }

    @Test
    void testObtenerDespachoPorIdExistente() {
        Despacho obj = new Despacho(1L, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino");
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Despacho> resultado = despachoService.obtenerDespachoPorId(1L);
        assertThat(resultado).isPresent();
        verify(despachoRepository).findById(1L);
    }

    @Test
    void testObtenerDespachoPorIdNoExistente() {
        when(despachoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Despacho> resultado = despachoService.obtenerDespachoPorId(99L);
        assertThat(resultado).isEmpty();
        verify(despachoRepository).findById(99L);
    }

    @Test
    void testActualizarDespacho() {
        Despacho existente = new Despacho(1L, 1L, 1L, "estado", java.time.LocalDate.now(), "direccionDestino");
        Despacho nuevo     = new Despacho(null, 1L, 1L, "alt-estado", java.time.LocalDate.now(), "alt-direccionDestino");
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(despachoRepository.save(any(Despacho.class))).thenAnswer(i -> i.getArgument(0));
        Despacho resultado = despachoService.actualizarDespacho(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(despachoRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarDespacho
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(despachoRepository).deleteById(1L);
    //   2. despachoService.eliminarDespacho(1L);
    //   3. verify(despachoRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarDespacho() {
        // TODO: implementar esta prueba en vivo
    }
}
