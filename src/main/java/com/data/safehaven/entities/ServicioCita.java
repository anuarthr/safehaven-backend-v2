package com.data.safehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "servicios_citas")
public class ServicioCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicioCita;

    @ManyToOne
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;
}
