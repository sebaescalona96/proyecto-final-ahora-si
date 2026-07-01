package com.bookpoint.descuento;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class DescuentoServiceApplicationTest {

    @Test
    void testMain() {
        try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
            springApplicationMock.when(() -> SpringApplication.run(DescuentoServiceApplication.class, new String[]{}))
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            DescuentoServiceApplication.main(new String[]{});

            springApplicationMock.verify(() -> SpringApplication.run(DescuentoServiceApplication.class, new String[]{}));
        }
    }
}