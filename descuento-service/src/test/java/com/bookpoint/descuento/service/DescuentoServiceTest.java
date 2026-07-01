package com.bookpoint.descuento.service;

import com.bookpoint.descuento.model.Descuento;
import com.bookpoint.descuento.repository.DescuentoRepository;
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

class DescuentoServiceTest {

    @Mock
    private DescuentoRepository descuentoRepository;

    @InjectMocks
    private DescuentoService descuentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarDescuento() {
        Descuento nuevo = new Descuento();
        when(descuentoRepository.save(nuevo)).thenReturn(nuevo);

        Descuento resultado = descuentoService.guardarDescuento(nuevo);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testListarDescuentos() {
        List<Descuento> lista = Arrays.asList(new Descuento());
        when(descuentoRepository.findAll()).thenReturn(lista);

        List<Descuento> resultado = descuentoService.listarDescuentos();
        assertThat(resultado).hasSize(1);
    }

    @Test
    void testObtenerDescuentoPorIdExistente() {
        Descuento desc = new Descuento();
        when(descuentoRepository.findById(1L)).thenReturn(Optional.of(desc));

        Optional<Descuento> resultado = descuentoService.obtenerDescuentoPorId(1L);
        assertThat(resultado).isPresent();
    }

    @Test
    void testActualizarDescuentoExitoso() {
        Descuento existente = new Descuento();
        Descuento nuevosDatos = new Descuento();

        when(descuentoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(descuentoRepository.save(any(Descuento.class))).thenAnswer(i -> i.getArgument(0));

        Descuento resultado = descuentoService.actualizarDescuento(1L, nuevosDatos);
        assertThat(resultado).isNotNull();
    }

    @Test
    void testActualizarDescuentoNoExistenteLanzaExcepcion() {
        when(descuentoRepository.findById(99L)).thenReturn(Optional.empty());
        Descuento nuevosDatos = new Descuento();

        assertThatThrownBy(() -> {
            descuentoService.actualizarDescuento(99L, nuevosDatos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe descuento con id: 99");
    }

    @Test
    void testEliminarDescuento() {
        Mockito.doNothing().when(descuentoRepository).deleteById(1L);
        descuentoService.eliminarDescuento(1L);
        verify(descuentoRepository).deleteById(1L);
    }
}