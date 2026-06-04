package com.data.safehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "historiales_clinicos")
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaDeCreacion;

    private String comentarios;

    @OneToMany(mappedBy = "historialClinico", fetch = FetchType.LAZY)
    private List<Tratamiento> tratamiento;

    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;
}
