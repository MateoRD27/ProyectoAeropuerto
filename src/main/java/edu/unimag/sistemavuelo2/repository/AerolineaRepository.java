package edu.unimag.sistemavuelo2.repository;

import edu.unimag.sistemavuelo2.entities.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    //

}
