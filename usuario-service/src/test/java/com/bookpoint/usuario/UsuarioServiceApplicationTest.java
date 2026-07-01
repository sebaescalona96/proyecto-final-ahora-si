package com.bookpoint.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring Boot se configure de forma correcta
    }

    @Test
    void main() {
        // Forzamos a la JVM a usar el perfil de pruebas 'test' para que lea application-test.properties
        System.setProperty("spring.profiles.active", "test");
        
        UsuarioServiceApplication.main(new String[] {});
    }
}