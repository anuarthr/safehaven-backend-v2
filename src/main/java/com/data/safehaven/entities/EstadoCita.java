package com.data.safehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "estados_cita")
public class EstadoCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoCita;

    private LocalDate fechaInicioEstado;

    private LocalDate fechaFinEstado;

    private LocalDate fechaInicioDeRegistroEstado;

    private LocalDate fechaFinDeRegistroEstado;

    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "idTipoEstadoCita")
    private TipoEstadoCita tipoEstadoCita;
}
