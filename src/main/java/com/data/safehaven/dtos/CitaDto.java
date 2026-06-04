package com.data.safehaven.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;
import java.time.LocalDate;

public record CitaDto(Long id,

                      @NotBlank(message = "El motivo es obligatorio")
                      String motivo,

                      @JsonFormat(pattern = "HH:mm")
                      LocalTime duracion,

                      String tipoCita,
                      String insertBy,
                      String updateBy,

                      @Pattern(regexp = "PENDIENTE|CONFIRMADA|CANCELADA|COMPLETADA",
                              message = "El estado debe ser PENDIENTE, CONFIRMADA, CANCELADA o COMPLETADA")
                      String estado,

                      @NotNull(message = "La fecha es obligatoria")
                      LocalDate fecha,

                      @NotNull(message = "La hora es obligatoria")
                      @JsonFormat(pattern = "HH:mm")
                      LocalTime hora,

                      @NotNull(message = "El paciente es obligatorio")
                      Long paciente,

                      @NotNull(message = "El psicólogo es obligatorio")
                      Long psicologo,

                      @NotNull(message = "El consultorio es obligatorio")
                      Long consultorio) {
}
