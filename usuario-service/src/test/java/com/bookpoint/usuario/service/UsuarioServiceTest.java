package com.bookpoint.usuario.service;
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
import com.bookpoint.usuario.model.Usuario;
import com.bookpoint.usuario.repository.UsuarioRepository;

public class UsuarioServiceTest {
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private UsuarioService usuarioService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGuardarUsuario() {
        Usuario nuevo   = new Usuario(null, "nombre", "email", "password", "rol");
        Usuario guardado = new Usuario(1L, "nombre", "email", "password", "rol");
        when(usuarioRepository.save(nuevo)).thenReturn(guardado);
        Usuario resultado = usuarioService.guardarUsuario(nuevo);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(usuarioRepository).save(nuevo);
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> lista = Arrays.asList(new Usuario(1L, "nombre", "email", "password", "rol"));
        when(usuarioRepository.findAll()).thenReturn(lista);
        List<Usuario> resultado = usuarioService.listarUsuarios();
        assertThat(resultado).hasSize(1);
        verify(usuarioRepository).findAll();
    }

    @Test
    void testObtenerUsuarioPorIdExistente() {
        Usuario obj = new Usuario(1L, "nombre", "email", "password", "rol");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(obj));
        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(1L);
        assertThat(resultado).isPresent();
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testObtenerUsuarioPorIdNoExistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(99L);
        assertThat(resultado).isEmpty();
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void testActualizarUsuario() {
        Usuario existente = new Usuario(1L, "nombre", "email", "password", "rol");
        Usuario nuevo     = new Usuario(null, "alt-nombre", "alt-email", "alt-password", "alt-rol");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));
        Usuario resultado = usuarioService.actualizarUsuario(1L, nuevo);
        assertThat(resultado).isNotNull();
        verify(usuarioRepository).save(existente);
    }

    // ══════════════════════════════════════════════════════════════════
    // TODO: IMPLEMENTAR EN VIVO - testEliminarUsuario
    // Dificultad: ⭐ (FACIL) — Ver PRUEBAS_PARA_MEMORIZAR.md
    // Pasos:
    //   1. doNothing().when(usuarioRepository).deleteById(1L);
    //   2. usuarioService.eliminarUsuario(1L);
    //   3. verify(usuarioRepository).deleteById(1L);
    // ══════════════════════════════════════════════════════════════════
    @Test
    void testEliminarUsuario() {
        // TODO: implementar esta prueba en vivo
    }
}
