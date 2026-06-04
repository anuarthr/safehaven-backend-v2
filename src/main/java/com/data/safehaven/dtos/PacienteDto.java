package com.data.safehaven.dtos;

import java.time.LocalDate;

public record PacienteDto(Long id,
                          String nombre,
                          String apellido,
                          String correoElectronico,
                          Integer edad,
                          String telefono,
                          String sexo,
                          LocalDate fechaDeNacimiento,
                          String aseguradora,
                          String estadoDeSalud,
                          LocalDate fechaDeRegistro,
                          Long rol) {
}
