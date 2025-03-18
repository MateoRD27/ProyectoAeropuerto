package edu.unimag.sistemavuelo2.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "vuelos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID numeroVuelo= UUID.randomUUID();;
    private String origen;
    private String destino;

    @OneToMany(mappedBy = "vuelo", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "vuelos_aerolineas",
            joinColumns = @JoinColumn(name = "vuelos_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "aerolineas_id", referencedColumnName = "id")
    )
    private List<Aerolinea> aerolineas= new ArrayList<>();

}
