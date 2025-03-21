package edu.unimag.sistemavuelo2.services;

import edu.unimag.sistemavuelo2.entities.Pasajero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasajeroService {
    // Buscar un pasajero por su NID
    Optional<Pasajero> findPasajerosByNid(String nid);

    // Buscar pasajeros por nombre
    List<Pasajero> findPasajerosByNombre(String nombre);

    // Contar cuántos pasajeros tienen el mismo nombre
    long countPasajerosByNombre(String nombre);

    // Buscar pasajeros que no tienen reservas
    List<Pasajero> findPasajerosWithoutReservas();

    // Buscar un pasajero por nombre y pasaporte
    Optional<Pasajero> findPasajeroByNombreAndPasaporteId(String nombre, Long pasaporteId);

    // Actualizar el nombre y apellido de un pasajero por su NID
    void updatePasajeroByNid(String nid, String nombre, String apellido);

    // Obtener los pasajeros de una reserva dado un código de reserva
    List<Pasajero> findPasajerosByCodigoReserva(UUID codigoReserva);

    // Eliminar la reserva de un pasajero dado su NID y el código de reserva
    void deleteReservaByNidAndCodigoReserva(String nid, UUID codigoReserva);

    // Actualizar el apellido de un pasajero dado su nombre y su ID
    void updateApellidoByNombreAndId(String nombre, Long id, String apellido);

    // Buscar un pasajero dado su NID y el código de reserva
    Optional<Pasajero> findPasajeroByNidAndCodigoReserva(String nid, UUID codigoReserva);

}
