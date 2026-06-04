package com.data.safehaven.dtos;

import jakarta.validation.constraints.NotBlank;

public record RolDto(Long id,

                     @NotBlank(message = "El nombre del rol es obligatorio")
                     String nombre,

                     String descripcion) {
}
