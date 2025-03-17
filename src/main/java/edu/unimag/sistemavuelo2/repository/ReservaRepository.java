package edu.unimag.sistemavuelo2.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.entities.Reserva;
import edu.unimag.sistemavuelo2.entities.Vuelo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    //buscar una reserva por su codigoReserva
    Optional<Reserva> findByCodigoReserva(UUID codigoReserva);

    //añadir una reserva
    @Modifying
    @Transactional
    Reserva save(Reserva reserva);

    //eliminar una reserva
    @Modifying
    @Transactional
    void delete(Reserva reserva);

    //buscar todas las reservas de un pasajero
    List<Reserva> findByPasajero(Pasajero pasajero);

    //buscar todas las reservas de un vuelo
    List<Reserva> findByVuelo(Vuelo vuelo);

    //modificar una reserva dado su codigoReserva
    @Query("UPDATE Reserva r SET r.pasajero = ?1, r.vuelo = ?2 WHERE r.codigoReserva = ?3")
    void updateReserva(Pasajero pasajero, Vuelo vuelo, UUID codigoReserva);

    //buscar todas las reservas paginadas de 50 en 50
    Page<Reserva> findAll(Pageable pageable);

    //modificar el pasajero de una reserva dado el codigoReserva
    @Modifying
    @Transactional
    @Query("UPDATE Reserva r SET r.pasajero = ?1 WHERE r.codigoReserva = ?2")
    void updatePasajero(Pasajero pasajero, UUID codigoReserva);

    //modificar el vuelo de una reserva dado el codigoReserva
    @Modifying
    @Transactional
    @Query("UPDATE Reserva r SET r.vuelo = ?1 WHERE r.codigoReserva = ?2")
    void updateVuelo(Vuelo vuelo, UUID codigoReserva);



}
