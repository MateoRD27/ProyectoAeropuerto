package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PasaporteRepository extends JpaRepository<Pasaporte, Long> {
// obtener un paaporte dado un nombre apellido y nid de un pasajero
    List<Pasaporte> findByPasajeroNombreAndPasajeroApellidoAndPasajeroNid(String nombre, String apellido, String nid);
// añadir un pasaporte
    Pasaporte save(Pasaporte pasaporte);
}

