package com.data.safehaven.dtos;


import java.util.Date;

public record PsicologoDto(Long id,
                           String nombre,
                           String apellido,
                           Long rol,
                           String correoElectronico,
                           Integer edad,
                           Long telefono,
                           String sexo,
                           Date fechaDeNacimiento,
                           String especialidad,
                           Integer anosDeExperiencia,
                           String horarioDeAtencion) {
}
