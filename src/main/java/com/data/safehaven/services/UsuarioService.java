package com.data.safehaven.services;

import com.data.safehaven.dtos.UsuarioDto;
import com.data.safehaven.dtos.UsuarioMapper;
import com.data.safehaven.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UsuarioServiceI {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Optional<UsuarioDto> findByCorreoElectronico(String email) {
        return usuarioRepository.findByCorreoElectronico(email).map(usuarioMapper::toDTO);
    }

    @Override
    public boolean validatePassword(UsuarioDto usuario, String password) {
        return usuario.password().equals(password);
    }
}
