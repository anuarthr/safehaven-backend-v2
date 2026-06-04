package com.data.safehaven.dtos;


import java.time.LocalDate;

public record AdministradorDto(Long id,
                               String nombre,
                               String apellido,
                               Long rol,
                               String correoElectronico,
                               Integer edad,
                               String telefono,
                               String sexo,
                               LocalDate fechaDeNacimiento,
                               String cargo) {
}
