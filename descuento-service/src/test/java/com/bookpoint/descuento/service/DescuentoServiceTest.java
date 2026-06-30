package com.bookpoint.descuento.service;
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
import com.bookpoint.descuento.model.Descuento;
import com.bookpoint.descuento.repository.DescuentoRepository;

public class DescuentoServiceTest {
    @Mock private DescuentoRepository descuentoRepository;
    @InjectMocks private DescuentoService descuentoService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarDescuento() {
        Descuento nuevo   = new Descuento(null, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now());
        Descuento guardado = new Descuento(1L, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now());
        when(descuentoRepository.save(nuevo)).thenReturn(guardado);
        Descuento resultado = descuentoService.guardarDescuento(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(descuentoRepository).save(nuevo);
    }

    @Test
    void testListarDescuentos() {
        List<Descuento> lista = Arrays.asList(new Descuento(1L, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now()));
        when(descuentoRepository.findAll()).thenReturn(lista);
        List<Descuento> resultado = descuentoService.listarDescuentos();
        assertThat(resultado).hasSize(1);
        verify(descuentoRepository).findAll();
    }

    @Test
    void testObtenerDescuentoPorIdExistente() {
        Descuento obj = new Descuento(1L, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now());
        when(descuentoRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Descuento> resultado = descuentoService.obtenerDescuentoPorId(1L);
        assertThat(resultado).isPresent();
        verify(descuentoRepository).findById(1L);
    }

    @Test
    void testObtenerDescuentoPorIdNoExistente() {
        when(descuentoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Descuento> resultado = descuentoService.obtenerDescuentoPorId(99L);
        assertThat(resultado).isEmpty();
        verify(descuentoRepository).findById(99L);
    }

    @Test
    void testActualizarDescuento() {
        Descuento existente = new Descuento(1L, "codigo", "descripcion", 99.9, "tipo", true, java.time.LocalDate.now());
        Descuento nuevo     = new Descuento(null, "alt-codigo", "alt-descripcion", 199.9, "alt-tipo", true, java.time.LocalDate.now());
        when(descuentoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(descuentoRepository.save(any(Descuento.class))).thenAnswer(i -> i.getArgument(0));
        Descuento resultado = descuentoService.actualizarDescuento(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(descuentoRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarDescuento
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(descuentoRepository).deleteById(1L);
    //   2. descuentoService.eliminarDescuento(1L);
    //   3. verify(descuentoRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarDescuento() {
        // TODO: implementar esta prueba en vivo
    }
}
