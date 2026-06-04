package com.data.safehaven.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistroPacienteDto(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

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
        String aseguradora,
        String estadoDeSalud,
        LocalDate fechaDeRegistro,
        Long rol) {
}
