package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Pasaporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PasaporteRepositoryTest {

    @Autowired
    private PasaporteRepository pasaporteRepository;

    private Pasaporte pasaporte1, pasaporte2;

    @BeforeEach
    void setUp() {
        pasaporte1 = new Pasaporte();
        pasaporte2 = new Pasaporte();

        pasaporteRepository.saveAll(List.of(pasaporte1, pasaporte2));
    }

    @Test
    void testFindById() {
        Optional<Pasaporte> found = pasaporteRepository.findById(pasaporte1.getId());
        assertTrue(found.isPresent());
        assertEquals(pasaporte1.getId(), found.get().getId());
    }

    @Test
    void testFindByIdGreaterThan() {
        List<Pasaporte> result = pasaporteRepository.findByIdGreaterThan(0L);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByIdLessThan() {
        List<Pasaporte> result = pasaporteRepository.findByIdLessThan(Long.MAX_VALUE);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByIdBetween() {
        List<Pasaporte> result = pasaporteRepository.findByIdBetween(pasaporte1.getId(), pasaporte2.getId());
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByIdOrderByIdDesc() {
        List<Pasaporte> result = pasaporteRepository.obtenerTodosOrdenadosPorIdDesc();
        assertFalse(result.isEmpty());
    }

    @Test
    void testObtenerPasaportesSinPasajero() {
        List<Pasaporte> result = pasaporteRepository.obtenerPasaportesSinPasajero();
        assertEquals(2, result.size());
    }

    @Test
    void testListarOrdenadosPorIdAsc() {
        List<Pasaporte> result = pasaporteRepository.listarOrdenadosPorIdAsc();
        assertFalse(result.isEmpty());
    }

    @Test
    void testBuscarPorIdMenorQue() {
        List<Pasaporte> result = pasaporteRepository.buscarPorIdMenorQue(Long.MAX_VALUE);
        assertFalse(result.isEmpty());
    }

    @Test
    void testContarPasaportes() {
        long count = pasaporteRepository.contarPasaportes();
        assertEquals(2, count);
    }
}
