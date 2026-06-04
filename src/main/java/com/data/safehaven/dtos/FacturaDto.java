package com.data.safehaven.dtos;

import java.time.LocalDate;

public record FacturaDto(
        Long id,
        double monto,
        String insertBy,
        String updateBy,
        LocalDate fechaDePago,
        LocalDate insertAt,
        LocalDate updateAt,
        Long cita,
        Long paciente
) {}

