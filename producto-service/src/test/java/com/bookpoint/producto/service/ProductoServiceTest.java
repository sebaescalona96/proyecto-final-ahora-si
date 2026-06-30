package com.bookpoint.producto.service;
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
import com.bookpoint.producto.model.Producto;
import com.bookpoint.producto.repository.ProductoRepository;

public class ProductoServiceTest {
    @Mock private ProductoRepository productoRepository;
    @InjectMocks private ProductoService productoService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarProducto() {
        Producto nuevo   = new Producto(null, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial");
        Producto guardado = new Producto(1L, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial");
        when(productoRepository.save(nuevo)).thenReturn(guardado);
        Producto resultado = productoService.guardarProducto(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(productoRepository).save(nuevo);
    }

    @Test
    void testListarProductos() {
        List<Producto> lista = Arrays.asList(new Producto(1L, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial"));
        when(productoRepository.findAll()).thenReturn(lista);
        List<Producto> resultado = productoService.listarProductos();
        assertThat(resultado).hasSize(1);
        verify(productoRepository).findAll();
    }

    @Test
    void testObtenerProductoPorIdExistente() {
        Producto obj = new Producto(1L, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Producto> resultado = productoService.obtenerProductoPorId(1L);
        assertThat(resultado).isPresent();
        verify(productoRepository).findById(1L);
    }

    @Test
    void testObtenerProductoPorIdNoExistente() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Producto> resultado = productoService.obtenerProductoPorId(99L);
        assertThat(resultado).isEmpty();
        verify(productoRepository).findById(99L);
    }

    @Test
    void testActualizarProducto() {
        Producto existente = new Producto(1L, "nombre", "descripcion", 99.9, "categoria", "autor", "editorial");
        Producto nuevo     = new Producto(null, "alt-nombre", "alt-descripcion", 199.9, "alt-categoria", "alt-autor", "alt-editorial");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));
        Producto resultado = productoService.actualizarProducto(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(productoRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarProducto
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(productoRepository).deleteById(1L);
    //   2. productoService.eliminarProducto(1L);
    //   3. verify(productoRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarProducto() {
        // TODO: implementar esta prueba en vivo
    }
}
