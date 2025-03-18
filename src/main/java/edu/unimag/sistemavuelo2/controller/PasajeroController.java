package edu.unimag.sistemavuelo2.controller;
import edu.unimag.sistemavuelo2.entities.Pasajero;
import edu.unimag.sistemavuelo2.services.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pasajeros")
public class PasajeroController {

    private final PasajeroService pasajeroService;

    @Autowired
    public PasajeroController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }

    // Obtener un pasajero por su NID
    @GetMapping("/nid/{nid}")
    public ResponseEntity<List<Pasajero>> getPasajerosByNid(@PathVariable String nid) {
        List<Pasajero> pasajeros = pasajeroService.findPasajerosByNid(nid);
        return pasajeros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pasajeros);
    }

    // Obtener pasajeros por nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<List<Pasajero>> getPasajerosByNombre(@PathVariable String nombre) {
        List<Pasajero> pasajeros = pasajeroService.findPasajerosByNombre(nombre);
        return pasajeros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pasajeros);
    }

    // Contar cuántos pasajeros tienen el mismo nombre
    @GetMapping("/count/{nombre}")
    public ResponseEntity<Long> countPasajerosByNombre(@PathVariable String nombre) {
        long count = pasajeroService.countPasajerosByNombre(nombre);
        return ResponseEntity.ok(count);
    }

    // Obtener pasajeros que no tienen reservas
    @GetMapping("/sin-reservas")
    public ResponseEntity<List<Pasajero>> getPasajerosSinReservas() {
        List<Pasajero> pasajeros = pasajeroService.findPasajerosWithoutReservas();
        return pasajeros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pasajeros);
    }

    // Obtener un pasajero por nombre y pasaporte
    @GetMapping("/nombre/{nombre}/pasaporte/{pasaporteId}")
    public ResponseEntity<Pasajero> getPasajeroByNombreAndPasaporte(@PathVariable String nombre, @PathVariable Long pasaporteId) {
        Optional<Pasajero> pasajero = pasajeroService.findPasajeroByNombreAndPasaporteId(nombre, pasaporteId);
        return pasajero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar el nombre y apellido de un pasajero dado su NID
    @PutMapping("/nid/{nid}")
    public ResponseEntity<Void> updatePasajeroByNid(@PathVariable String nid, @RequestParam String nombre, @RequestParam String apellido) {
        pasajeroService.updatePasajeroByNid(nid, nombre, apellido);
        return ResponseEntity.noContent().build();
    }

    // Obtener pasajeros de una reserva dado el código de reserva
    @GetMapping("/reserva/{codigoReserva}")
    public ResponseEntity<List<Pasajero>> getPasajerosByCodigoReserva(@PathVariable UUID codigoReserva) {
        List<Pasajero> pasajeros = pasajeroService.findPasajerosByCodigoReserva(codigoReserva);
        return pasajeros.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pasajeros);
    }

    // Eliminar la reserva de un pasajero dado su NID y código de reserva
    @DeleteMapping("/nid/{nid}/reserva/{codigoReserva}")
    public ResponseEntity<Void> deleteReservaByNidAndCodigoReserva(@PathVariable String nid, @PathVariable UUID codigoReserva) {
        pasajeroService.deleteReservaByNidAndCodigoReserva(nid, codigoReserva);
        return ResponseEntity.noContent().build();
    }

    // Actualizar el apellido de un pasajero dado su nombre y su ID
    @PutMapping("/nombre/{nombre}/id/{id}/apellido")
    public ResponseEntity<Void> updateApellidoByNombreAndId(@PathVariable String nombre, @PathVariable Long id, @RequestParam String apellido) {
        pasajeroService.updateApellidoByNombreAndId(nombre, id, apellido);
        return ResponseEntity.noContent().build();
    }

    // Buscar un pasajero dado su NID y el código de la reserva
    @GetMapping("/nid/{nid}/reserva/{codigoReserva}")
    public ResponseEntity<Pasajero> getPasajeroByNidAndCodigoReserva(@PathVariable String nid, @PathVariable UUID codigoReserva) {
        Optional<Pasajero> pasajero = pasajeroService.findPasajeroByNidAndCodigoReserva(nid, codigoReserva);
        return pasajero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
