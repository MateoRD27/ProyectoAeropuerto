package edu.unimag.sistemavuelo2.services;

import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.repository.PasajeroRepository;
import edu.unimag.sistemavuelo2.services.impl.PasajeroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.UUID;


//la recomendacion es usar extendwich para que reconociera mikito y no en el constructor
@ExtendWith(MockitoExtension.class)
class PasajeroServiceTest {
    @Mock //
    private PasajeroRepository pasajeroRepository;

    @InjectMocks //
    private PasajeroServiceImpl pasajeroService;

    @Test
    void findPasajerosByNid() {
        // Dado
        Pasajero pasajero = new Pasajero(1L, "Juan", "Perez", "123111", null, null);
        Optional<Pasajero> optionalPasajero = Optional.of(pasajero);

        // Cuando
        when(pasajeroRepository.findByNid("123111")).thenReturn(optionalPasajero);

        // Entonces
        Optional<Pasajero> result = pasajeroService.findPasajerosByNid("123111");

        assertTrue(result.isPresent(), "El pasajero debería estar presente");
        assertEquals("Juan", result.get().getNombre()); // Valida que el nombre es "Juan"
        verify(pasajeroRepository, times(1)).findByNid("123111"); // Verifica que el método se llamó una vez
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

        // Crear un pasajero mock
        Pasajero pasajeroMock = new Pasajero();
        pasajeroMock.setNid(nid);
        pasajeroMock.setNombre("AntiguoNombre");
        pasajeroMock.setApellido("AntiguoApellido");

        // Simular que el repositorio devuelve el pasajero cuando se busque por NID
        when(pasajeroRepository.findByNid(nid)).thenReturn(Optional.of(pasajeroMock));

        // Simular que save() simplemente guarda el pasajero
        // No es necesario usar doNothing, solo verificamos la llamada a save
        when(pasajeroRepository.save(any(Pasajero.class))).thenReturn(pasajeroMock);

        // Cuando
        pasajeroService.updatePasajeroByNid(nid, nuevoNombre, nuevoApellido);

        // Entonces
        verify(pasajeroRepository, times(1)).save(any(Pasajero.class)); // Verificar que save fue llamado
        assertEquals(nuevoNombre, pasajeroMock.getNombre());  // Verificar que el nombre se actualizó
        assertEquals(nuevoApellido, pasajeroMock.getApellido());  // Verificar que el apellido se actualizó
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

        // Simulamos que el repositorio encuentra el pasajero con la reserva
        Optional<Pasajero> pasajeroOpt = Optional.of(new Pasajero());  // Crear un Pasajero mockado

        when(pasajeroRepository.findPasajeroByNidAndCodigoReserva(nid, codigoReserva)).thenReturn(pasajeroOpt);

        // Cuando
        doNothing().when(pasajeroRepository).deleteReservaByNidAndCodigoReserva(nid, codigoReserva);

        // Ejecutamos el método del servicio
        pasajeroService.deleteReservaByNidAndCodigoReserva(nid, codigoReserva);

        // Entonces
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

        // Crear un pasajero mock
        Pasajero pasajeroMock = new Pasajero();
        pasajeroMock.setId(id);
        pasajeroMock.setNombre(nombre);
        pasajeroMock.setApellido("ViejoApellido");

        // Simular que el repositorio devuelve el pasajero cuando se busque por ID
        when(pasajeroRepository.findById(id)).thenReturn(Optional.of(pasajeroMock));

        // Simular que save() simplemente guarda el pasajero actualizado
        when(pasajeroRepository.save(any(Pasajero.class))).thenReturn(pasajeroMock);

        // Cuando
        pasajeroService.updateApellidoByNombreAndId(nombre, id, nuevoApellido);

        // Entonces
        verify(pasajeroRepository, times(1)).save(any(Pasajero.class)); // Verificar que save fue llamado
        assertEquals(nuevoApellido, pasajeroMock.getApellido());  // Verificar que el apellido se actualizó
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