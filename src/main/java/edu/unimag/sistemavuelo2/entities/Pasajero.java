package edu.unimag.sistemavuelo2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pasajeros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String nombre;
    private  String apellido;
    private String nid;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "pasaportes_id", referencedColumnName = "id")
    private Pasaporte pasaporte;


    @OneToMany(mappedBy = "pasajero", fetch = FetchType.LAZY)
    private List<Reserva> reservas;

}
