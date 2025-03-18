package edu.unimag.sistemavuelo2.services;

import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.repository.PasajeroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.UUID;

class PasajeroServiceTest {
    @Mock
    private PasajeroRepository pasajeroRepository;

    @InjectMocks
    private PasajeroService pasajeroService;

    public PasajeroServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findPasajerosByNid() {
        // Dado
        Pasajero pasajero1 = new Pasajero(1L, "Juan", "Perez", "123111", null, null);
        Pasajero pasajero2 = new Pasajero(2L, "Maria", "Lopez", "1223334", null, null);
        List<Pasajero> pasajeros = Arrays.asList(pasajero1, pasajero2);

        // Cuando
        when(pasajeroRepository.findByNid("123111")).thenReturn(pasajeros);

        // Entonces
        List<Pasajero> result = pasajeroService.findPasajerosByNid("123111");
        assertEquals("Juan", result.get(0).getNombre());
        verify(pasajeroRepository, times(1)).findByNid("123111");
    }

    @Test
    void findPasajerosByNombre() {
        // Dado
        Pasajero pasajero1 = new Pasajero(1L, "Juan", "Perez", "123", null, null);
        Pasajero pasajero2 = new Pasajero(2L, "Juan", "Lopez", "124", null, null);
        List<Pasajero> pasajeros = Arrays.asList(pasajero1, pasajero2);

        // Cuando
        when(pasajeroRepository.findByNombre("Juan")).thenReturn(pasajeros);

        // Entonces
        List<Pasajero> result = pasajeroService.findPasajerosByNombre("Juan");
        assertEquals("Juan", result.get(0).getNombre());
        verify(pasajeroRepository, times(1)).findByNombre("Juan");
    }

    @Test
    void countPasajerosByNombre() {
        // Dado
        when(pasajeroRepository.countByNombre("Juan")).thenReturn(3L);

        // Cuando
        long count = pasajeroService.countPasajerosByNombre("Juan");

        // Entonces
        assertEquals(3L, count);
        verify(pasajeroRepository, times(1)).countByNombre("Juan");
    }

    @Test
    void findPasajeroByNombreAndPasaporteId() {
        // Dado
        Long pasaporteId = 1L;
        Pasajero pasajero = new Pasajero(1L, "Juan", "Perez", "123", null, null);
        when(pasajeroRepository.findByNombreAndPasaporteId("Juan", pasaporteId)).thenReturn(Optional.of(pasajero));

        // Cuando
        Optional<Pasajero> result = pasajeroService.findPasajeroByNombreAndPasaporteId("Juan", pasaporteId);

        // Entonces
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        verify(pasajeroRepository, times(1)).findByNombreAndPasaporteId("Juan", pasaporteId);
    }

    @Test
    void updatePasajeroByNid() {
        // Dado
        String nid = "123";
        String nuevoNombre = "Juanito";
        String nuevoApellido = "Perezito";

        // Cuando
        doNothing().when(pasajeroRepository).updatePasajeroUid(nid, nuevoNombre, nuevoApellido);

        // Entonces
        pasajeroService.updatePasajeroByNid(nid, nuevoNombre, nuevoApellido);
        verify(pasajeroRepository, times(1)).updatePasajeroUid(nid, nuevoNombre, nuevoApellido);
    }

    @Test
    void findPasajerosWithoutReservas() {
        // Dado
        Pasajero pasajero1 = new Pasajero(1L, "Juan", "Perez", "123", null, null);
        Pasajero pasajero2 = new Pasajero(2L, "Maria", "Lopez", "124", null, null);
        List<Pasajero> pasajeros = Arrays.asList(pasajero1, pasajero2);

        // Cuando
        when(pasajeroRepository.findByReservasIsEmpty()).thenReturn(pasajeros);

        // Entonces
        List<Pasajero> result = pasajeroService.findPasajerosWithoutReservas();
        assertEquals(2, result.size());
        verify(pasajeroRepository, times(1)).findByReservasIsEmpty();
    }

    @Test
    void deleteReservaByNidAndCodigoReserva() {
        // Dado
        UUID codigoReserva = UUID.randomUUID();
        String nid = "123";

        // Cuando
        doNothing().when(pasajeroRepository).deleteReservaByNidAndCodigoReserva(nid, codigoReserva);

        // Entonces
        pasajeroService.deleteReservaByNidAndCodigoReserva(nid, codigoReserva);
        verify(pasajeroRepository, times(1)).deleteReservaByNidAndCodigoReserva(nid, codigoReserva);
    }

    @Test
    void findPasajerosByCodigoReserva() {
        // Dado
        UUID codigoReserva = UUID.randomUUID();
        Pasajero pasajero1 = new Pasajero(1L, "Juan", "Perez", "123", null, null);
        Pasajero pasajero2 = new Pasajero(2L, "Maria", "Lopez", "124", null, null);
        List<Pasajero> pasajeros = Arrays.asList(pasajero1, pasajero2);

        // Cuando
        when(pasajeroRepository.findPasajerosByCodigoReserva(codigoReserva)).thenReturn(pasajeros);

        // Entonces
        List<Pasajero> result = pasajeroService.findPasajerosByCodigoReserva(codigoReserva);
        assertEquals(2, result.size());
        verify(pasajeroRepository, times(1)).findPasajerosByCodigoReserva(codigoReserva);
    }

    @Test
    void updateApellidoByNombreAndId() {
        // Dado
        String nombre = "Juan";
        Long id = 1L;
        String nuevoApellido = "Perezito";

        // Cuando
        doNothing().when(pasajeroRepository).updateApellidoPasajeroByNombreAndUid(nombre, id, nuevoApellido);

        // Entonces
        pasajeroService.updateApellidoByNombreAndId(nombre, id, nuevoApellido);
        verify(pasajeroRepository, times(1)).updateApellidoPasajeroByNombreAndUid(nombre, id, nuevoApellido);
    }

    @Test
    void findPasajeroByNidAndCodigoReserva() {
        // Dado
        UUID codigoReserva = UUID.randomUUID();
        String nid = "123";
        Pasajero pasajero = new Pasajero(1L, "Juan", "Perez", nid, null, null);

        // Cuando
        when(pasajeroRepository.findPasajeroByNidAndCodigoReserva(nid, codigoReserva)).thenReturn(Optional.of(pasajero));

        // Entonces
        Optional<Pasajero> result = pasajeroService.findPasajeroByNidAndCodigoReserva(nid, codigoReserva);
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        verify(pasajeroRepository, times(1)).findPasajeroByNidAndCodigoReserva(nid, codigoReserva);
    }

}