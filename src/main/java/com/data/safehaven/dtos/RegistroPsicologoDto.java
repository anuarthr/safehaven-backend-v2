package com.data.safehaven.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistroPsicologoDto(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotNull(message = "El rol es obligatorio")
        Long rol,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String correoElectronico,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @Positive(message = "La edad debe ser un número positivo")
        Integer edad,

        String telefono,
        String sexo,
        LocalDate fechaDeNacimiento,
        String especialidad,

        @PositiveOrZero(message = "Los años de experiencia no pueden ser negativos")
        Integer anosDeExperiencia,

        String horarioDeAtencion) {
}
