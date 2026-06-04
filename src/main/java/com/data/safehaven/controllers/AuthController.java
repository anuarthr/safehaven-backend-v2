package com.data.safehaven.controllers;

import com.data.safehaven.dtos.LoginRequestDto;
import com.data.safehaven.dtos.LoginResponseDto;
import com.data.safehaven.dtos.UsuarioDto;
import com.data.safehaven.security.JwtService;
import com.data.safehaven.services.UsuarioServiceI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioServiceI usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioServiceI usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        UsuarioDto usuario = usuarioService.findByCorreoElectronico(loginRequest.email()).orElseThrow();
        return ResponseEntity.ok(toLoginResponse(usuario, token));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDto> obtenerUsuarioLogueado(Authentication authentication) {
        return usuarioService.findByCorreoElectronico(authentication.getName())
                .map(u -> ResponseEntity.ok(toLoginResponse(u, null)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private LoginResponseDto toLoginResponse(UsuarioDto usuario, String token) {
        UsuarioDto.RolDto rol = usuario.rol();
        return new LoginResponseDto(
                usuario.id(),
                usuario.nombre(),
                usuario.apellido(),
                usuario.correoElectronico(),
                rol == null ? null : new LoginResponseDto.RolDto(rol.id(), rol.nombre()),
                usuario.edad(),
                usuario.telefono(),
                usuario.sexo(),
                usuario.fechaDeNacimiento(),
                token
        );
    }
}
