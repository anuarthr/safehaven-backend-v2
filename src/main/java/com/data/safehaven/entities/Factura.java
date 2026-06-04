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
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double monto;
    private String insertBy;
    private String updateBy;
    private LocalDate fechaDePago;

    private LocalDate insertAt;

    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "idCita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
    private List<EstadoFactura> estadoFactura;
}
