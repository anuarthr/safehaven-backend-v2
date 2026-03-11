package com.data.safehaven.dtos;


import java.util.Date;

public record AdministradorDto(Long id,
                               String nombre,
                               String apellido,
                               Long rol,
                               String correoElectronico,
                               Integer edad,
                               Long telefono,
                               String sexo,
                               Date fechaDeNacimiento,
                               String cargo) {
}
