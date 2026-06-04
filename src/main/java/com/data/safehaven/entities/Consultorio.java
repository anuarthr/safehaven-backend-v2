package com.data.safehaven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consultorios")
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ubicacion;
    private String tipo;
    private Integer capacidad;
    private String horarioDeApertura;
    private String horarioDeCierre;
    private boolean activo;

    @OneToMany(mappedBy = "consultorio", fetch = FetchType.LAZY)
    private List<Cita> citas;
}
