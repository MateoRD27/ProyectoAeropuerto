package edu.unimag.sistemavuelo2.services;


import org.springframework.stereotype.Service;
import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.repository.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasajeroService {
    private final PasajeroRepository pasajeroRepository;

    @Autowired
    public PasajeroService(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    // Buscar un pasajero por su NID
    public List<Pasajero> findPasajerosByNid(String nid) {
        return pasajeroRepository.findByNid(nid);
    }

    // Buscar pasajeros por nombre
    public List<Pasajero> findPasajerosByNombre(String nombre) {
        return pasajeroRepository.findByNombre(nombre);
    }

    // Contar cuántos pasajeros tienen el mismo nombre
    public long countPasajerosByNombre(String nombre) {
        return pasajeroRepository.countByNombre(nombre);
    }

    // Buscar pasajeros que no tienen reservas
    public List<Pasajero> findPasajerosWithoutReservas() {
        return pasajeroRepository.findByReservasIsEmpty();
    }

    // Buscar un pasajero por nombre y pasaporte
    public Optional<Pasajero> findPasajeroByNombreAndPasaporteId(String nombre, Long pasaporteId) {
        return pasajeroRepository.findByNombreAndPasaporteId(nombre, pasaporteId);
    }

    // Actualizar el nombre y apellido de un pasajero por su NID
    public void updatePasajeroByNid(String nid, String nombre, String apellido) {
        pasajeroRepository.updatePasajeroUid(nid, nombre, apellido);
    }

    // Obtener los pasajeros de una reserva dado un código de reserva
    public List<Pasajero> findPasajerosByCodigoReserva(UUID codigoReserva) {
        return pasajeroRepository.findPasajerosByCodigoReserva(codigoReserva);
    }

    // Eliminar la reserva de un pasajero dado su NID y el código de reserva
    public void deleteReservaByNidAndCodigoReserva(String nid, UUID codigoReserva) {
        pasajeroRepository.deleteReservaByNidAndCodigoReserva(nid, codigoReserva);
    }

    // Actualizar el apellido de un pasajero dado su nombre y su ID
    public void updateApellidoByNombreAndId(String nombre, Long id, String apellido) {
        pasajeroRepository.updateApellidoPasajeroByNombreAndUid(nombre, id, apellido);
    }

    // Buscar un pasajero dado su NID y el código de reserva
    public Optional<Pasajero> findPasajeroByNidAndCodigoReserva(String nid, UUID codigoReserva) {
        return pasajeroRepository.findPasajeroByNidAndCodigoReserva(nid, codigoReserva);
    }
}
