package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.TestcontainersConfiguration;
import edu.unimag.sistemavuelo2.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestConfiguration
@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PasajeroRepositoryTest {
    private UUID numeroReserva;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Autowired
    private PasaporteRepository pasaporteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private VueloRepository vueloRepository;

    @BeforeEach
    void setUp() {
        pasajeroRepository.deleteAll();

        Pasaporte pasaporte1 = Pasaporte.builder().pasaporteNid("100").build();
        Pasaporte pasaporte2 = Pasaporte.builder().pasaporteNid("101").build();
        Pasaporte pasaporte3 = Pasaporte.builder().pasaporteNid("102").build();
        Pasaporte pasaporte4 = Pasaporte.builder().pasaporteNid("103").build();

        Pasajero pasajero1= Pasajero.builder().nombre("Juan").apellido("Perez").nid("10000").pasaporte(pasaporte1).build();
        Pasajero pasajero2 = Pasajero.builder().nombre("Maria").apellido("Gomez").nid("10001").pasaporte(pasaporte2).build();
        Pasajero pasajero3 = Pasajero.builder().nombre("Juan").apellido("Garcia").nid("10002").pasaporte(pasaporte3).build();

        List<Aerolinea> aerolineas = new ArrayList<>();
        Aerolinea aerolinea = Aerolinea.builder().nombre("Avianca").build();
        Aerolinea aerolinea2 = Aerolinea.builder().nombre("Avianca2").build();
        aerolineas.add(aerolinea);
        aerolineas.add(aerolinea2);
        Vuelo vuelo = Vuelo.builder().origen("santa marta").destino("bogota").aerolineas(aerolineas).build();

        Reserva reserva = new Reserva();
        reserva.setPasajero(pasajero3);
        reserva.setVuelo(vuelo);
      //  reserva.setCodigoReserva(UUID.randomUUID());
        numeroReserva = reserva.getCodigoReserva();
        reservaRepository.save(reserva);

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
    void findByNombreOrderByNid() {
        List<Pasajero> pasajeross = pasajeroRepository.findByNombreOrderByNid("Juan");
        assertFalse(pasajeross.isEmpty());
        for(Pasajero pasajero: pasajeross){
            assertThat(pasajero.getNombre()).isEqualTo("Juan");
            System.out.println(pasajero.getNombre());
        }
    }

    @Test
    void findByNombreAndPasaporteId() {
        Optional<Pasajero> pasajero = pasajeroRepository.findByNombreAndPasaporteId("Juan", 1L);
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getNombre()).isEqualTo("Juan");
    }

    @Test
    void save() {
        Pasaporte pasaporte = Pasaporte.builder().pasaporteNid("103").build();
        Pasajero pasajero = Pasajero.builder().nombre("Juan").apellido("Perez").nid("10003").pasaporte(pasaporte).build();
        pasajeroRepository.save(pasajero);
        Optional<Pasajero> pasajero1 = pasajeroRepository.findByNid("10003");
        assertThat(pasajero1).isPresent();
        assertThat(pasajero1.get().getNombre()).isEqualTo("Juan");
    }

    @Test
    void delete() {
        Optional<Pasajero> pasajero = pasajeroRepository.findByNid("10000");
        assertThat(pasajero).isPresent();
        pasajeroRepository.delete(pasajero.get());
        Optional<Pasajero> pasajero1 = pasajeroRepository.findByNid("10000");
        assertThat(pasajero1).isEmpty();
    }

    @Test
    void updateNombreAndApellidoByNid() {
        pasajeroRepository.updateNombreAndApellidoByNid("10000", "Juanito", "Pereza");
        Optional<Pasajero> pasajero = pasajeroRepository.findByNid("10000");
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getNombre()).isEqualTo("Juanito");
        assertThat(pasajero.get().getApellido()).isEqualTo("Pereza");
    }

    @Test
    void findPasajerosByCodigoReserva() {
        List<Pasajero> pasajeros = pasajeroRepository.findPasajerosByCodigoReserva(numeroReserva);
        assertFalse(pasajeros.isEmpty());
        for(Pasajero pasajero: pasajeros){
            assertThat(pasajero.getNombre()).isEqualTo("Juan");
            System.out.println(pasajero.getNombre());
        }
    }



    @Test
    void updateApellidoByNombreAndNid() {
        pasajeroRepository.updateApellidoByNombreAndNid("Juan", "10000", "Ramos");
        Optional<Pasajero> pasajero = pasajeroRepository.findByNid("10000");
        assertThat(pasajero).isPresent();
        assertThat(pasajero.get().getApellido()).isEqualTo("Ramos");
    }

    @Test
    void findPasajeroByNidAndCodigoReserva() {
        Pasajero pasajero = pasajeroRepository.findPasajeroByNidAndCodigoReserva("10002", numeroReserva);
        assertThat(pasajero).isNotNull();
        assertThat(pasajero.getNombre()).isEqualTo("Juan");
    }
}