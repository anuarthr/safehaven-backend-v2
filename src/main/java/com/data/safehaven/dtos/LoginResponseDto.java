package com.data.safehaven.dtos;
public record LoginResponseDto(
        Long id,
        String nombre,
        String apellido,
        Long rol,
        String correoElectronico,
        Integer edad,
        Long telefono,
        String sexo) {
}