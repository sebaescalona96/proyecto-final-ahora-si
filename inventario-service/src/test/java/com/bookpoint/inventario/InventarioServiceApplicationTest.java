package com.bookpoint.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InventarioServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica la carga del contexto
    }

    @Test
    void main() {
        System.setProperty("spring.profiles.active", "test");
        InventarioServiceApplication.main(new String[] {});
    }
}