package com.bookpoint.pedido;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PedidoApplicationTest {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring de Pedidos cargue correctamente
    }

    @Test
    void main() {
        System.setProperty("spring.profiles.active", "test");
        // CORREGIDO: Se cambia PedidoApplication por PedidoServiceApplication
        PedidoServiceApplication.main(new String[] {});
    }
}