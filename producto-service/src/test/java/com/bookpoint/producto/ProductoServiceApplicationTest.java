package com.bookpoint.producto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductoServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring cargue correctamente
    }

    @Test
    void main() {
        System.setProperty("spring.profiles.active", "test");
        ProductoServiceApplication.main(new String[] {});
    }
}