package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.entities.Reserva;
import edu.unimag.sistemavuelo2.entities.Vuelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservaRepositoryTest {

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
    private ReservaRepository reservaRepository;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Autowired
    private VueloRepository vueloRepository;

    private Pasajero pasajero;
    private Vuelo vuelo;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reservaRepository.deleteAll();
        pasajeroRepository.deleteAll();
        vueloRepository.deleteAll();

        pasajero = new Pasajero(null, "Juan Pérez", "123456789", null);
        vuelo = new Vuelo(null, "AV123", "Bogotá", "Madrid", null, null);

        pasajero = pasajeroRepository.save(pasajero);
        vuelo = vueloRepository.save(vuelo);

        reserva = new Reserva(null, UUID.randomUUID(), pasajero, vuelo);
        reserva = reservaRepository.save(reserva);
    }

    @Test
    void testBuscarPorCodigo() {
        Optional<Reserva> resultado = reservaRepository.buscarPorCodigo(reserva.getCodigoReserva());
        assertTrue(resultado.isPresent());
        assertEquals(reserva.getCodigoReserva(), resultado.get().getCodigoReserva());
    }

    @Test
    void testObtenerReservasPorPasajero() {
        List<Reserva> resultado = reservaRepository.obtenerReservasPorPasajero(pasajero.getId());
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testContarReservasPorVuelo() {
        long count = reservaRepository.contarReservasPorVuelo(vuelo.getId());
        assertEquals(1, count);
    }

    @Test
    void testObtenerReservasPasajeroOrdenadas() {
        List<Reserva> resultado = reservaRepository.obtenerReservasPasajeroOrdenadas(pasajero.getId());
        assertFalse(resultado.isEmpty());
        assertEquals(reserva.getId(), resultado.get(0).getId());
    }

    @Test
    void testExisteReservaPorPasajeroYVuelo() {
        boolean existe = reservaRepository.existeReservaPorPasajeroYVuelo(pasajero.getId(), vuelo.getId());
        assertTrue(existe);
    }

    @Test
    void testFindByCodigoReserva() {
        Optional<Reserva> resultado = reservaRepository.findByCodigoReserva(reserva.getCodigoReserva());
        assertTrue(resultado.isPresent());
        assertEquals(reserva.getCodigoReserva(), resultado.get().getCodigoReserva());
    }

    @Test
    void testFindByPasajero() {
        List<Reserva> resultado = reservaRepository.findByPasajero(pasajero);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testCountByVuelo() {
        long count = reservaRepository.countByVuelo(vuelo);
        assertEquals(1, count);
    }

    @Test
    void testFindByVuelo() {
        List<Reserva> resultado = reservaRepository.findByVuelo(vuelo);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testFindByPasajeroAndVuelo() {
        List<Reserva> resultado = reservaRepository.findByPasajeroAndVuelo(pasajero, vuelo);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }
}
