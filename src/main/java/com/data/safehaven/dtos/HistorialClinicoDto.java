package com.data.safehaven.dtos;

import java.time.LocalDate;

public record HistorialClinicoDto(Long id,
                                  LocalDate fechaDeCreacion,
                                  String comentarios) {
}
