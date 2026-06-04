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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String password;
    private Integer edad;
    private String telefono;
    private String sexo;
    private LocalDate fechaDeNacimiento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRol", nullable = true)
    private Rol rol;
}