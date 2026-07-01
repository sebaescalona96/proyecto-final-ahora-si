package com.bookpoint.resena;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ResenaServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring cargue correctamente
    }

    @Test
    void main() {
        // Forzamos el perfil de pruebas antes de lanzar el main
        System.setProperty("spring.profiles.active", "test");
        ResenaServiceApplication.main(new String[] {});
    }
}