package com.bookpoint.transferencia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TransferenciaServiceApplicationTest {

    @Test
    void contextLoads() {
        
    }

    @Test
    void main() {
        
        System.setProperty("spring.profiles.active", "test");
        TransferenciaServiceApplication.main(new String[] {});
    }
}