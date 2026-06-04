package com.data.safehaven.security;

import com.data.safehaven.entities.Usuario;
import com.data.safehaven.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Carga el {@link Usuario} por correo electrónico y lo expone como {@link UserDetails}
 * para que Spring Security pueda autenticar y autorizar. El rol del usuario se traduce
 * a una autoridad con prefijo {@code ROLE_} (p. ej. {@code ROLE_ADMINISTRADOR}).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : "Paciente";
        var authority = new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase());

        return new User(usuario.getCorreoElectronico(), usuario.getPassword(), List.of(authority));
    }
}
