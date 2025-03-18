package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Pasajero;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    // Buscar un pasajero por su Nid exacto
    Optional<Pasajero> findByNid(String nid);

    //Buscar todos los pasajeros que tengan el mismo nombre ordenados por nid
    List<Pasajero> findByNombreOrderByNid(String nombre);

    //buscar un pasajero por su nombre y pasaporte
    Optional<Pasajero> findByNombreAndPasaporteId(String nombre, Long pasaporteId);

    //añadir un pasajero
    @Modifying
    @Transactional
    Pasajero save(Pasajero pasajero);

    //eliminar un pasajero
    @Modifying
    @Transactional
    void delete(Pasajero pasajero);


    //buscar un pasajero por su nid y modificar su nombre y apellido
    @Modifying
    @Transactional
    @Query("UPDATE Pasajero p SET p.nombre = :nombre, p.apellido = :apellido WHERE p.nid = :nid")
    void updateNombreAndApellidoByNid(@Param("nid") String nid, @Param("nombre") String nombre, @Param("apellido") String apellido);


    // obtener el pasajero de una reserva dado un codigo de reserva
    @Query("SELECT p FROM Pasajero p JOIN p.reservas r WHERE r.codigoReserva = :codigoReserva")
    List<Pasajero> findPasajerosByCodigoReserva(@Param("codigoReserva") UUID codigoReserva);


    //obtener un pasajero dado su nombre y nid y actualizar su apellido

    @Modifying
    @Transactional
    @Query("UPDATE Pasajero p SET p.apellido = :apellido WHERE p.nombre = :nombre AND p.nid = :nid")
    void updateApellidoByNombreAndNid(@Param("nombre") String nombre, @Param("nid") String nid, @Param("apellido") String apellido);

    //buscar un pasajero de un reserva dado el nid del pasajero y el uuid de la reserva
    @Query("SELECT p FROM Pasajero p JOIN p.reservas r WHERE r.codigoReserva = :codigoReserva AND p.nid = :nid")
    Pasajero findPasajeroByNidAndCodigoReserva(@Param("nid") String nid, @Param("codigoReserva") UUID codigoReserva);



}
