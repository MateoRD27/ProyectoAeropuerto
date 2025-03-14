package edu.unimag.sistemavuelo2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.unimag.sistemavuelo2.entities.Vuelo;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    //agregar un vuelo
    Vuelo save(Vuelo vuelo);

    //eliminar un vuelo dado su numero de vuelo
    void deleteByNumeroVuelo(UUID numeroVuelo);

    //buscar un vuelo dado su numero de vuelo
    Optional<Vuelo> findByNumeroVuelo(UUID numeroVuelo);

    //buscar todos los vuelos paginado
    Page<Vuelo> findAll(Pageable pageable);

    //obtener todos los vuelos de una aerolinea dado el nombre de la aerolinea
    @Query("SELECT v FROM Vuelo v JOIN v.aerolineas a WHERE a.nombre = ?1")
    List<Vuelo> findByAerolinea(String nombreAerolinea);

    //obtener los vuelos que han ido de un origen a un destino
    @Query("SELECT v FROM Vuelo v WHERE v.origen = ?1 AND v.destino = ?2")
    List<Vuelo> findByOrigenAndDestino(String origen, String destino);

    //obtener los vuelos que han partido de un origen
    @Query("SELECT v FROM Vuelo v WHERE v.origen = ?1")
    List<Vuelo> findByOrigen(String origen);

    //obtener los vuelos que han llegado a un destino
    @Query("SELECT v FROM Vuelo v WHERE v.destino = ?1")
    List<Vuelo> findByDestino(String destino);







}
