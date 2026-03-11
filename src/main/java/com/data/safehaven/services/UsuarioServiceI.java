package com.data.safehaven.services;
import com.data.safehaven.dtos.UsuarioDto;
import java.util.Optional;
public interface UsuarioServiceI {
    Optional<UsuarioDto> findByCorreoElectronico(String email);
    boolean validatePassword(UsuarioDto usuario, String password);
}