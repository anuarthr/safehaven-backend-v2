package com.data.safehaven.dtos;

import java.time.LocalDate;

public record EstadoCitaDto(Long idEstadoCita,
                            LocalDate fechaInicioEstado,
                            LocalDate fechaFinEstado,
                            LocalDate fechaInicioDeRegistroEstado,
                            LocalDate fechaFinDeRegistroEstado) {
}
