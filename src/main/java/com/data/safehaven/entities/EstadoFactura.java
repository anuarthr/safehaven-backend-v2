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
@Table(name = "estados_factura")
public class EstadoFactura {


    @EmbeddedId
    private EstadoDeFacturaId id;

    @ManyToOne
    @MapsId("facturaId")
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;

    @ManyToOne
    @MapsId("tipoDeEstadoFacturaId")
    @JoinColumn(name = "TIPODEESTADOFACTURA_ID")
    private TipoEstadoFactura tipoEstadoFactura;

    private LocalDate fechaInicioEstado;

    private LocalDate fechaFinEstado;

    private LocalDate fechaInicioDeRegistroEstado;

    private LocalDate fechaFinDeRegistroEstado;


}
