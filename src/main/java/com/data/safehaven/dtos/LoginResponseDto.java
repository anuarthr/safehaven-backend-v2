package com.data.safehaven.dtos;

import java.time.LocalDate;

public record LoginResponseDto(
        Long id,
        String nombre,
        String apellido,
        String correoElectronico,
        RolDto rol,
        Integer edad,
        String telefono,
        String sexo,
        LocalDate fechaNacimiento,
        String token
) {
    public record RolDto(Long id, String nombre) {}
}