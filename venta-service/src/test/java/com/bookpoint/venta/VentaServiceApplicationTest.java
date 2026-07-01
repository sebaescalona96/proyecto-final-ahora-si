package com.bookpoint.venta;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // Le avisa a Spring que use la configuración de pruebas
class VentaServiceApplicationTest {

    @Test
    void main() {
        // Ejecuta el main pasándole explícitamente el perfil de test para que no busque la BD real
        VentaServiceApplication.main(new String[] {"--spring.profiles.active=test"});
    }
}