package com.bookpoint.resena.service;

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
import com.bookpoint.resena.model.Resena;
import com.bookpoint.resena.repository.ResenaRepository;

public class ResenaServiceTest {
    @Mock
    private ResenaRepository resenaRepository;
    @InjectMocks
    private ResenaService resenaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarResena() {
        Resena nuevo = new Resena(null, 1L, 1L, "comentario", 5, java.time.LocalDate.now());
        Resena guardado = new Resena(1L, 1L, 1L, "comentario", 5, java.time.LocalDate.now());
        when(resenaRepository.save(nuevo)).thenReturn(guardado);
        Resena resultado = resenaService.guardarResena(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(resenaRepository).save(nuevo);
    }

    @Test
    void testListarResenas() {
        List<Resena> lista = Arrays.asList(new Resena(1L, 1L, 1L, "comentario", 5, java.time.LocalDate.now()));
        when(resenaRepository.findAll()).thenReturn(lista);
        List<Resena> resultado = resenaService.listarResenas();
        assertThat(resultado).hasSize(1);
        verify(resenaRepository).findAll();
    }

    @Test
    void testObtenerResenaPorIdExistente() {
        Resena obj = new Resena(1L, 1L, 1L, "comentario", 5, java.time.LocalDate.now());
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Resena> resultado = resenaService.obtenerResenaPorId(1L);
        assertThat(resultado).isPresent();
        verify(resenaRepository).findById(1L);
    }

    @Test
    void testObtenerResenaPorIdNoExistente() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Resena> resultado = resenaService.obtenerResenaPorId(99L);
        assertThat(resultado).isEmpty();
        verify(resenaRepository).findById(99L);
    }

    @Test
    void testActualizarResena() {
        Resena existente = new Resena(1L, 1L, 1L, "comentario", 5, java.time.LocalDate.now());
        Resena nuevo = new Resena(null, 1L, 1L, "alt-comentario", 10, java.time.LocalDate.now());
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(resenaRepository.save(any(Resena.class))).thenAnswer(i -> i.getArgument(0));
        Resena resultado = resenaService.actualizarResena(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(resenaRepository).save(existente);
    }

    @Test
    void testActualizarResenaNoExistenteLanzaExcepcion() {
        Long idInexistente = 99L;
        com.bookpoint.resena.model.Resena datosNuevos = new com.bookpoint.resena.model.Resena();
        datosNuevos.setId(idInexistente);

        org.mockito.Mockito.when(resenaRepository.findById(idInexistente))
                .thenReturn(java.util.Optional.empty());

        // Buscamos "resena" con 'n' para que calce exactamente con tu ResenaService
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            resenaService.actualizarResena(idInexistente, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe resena con id: " + idInexistente);
    }
}
