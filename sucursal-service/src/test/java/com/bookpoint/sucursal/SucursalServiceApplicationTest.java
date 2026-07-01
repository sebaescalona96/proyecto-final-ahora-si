package com.bookpoint.sucursal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SucursalServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica la carga del contexto de Spring
    }

    @Test
    void main() {
        // Forzamos el perfil de pruebas antes de lanzar el main
        System.setProperty("spring.profiles.active", "test");
        SucursalServiceApplication.main(new String[] {});
    }
}