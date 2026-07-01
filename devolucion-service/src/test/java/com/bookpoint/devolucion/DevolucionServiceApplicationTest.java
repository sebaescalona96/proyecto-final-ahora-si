package com.bookpoint.devolucion;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class DevolucionServiceApplicationTest {

    @Test
    void testMain() {
        
        try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
            
            // Simulamos que al arrancar devuelve un contexto genérico vacío
            springApplicationMock.when(() -> SpringApplication.run(DevolucionServiceApplication.class, new String[]{}))
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            
            DevolucionServiceApplication.main(new String[]{});

            // Verificamos que efectivamente se haya hecho el llamado interno
            springApplicationMock.verify(() -> SpringApplication.run(DevolucionServiceApplication.class, new String[]{}));
        }
    }
}