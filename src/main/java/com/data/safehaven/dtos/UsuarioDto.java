package com.data.safehaven.dtos;

import java.time.LocalDate;

public record UsuarioDto(Long id,
                         String nombre,
                         String apellido,
                         RolDto rol,
                         String correoElectronico,
                         Integer edad,
                         String telefono,
                         String sexo,
                         LocalDate fechaDeNacimiento) {

    public record RolDto(Long id, String nombre) {}
}
