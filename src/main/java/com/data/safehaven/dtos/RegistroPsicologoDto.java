package com.data.safehaven.dtos;

import java.util.Date;

public record RegistroPsicologoDto(String nombre,
                                   String apellido,
                                   Long rol,
                                   String correoElectronico,
                                   String password,
                                   Integer edad,
                                   Long telefono,
                                   String sexo,
                                   Date fechaDeNacimiento,
                                   String especialidad,
                                   Integer anosDeExperiencia,
                                   String horarioDeAtencion) {
}
