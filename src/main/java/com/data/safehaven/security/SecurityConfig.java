package com.data.safehaven.security;

import com.data.safehaven.exceptions.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración central de seguridad: API stateless protegida por JWT, codificación BCrypt
 * de contraseñas, reglas de autorización por rol y CORS para el frontend.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_DOCS = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, ObjectMapper objectMapper) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Autenticación y registro de pacientes son públicos
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pacientes").permitAll()
                        .requestMatchers(PUBLIC_DOCS).permitAll()
                        // Alta de personal y roles: solo administradores
                        .requestMatchers(HttpMethod.POST, "/api/administradores", "/api/psicologos", "/api/roles")
                        .hasAnyRole("ADMINISTRADOR", "SUPERUSER")
                        // Borrados: solo administradores
                        .requestMatchers(HttpMethod.DELETE, "/api/**")
                        .hasAnyRole("ADMINISTRADOR", "SUPERUSER")
                        // El resto requiere autenticación
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(this::unauthorized)
                        .accessDeniedHandler(this::accessDenied))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void unauthorized(jakarta.servlet.http.HttpServletRequest request,
                              HttpServletResponse response,
                              org.springframework.security.core.AuthenticationException ex) throws java.io.IOException {
        writeError(response, HttpServletResponse.SC_UNAUTHORIZED,
                "Autenticación requerida o token inválido", "UNAUTHORIZED");
    }

    private void accessDenied(jakarta.servlet.http.HttpServletRequest request,
                              HttpServletResponse response,
                              org.springframework.security.access.AccessDeniedException ex) throws java.io.IOException {
        writeError(response, HttpServletResponse.SC_FORBIDDEN,
                "No tiene permisos para realizar esta acción", "ACCESS_DENIED");
    }

    private void writeError(HttpServletResponse response, int status, String message, String code) throws java.io.IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), new ErrorMessage(status, message, code));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).map(String::trim).toList());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
