package edu.unimag.sistemavuelo2.services.impl;


import edu.unimag.sistemavuelo2.services.PasajeroService;
import org.springframework.stereotype.Service;
import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.repository.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasajeroServiceImpl implements PasajeroService {
    private final PasajeroRepository pasajeroRepository;

    @Autowired
    public PasajeroServiceImpl(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    @Override
    public Optional<Pasajero> findPasajerosByNid(String nid) {
        return pasajeroRepository.findByNid(nid);
    }

    @Override
    public List<Pasajero> findPasajerosByNombre(String nombre) {
        return pasajeroRepository.findByNombre(nombre);
    }

    @Override
    public long countPasajerosByNombre(String nombre) {
        return pasajeroRepository.countByNombre(nombre);
    }

    @Override
    public List<Pasajero> findPasajerosWithoutReservas() {
        return pasajeroRepository.findByReservasIsEmpty();
    }

    @Override
    public Optional<Pasajero> findPasajeroByNombreAndPasaporteId(String nombre, Long pasaporteId) {
        return pasajeroRepository.findByNombreAndPasaporteId(nombre, pasaporteId);
    }

    @Override
    public void updatePasajeroByNid(String nid, String nombre, String apellido) {
        Optional<Pasajero> pasajeroOpt = pasajeroRepository.findByNid(nid).stream().findFirst();

        if (pasajeroOpt.isPresent()) {
            Pasajero pasajero = pasajeroOpt.get();
            pasajero.setNombre(nombre);
            pasajero.setApellido(apellido);
            pasajeroRepository.save(pasajero); // Guarda los cambios en la BD
        } else {
            throw new RuntimeException("Pasajero con NID " + nid + " no encontrado");
        }
    }


    @Override
    public List<Pasajero> findPasajerosByCodigoReserva(UUID codigoReserva) {
        return pasajeroRepository.findPasajerosByCodigoReserva(codigoReserva);
    }

    @Override
    public void deleteReservaByNidAndCodigoReserva(String nid, UUID codigoReserva) {
        Optional<Pasajero> pasajeroOpt = pasajeroRepository.findPasajeroByNidAndCodigoReserva(nid, codigoReserva);

        if (pasajeroOpt.isPresent()) {
            pasajeroRepository.deleteReservaByNidAndCodigoReserva(nid, codigoReserva);
        } else {
            throw new RuntimeException("No se encontró una reserva con el código " + codigoReserva + " para el pasajero con NID " + nid);
        }
    }

    @Override
    public void updateApellidoByNombreAndId(String nombre, Long id, String apellido) {
        Optional<Pasajero> pasajeroOpt = pasajeroRepository.findById(id);

        if (pasajeroOpt.isPresent()) {
            Pasajero pasajero = pasajeroOpt.get();

            // Verificar que el nombre coincida
            if (pasajero.getNombre().equals(nombre)) {
                pasajero.setApellido(apellido);
                pasajeroRepository.save(pasajero); // Guarda los cambios en la BD
            } else {
                throw new RuntimeException("El nombre no coincide con el ID proporcionado.");
            }
        } else {
            throw new RuntimeException("Pasajero con ID " + id + " no encontrado.");
        }
    }


    @Override
    public Optional<Pasajero> findPasajeroByNidAndCodigoReserva(String nid, UUID codigoReserva) {
        return pasajeroRepository.findPasajeroByNidAndCodigoReserva(nid, codigoReserva);
    }
}
