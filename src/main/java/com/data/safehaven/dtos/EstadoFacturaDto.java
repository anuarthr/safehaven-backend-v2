package com.data.safehaven.dtos;

import com.data.safehaven.entities.EstadoDeFacturaId;
import com.data.safehaven.entities.Factura;
import com.data.safehaven.entities.TipoEstadoFactura;

import java.time.LocalDate;

public record EstadoFacturaDto(EstadoDeFacturaId id,
                               Factura factura,
                               TipoEstadoFactura tipoEstadoFactura,
                               LocalDate fechaInicioEstado,
                               LocalDate fechaFinEstado,
                               LocalDate fechaInicioDeRegistroEstado,
                               LocalDate fechaFinDeRegistroEstado) {
}
