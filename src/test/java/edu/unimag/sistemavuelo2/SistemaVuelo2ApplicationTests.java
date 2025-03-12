package edu.unimag.sistemavuelo2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SistemaVuelo2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
