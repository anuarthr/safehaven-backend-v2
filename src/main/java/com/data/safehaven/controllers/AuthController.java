package com.data.safehaven.controllers;

import com.data.safehaven.dtos.LoginRequestDto;
import com.data.safehaven.dtos.LoginResponseDto;
import com.data.safehaven.dtos.UsuarioDto;
import com.data.safehaven.exceptions.ErrorMessage;
import com.data.safehaven.services.UsuarioServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioServiceI usuarioService;

    public AuthController(UsuarioServiceI usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        Optional<UsuarioDto> usuarioOpt = usuarioService.findByCorreoElectronico(loginRequest.email());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado", "USER_NOT_FOUND"));
        }
        UsuarioDto usuario = usuarioOpt.get();
        if (!usuarioService.validatePassword(usuario, loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Credenciales inválidas", "INVALID_CREDENTIALS"));
        }
        return ResponseEntity.ok(toLoginResponse(usuario));
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioLogueado(@RequestParam("email") String email) {
        return usuarioService.findByCorreoElectronico(email)
                .map(u -> ResponseEntity.ok(toLoginResponse(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private LoginResponseDto toLoginResponse(UsuarioDto usuario) {
        return new LoginResponseDto(
                usuario.id(),
                usuario.nombre(),
                usuario.apellido(),
                usuario.rol(),
                usuario.correoElectronico(),
                usuario.edad(),
                usuario.telefono(),
                usuario.sexo()
        );
    }
}


