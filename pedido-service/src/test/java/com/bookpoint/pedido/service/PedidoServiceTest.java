package com.bookpoint.pedido.service;

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
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.repository.PedidoRepository;

public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarPedido() {
        Pedido nuevo = new Pedido(null, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega");
        Pedido guardado = new Pedido(1L, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega");
        when(pedidoRepository.save(nuevo)).thenReturn(guardado);
        Pedido resultado = pedidoService.guardarPedido(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(pedidoRepository).save(nuevo);
    }

    @Test
    void testListarPedidos() {
        List<Pedido> lista = Arrays
                .asList(new Pedido(1L, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega"));
        when(pedidoRepository.findAll()).thenReturn(lista);
        List<Pedido> resultado = pedidoService.listarPedidos();
        assertThat(resultado).hasSize(1);
        verify(pedidoRepository).findAll();
    }

    @Test
    void testObtenerPedidoPorIdExistente() {
        Pedido obj = new Pedido(1L, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega");
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Pedido> resultado = pedidoService.obtenerPedidoPorId(1L);
        assertThat(resultado).isPresent();
        verify(pedidoRepository).findById(1L);
    }

    @Test
    void testObtenerPedidoPorIdNoExistente() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Pedido> resultado = pedidoService.obtenerPedidoPorId(99L);
        assertThat(resultado).isEmpty();
        verify(pedidoRepository).findById(99L);
    }

    @Test
    void testActualizarPedido() {
        Pedido existente = new Pedido(1L, 1L, 1L, 5, "estado", java.time.LocalDate.now(), "direccionEntrega");
        Pedido nuevo = new Pedido(null, 1L, 1L, 10, "alt-estado", java.time.LocalDate.now(), "alt-direccionEntrega");
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArgument(0));
        Pedido resultado = pedidoService.actualizarPedido(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(pedidoRepository).save(existente);
    }

    @Test
    void testActualizarPedidoNoExistenteLanzaExcepcion() {
        Long idInexistente = 99L;
        com.bookpoint.pedido.model.Pedido datosNuevos = new com.bookpoint.pedido.model.Pedido();
        datosNuevos.setId(idInexistente);

        // Simulamos que el repositorio devuelve un Optional vacío
        org.mockito.Mockito.when(pedidoRepository.findById(idInexistente))
                .thenReturn(java.util.Optional.empty());

        // Verificamos que se levante la RuntimeException exacta sin tilde
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
            pedidoService.actualizarPedido(idInexistente, datosNuevos);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe pedido con id: " + idInexistente);
    }
}
