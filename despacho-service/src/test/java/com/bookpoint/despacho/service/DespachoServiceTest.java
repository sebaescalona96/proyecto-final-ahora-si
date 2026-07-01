package com.bookpoint.despacho.service;

import com.bookpoint.despacho.model.Despacho;
import com.bookpoint.despacho.repository.DespachoRepository;
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

class DespachoServiceTest {

    @Mock
    private DespachoRepository despachoRepository;

    @InjectMocks
    private DespachoService despachoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarDespacho() {
        Despacho nuevo = new Despacho();
        when(despachoRepository.save(nuevo)).thenReturn(nuevo);

        Despacho resultado = despachoService.guardarDespacho(nuevo);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testListarDespachos() {
        List<Despacho> lista = Arrays.asList(new Despacho());
        when(despachoRepository.findAll()).thenReturn(lista);

        List<Despacho> resultado = despachoService.listarDespachos();
        assertThat(resultado).hasSize(1);
    }

    @Test
    void testObtenerDespachoPorIdExistente() {
        Despacho desp = new Despacho();
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(desp));

        Optional<Despacho> resultado = despachoService.obtenerDespachoPorId(1L);
        assertThat(resultado).isPresent();
    }

    @Test
    void testActualizarDespachoExitoso() {
        Despacho existente = new Despacho();
        Despacho nuevosDatos = new Despacho();

        when(despachoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(despachoRepository.save(any(Despacho.class))).thenAnswer(i -> i.getArgument(0));

        Despacho resultado = despachoService.actualizarDespacho(1L, nuevosDatos);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testActualizarDespachoNoExistenteLanzaExcepcion() {
        when(despachoRepository.findById(99L)).thenReturn(Optional.empty());
        Despacho nuevosDatos = new Despacho();

        assertThatThrownBy(() -> {
            despachoService.actualizarDespacho(99L, nuevosDatos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe despacho con id: 99");
    }

    @Test
    void testEliminarDespacho() {
        Mockito.doNothing().when(despachoRepository).deleteById(1L);
        despachoService.eliminarDespacho(1L);
        verify(despachoRepository).deleteById(1L);
    }
}