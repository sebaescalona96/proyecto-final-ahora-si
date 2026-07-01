package com.bookpoint.transferencia.service;

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
import com.bookpoint.transferencia.model.Transferencia;
import com.bookpoint.transferencia.repository.TransferenciaRepository;

public class TransferenciaServiceTest {
    @Mock
    private TransferenciaRepository transferenciaRepository;
    @InjectMocks
    private TransferenciaService transferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarTransferencia() {
        Transferencia nuevo = new Transferencia(null, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Transferencia guardado = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        when(transferenciaRepository.save(nuevo)).thenReturn(guardado);
        Transferencia resultado = transferenciaService.guardarTransferencia(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(transferenciaRepository).save(nuevo);
    }

    @Test
    void testListarTransferencias() {
        List<Transferencia> lista = Arrays
                .asList(new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now()));
        when(transferenciaRepository.findAll()).thenReturn(lista);
        List<Transferencia> resultado = transferenciaService.listarTransferencias();
        assertThat(resultado).hasSize(1);
        verify(transferenciaRepository).findAll();
    }

    @Test
    void testObtenerTransferenciaPorIdExistente() {
        Transferencia obj = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Transferencia> resultado = transferenciaService.obtenerTransferenciaPorId(1L);
        assertThat(resultado).isPresent();
        verify(transferenciaRepository).findById(1L);
    }

    @Test
    void testObtenerTransferenciaPorIdNoExistente() {
        when(transferenciaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Transferencia> resultado = transferenciaService.obtenerTransferenciaPorId(99L);
        assertThat(resultado).isEmpty();
        verify(transferenciaRepository).findById(99L);
    }

    @Test
    void testActualizarTransferencia() {
        Transferencia existente = new Transferencia(1L, 1L, 1L, 1L, 5, "estado", java.time.LocalDate.now());
        Transferencia nuevo = new Transferencia(null, 1L, 1L, 1L, 10, "alt-estado", java.time.LocalDate.now());
        when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(i -> i.getArgument(0));
        Transferencia resultado = transferenciaService.actualizarTransferencia(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(transferenciaRepository).save(existente);
    }

    @Test
    void testActualizarTransferenciaNoExistenteLanzaExcepcion() {
        Long idInexistente = 99L;
        com.bookpoint.transferencia.model.Transferencia datosNuevos = new com.bookpoint.transferencia.model.Transferencia();

        // Simulamos que el repositorio no encuentra nada y retorna un Optional vacío
        org.mockito.Mockito.when(transferenciaRepository.findById(idInexistente))
                .thenReturn(java.util.Optional.empty());

        // Verificamos que el servicio arroje la RuntimeException esperada
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            transferenciaService.actualizarTransferencia(idInexistente, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe transferencia con id: " + idInexistente);
    }
}
