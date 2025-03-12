package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.TestcontainersConfiguration;
import edu.unimag.sistemavuelo2.entities.Pasajero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class PasajeroRepositoryTest {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @BeforeEach
    void setUp() {
        // Crear datos de prueba
        Pasajero p1 = new Pasajero();
        p1.setNombre("Carlos López");
        p1.setNid("123456");

        Pasajero p2 = new Pasajero();
        p2.setNombre("Ana Torres");
        p2.setNid("654321");
        p2.setPasaporte(null); // Sin pasaporte

        Pasajero p3 = new Pasajero();
        p3.setNombre("Luis Fernández");
        p3.setNid("987654");

        pasajeroRepository.saveAll(List.of(p1, p2, p3));
    }

    @Test
    void testBuscarPorNid() {
        Optional<Pasajero> pasajero = pasajeroRepository.buscarPorNid("123456");
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getNombre()).isEqualTo("Carlos López");
    }

    @Test
    void testBuscarPorLetraInicial() {
        List<Pasajero> pasajeros = pasajeroRepository.buscarPorLetraInicial("A");
        assertThat(pasajeros).hasSize(1);
        assertThat(pasajeros.get(0).getNombre()).isEqualTo("Ana Torres");
    }

    @Test
    void testContarPasajerosConPasaporte() {
        long count = pasajeroRepository.contarPasajerosConPasaporte();
        assertThat(count).isEqualTo(2); // Solo Carlos y Luis tienen pasaporte
    }

    @Test
    void testObtenerIdYNombreOrdenados() {
        List<Object[]> resultados = pasajeroRepository.obtenerIdYNombreOrdenados();
        assertThat(resultados).hasSize(3);
        assertThat(resultados.get(0)[1]).isEqualTo("Ana Torres"); // Ordenado por nombre
    }

    @Test
    void testFindByNombre() {
        List<Pasajero> pasajeros = pasajeroRepository.findByNombre("Carlos López");
        assertThat(pasajeros).hasSize(1);
        assertThat(pasajeros.get(0).getNid()).isEqualTo("123456");
    }

    @Test
    void testFindByNid() {
        Optional<Pasajero> pasajero = pasajeroRepository.findByNid("654321");
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getNombre()).isEqualTo("Ana Torres");
    }

    @Test
    void testFindByNombreContainingIgnoreCase() {
        List<Pasajero> pasajeros = pasajeroRepository.findByNombreContainingIgnoreCase("fernández");
        assertThat(pasajeros).hasSize(1);
        assertThat(pasajeros.get(0).getNombre()).isEqualTo("Luis Fernández");
    }

    @Test
    void testFindByPasaporteIsNull() {
        List<Pasajero> pasajeros = pasajeroRepository.findByPasaporteIsNull();
        assertThat(pasajeros).hasSize(1);
        assertThat(pasajeros.get(0).getNombre()).isEqualTo("Ana Torres");
    }
}
