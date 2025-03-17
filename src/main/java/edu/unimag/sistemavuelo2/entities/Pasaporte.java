package edu.unimag.sistemavuelo2.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pasaportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Pasaporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pasaporteNid;

    @OneToOne(mappedBy = "pasaporte")
    private Pasajero pasajero;



}
