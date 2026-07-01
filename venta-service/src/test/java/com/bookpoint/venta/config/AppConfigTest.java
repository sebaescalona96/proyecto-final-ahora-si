package com.bookpoint.venta.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class AppConfigTest {

    @Test
    void testRestTemplateBean() {
        // 1. Instanciamos la clase de configuración manualmente
        AppConfig appConfig = new AppConfig();
        
        // 2. Ejecutamos el método que crea el Bean
        RestTemplate restTemplate = appConfig.restTemplate();
        
        // 3. Verificamos que el objeto no sea nulo
        assertNotNull(restTemplate);
    }
}