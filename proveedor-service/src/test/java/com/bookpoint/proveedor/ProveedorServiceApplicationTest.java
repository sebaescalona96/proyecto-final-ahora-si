package com.bookpoint.proveedor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProveedorServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring cargue correctamente
    }

    @Test
    void main() {
        // Forzamos el perfil de pruebas antes de lanzar el main
        System.setProperty("spring.profiles.active", "test");
        ProveedorServiceApplication.main(new String[] {});
    }
}