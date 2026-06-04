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
@Table(name = "pacientes")
public class Paciente extends Usuario{

    private String aseguradora;
    private String estadoDeSalud;

    private LocalDate fechaDeRegistro;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<HistorialClinico> historialClinico;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Diagnostico> diagnostico;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Cita> cita;
}
