package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Aerolinea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AerolineaRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("school")
            .withUsername("mateo")
            .withPassword("1234");

    static {
        postgreSQLContainer.start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }

    @Autowired
    private AerolineaRepository aerolineaRepository;

    @BeforeEach
    void setUp() {
        aerolineaRepository.deleteAll();
        aerolineaRepository.save(new Aerolinea(null, "AeroMexico", null));
        aerolineaRepository.save(new Aerolinea(null, "LATAM", null));
        aerolineaRepository.save(new Aerolinea(null, "Avianca", null));
    }

    @Test
    void testFindByNombre() {
        List<Aerolinea> resultado = aerolineaRepository.findByNombre("AeroMexico");
        assertEquals(1, resultado.size());
        assertEquals("AeroMexico", resultado.get(0).getNombre());
    }

    @Test
    void testFindByNombreContaining() {
        List<Aerolinea> resultado = aerolineaRepository.findByNombreContaining("Avi");
        assertFalse(resultado.isEmpty());
        assertEquals("Avianca", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorNombreQueTermineCon() {
        List<Aerolinea> resultado = aerolineaRepository.buscarPorNombreQueTermineCon("TAM");
        assertFalse(resultado.isEmpty());
        assertEquals("LATAM", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorNombreQueEmpieceCon() {
        List<Aerolinea> resultado = aerolineaRepository.buscarPorNombreQueEmpieceCon("Aer");
        assertFalse(resultado.isEmpty());
        assertEquals("AeroMexico", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorNombreConLongitudMinima() {
        List<Aerolinea> resultado = aerolineaRepository.buscarPorNombreConLongitudMinima(6);
        assertEquals(3, resultado.size());
    }
}
