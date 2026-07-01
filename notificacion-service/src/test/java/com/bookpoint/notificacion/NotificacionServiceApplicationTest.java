package com.bookpoint.notificacion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class NotificacionServiceApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring de Notificaciones cargue correctamente
    }

    @Test
    void main() {
        System.setProperty("spring.profiles.active", "test");
        NotificacionServiceApplication.main(new String[] {});
    }
}