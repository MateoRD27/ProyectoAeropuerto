package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.TestcontainersConfiguration;
import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.entities.Pasaporte;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestConfiguration
@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PasajeroRepositoryTest {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Autowired
    private PasaporteRepository pasaporteRepository;

    @BeforeEach
    void setUp() {
        pasajeroRepository.deleteAll();

        Pasaporte pasaporte1 = Pasaporte.builder().pasaporteNid("100").build();
        Pasaporte pasaporte2 = Pasaporte.builder().pasaporteNid("101").build();
        Pasaporte pasaporte3 = Pasaporte.builder().pasaporteNid("102").build();

        Pasajero pasajero1= Pasajero.builder().nombre("Juan").apellido("Perez").nid("10000").pasaporte(pasaporte1).build();
        Pasajero pasajero2 = Pasajero.builder().nombre("Maria").apellido("Gomez").nid("10001").pasaporte(pasaporte2).build();
        Pasajero pasajero3 = Pasajero.builder().nombre("Juan").apellido("Garcia").nid("10002").pasaporte(pasaporte3).build();
        pasajeroRepository.save(pasajero1);
        pasajeroRepository.save(pasajero2);
    }


    @AfterEach
    void tearDown() {

    }


    @Test
    void findByNid() {
        Optional<Pasajero> pasajero = pasajeroRepository.findByNid("10000");
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getNombre()).isEqualTo("Juan");
    }
    //este metodo busca todos los que tenga el mismo nombre
    @Test
    void findByNombre() {
        Optional<Pasajero> pasajeross = pasajeroRepository.findByNombre("Juan");
        assertThat(pasajeross).isPresent();

    }

    @Test
    void findByNombreAndPasaporteId() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void updatePasajeroUid() {
    }

    @Test
    void findPasajerosByCodigoReserva() {
    }

    @Test
    void deleteReservaByNidAndCodigoReserva() {
    }

    @Test
    void updateApellidoPasajeroByNombreAndUid() {
    }

    @Test
    void findPasajeroByNidAndCodigoReserva() {
    }
}