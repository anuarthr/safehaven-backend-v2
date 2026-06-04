package com.data.safehaven.dtos;

import java.time.LocalDate;

public record DiagnosticoDto(Long id,
                             String descripcion,
                             LocalDate fecha,
                             Long paciente,
                             Long psicologo) {
}
