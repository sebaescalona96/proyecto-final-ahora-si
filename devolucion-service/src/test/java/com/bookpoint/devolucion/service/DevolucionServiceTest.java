package com.bookpoint.devolucion.service;

import com.bookpoint.devolucion.model.Devolucion;
import com.bookpoint.devolucion.repository.DevolucionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class DevolucionServiceTest {

    @Mock
    private DevolucionRepository devolucionRepository;

    @InjectMocks
    private DevolucionService devolucionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarDevolucion() {
        Devolucion nuevo = new Devolucion();
        when(devolucionRepository.save(nuevo)).thenReturn(nuevo);

        Devolucion resultado = devolucionService.guardarDevolucion(nuevo);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testListarDevoluciones() {
        List<Devolucion> lista = Arrays.asList(new Devolucion());
        when(devolucionRepository.findAll()).thenReturn(lista);

        List<Devolucion> resultado = devolucionService.listarDevoluciones();
        assertThat(resultado).hasSize(1);
    }

    @Test
    void testObtenerDevolucionPorIdExistente() {
        Devolucion dev = new Devolucion();
        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(dev));

        Optional<Devolucion> resultado = devolucionService.obtenerDevolucionPorId(1L);
        assertThat(resultado).isPresent();
    }

    @Test
    void testActualizarDevolucionExitoso() {
        Devolucion existente = new Devolucion();
        Devolucion nuevosDatos = new Devolucion();

        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(devolucionRepository.save(any(Devolucion.class))).thenAnswer(i -> i.getArgument(0));

        Devolucion resultado = devolucionService.actualizarDevolucion(1L, nuevosDatos);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testActualizarDevolucionNoExistenteLanzaExcepcion() {
        when(devolucionRepository.findById(99L)).thenReturn(Optional.empty());
        Devolucion nuevosDatos = new Devolucion();

        assertThatThrownBy(() -> {
            devolucionService.actualizarDevolucion(99L, nuevosDatos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe devolucion con id: 99");
    }

    @Test
    void testEliminarDevolucion() {
        Mockito.doNothing().when(devolucionRepository).deleteById(1L);
        devolucionService.eliminarDevolucion(1L);
        verify(devolucionRepository).deleteById(1L);
    }
}