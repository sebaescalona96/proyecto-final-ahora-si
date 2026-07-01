package com.bookpoint.despacho;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class DespachoServiceApplicationTest {

    @Test
    void testMain() {
        try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
            springApplicationMock.when(() -> SpringApplication.run(DespachoServiceApplication.class, new String[]{}))
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            DespachoServiceApplication.main(new String[]{});

            springApplicationMock.verify(() -> SpringApplication.run(DespachoServiceApplication.class, new String[]{}));
        }
    }
}