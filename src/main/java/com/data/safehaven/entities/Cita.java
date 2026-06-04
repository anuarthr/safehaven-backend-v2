package com.data.safehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motivo;

    private LocalTime duracion;

    private String tipoCita;

    private String insertBy;
    private String updateBy;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado = "PENDIENTE";

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(nullable = false, name = "id_psicologo")
    private Psicologo psicologo;
    @ManyToOne
    @JoinColumn(nullable = false, name = "id_consultorio")
    private Consultorio consultorio;

    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    private List<Factura> factura;

    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    private List<ServicioCita> servicioDeCita;

    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    private List<EstadoCita> estadoCita;
}

