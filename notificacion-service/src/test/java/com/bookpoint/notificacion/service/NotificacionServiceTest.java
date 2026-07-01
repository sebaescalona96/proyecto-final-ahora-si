package com.bookpoint.notificacion.service;

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
import com.bookpoint.notificacion.model.Notificacion;
import com.bookpoint.notificacion.repository.NotificacionRepository;

public class NotificacionServiceTest {
    @Mock
    private NotificacionRepository notificacionRepository;
    @InjectMocks
    private NotificacionService notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarNotificacion() {
        Notificacion nuevo = new Notificacion(null, 1L, "mensaje", "tipo", true, java.time.LocalDate.now());
        Notificacion guardado = new Notificacion(1L, 1L, "mensaje", "tipo", true, java.time.LocalDate.now());
        when(notificacionRepository.save(nuevo)).thenReturn(guardado);
        Notificacion resultado = notificacionService.guardarNotificacion(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(notificacionRepository).save(nuevo);
    }

    @Test
    void testListarNotificacions() {
        List<Notificacion> lista = Arrays
                .asList(new Notificacion(1L, 1L, "mensaje", "tipo", true, java.time.LocalDate.now()));
        when(notificacionRepository.findAll()).thenReturn(lista);
        List<Notificacion> resultado = notificacionService.listarNotificacions();
        assertThat(resultado).hasSize(1);
        verify(notificacionRepository).findAll();
    }

    @Test
    void testObtenerNotificacionPorIdExistente() {
        Notificacion obj = new Notificacion(1L, 1L, "mensaje", "tipo", true, java.time.LocalDate.now());
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Notificacion> resultado = notificacionService.obtenerNotificacionPorId(1L);
        assertThat(resultado).isPresent();
        verify(notificacionRepository).findById(1L);
    }

    @Test
    void testObtenerNotificacionPorIdNoExistente() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Notificacion> resultado = notificacionService.obtenerNotificacionPorId(99L);
        assertThat(resultado).isEmpty();
        verify(notificacionRepository).findById(99L);
    }

    @Test
    void testActualizarNotificacion() {
        Notificacion existente = new Notificacion(1L, 1L, "mensaje", "tipo", true, java.time.LocalDate.now());
        Notificacion nuevo = new Notificacion(null, 1L, "alt-mensaje", "alt-tipo", true, java.time.LocalDate.now());
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(notificacionRepository.save(any(Notificacion.class))).thenAnswer(i -> i.getArgument(0));
        Notificacion resultado = notificacionService.actualizarNotificacion(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(notificacionRepository).save(existente);
    }

    @Test
    void testActualizarNotificacionNoExistenteLanzaExcepcion() {
        Long idInexistente = 99L;
        com.bookpoint.notificacion.model.Notificacion datosNuevos = new com.bookpoint.notificacion.model.Notificacion();
        datosNuevos.setId(idInexistente);

        // Simulamos que el repositorio no encuentra el registro
        org.mockito.Mockito.when(notificacionRepository.findById(idInexistente))
                .thenReturn(java.util.Optional.empty());

        // Verificamos que se lance la RuntimeException con el mensaje exacto sin tilde
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            notificacionService.actualizarNotificacion(idInexistente, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe notificacion con id: " + idInexistente);
    }
}
