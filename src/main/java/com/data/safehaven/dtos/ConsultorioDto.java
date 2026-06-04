package com.data.safehaven.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ConsultorioDto(Long id,

                             @NotBlank(message = "El nombre es obligatorio")
                             String nombre,

                             String ubicacion,
                             String tipo,

                             @Positive(message = "La capacidad debe ser un número positivo")
                             Integer capacidad,

                             String horarioDeApertura,
                             String horarioDeCierre,
                             boolean activo) {
}
