package com.data.safehaven;

import com.data.safehaven.dtos.LoginRequestDto;
import com.data.safehaven.dtos.LoginResponseDto;
import com.data.safehaven.dtos.PacienteDto;
import com.data.safehaven.dtos.RegistroPacienteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de integración del flujo de autenticación y autorización contra un Postgres real.
 */
class AuthFlowIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    /** Email único por ejecución para que los tests de registro sean idempotentes. */
    private static String uniqueEmail(String prefijo) {
        return prefijo + "." + UUID.randomUUID() + "@demo.com";
    }

    private String loginObtenerToken(String email, String password) {
        LoginResponseDto body = rest.postForObject("/api/auth/login",
                new LoginRequestDto(email, password), LoginResponseDto.class);
        return body.token();
    }

    @Test
    void login_conCredencialesSemilla_devuelveTokenJwt() {
        ResponseEntity<LoginResponseDto> resp = rest.postForEntity("/api/auth/login",
                new LoginRequestDto("admin@safehaven.com", "admin123"), LoginResponseDto.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().token()).isNotBlank();
        assertThat(resp.getBody().rol().nombre()).isEqualTo("Administrador");
    }

    @Test
    void login_conCredencialesInvalidas_devuelve401() {
        ResponseEntity<String> resp = rest.postForEntity("/api/auth/login",
                new LoginRequestDto("admin@safehaven.com", "incorrecta"), String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(resp.getBody()).contains("INVALID_CREDENTIALS");
    }

    @Test
    void login_sinPassword_devuelve400PorValidacion() {
        ResponseEntity<String> resp = rest.postForEntity("/api/auth/login",
                new LoginRequestDto("admin@safehaven.com", ""), String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void endpointProtegido_sinToken_devuelve401() {
        ResponseEntity<String> resp = rest.getForEntity("/api/pacientes", String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void endpointProtegido_conToken_devuelve200() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginObtenerToken("admin@safehaven.com", "admin123"));

        ResponseEntity<String> resp = rest.exchange("/api/pacientes", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void registroPublicoDePaciente_yLuegoLoginConSusCredenciales() {
        String email = uniqueEmail("integracion");
        RegistroPacienteDto registro = new RegistroPacienteDto(
                "Integracion", "Test", email, "secret123",
                28, "3001234567", "F", LocalDate.of(1996, 5, 20),
                "Aseguradora Demo", "Bueno", null, 4L);

        ResponseEntity<PacienteDto> creado = rest.postForEntity("/api/pacientes", registro, PacienteDto.class);
        assertThat(creado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(creado.getBody()).isNotNull();
        assertThat(creado.getBody().correoElectronico()).isEqualTo(email);

        // La contraseña se guardó hasheada; el login debe funcionar con la original.
        ResponseEntity<LoginResponseDto> login = rest.postForEntity("/api/auth/login",
                new LoginRequestDto(email, "secret123"), LoginResponseDto.class);
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(login.getBody().token()).isNotBlank();
        assertThat(login.getBody().rol().nombre()).isEqualTo("Paciente");
    }

    @Test
    void pacienteNoPuedeCrearPsicologo_devuelve403() {
        // Registrar y autenticar un paciente
        String email = uniqueEmail("sinpermisos");
        RegistroPacienteDto registro = new RegistroPacienteDto(
                "Sin", "Permisos", email, "secret123",
                40, "3009998877", "M", LocalDate.of(1985, 1, 1),
                "Aseg", "Regular", null, 4L);
        rest.postForEntity("/api/pacientes", registro, PacienteDto.class);

        String psicologoJson = """
                {"nombre":"X","apellido":"Y","rol":3,"correoElectronico":"x.y@demo.com",
                 "password":"secret123","edad":30,"telefono":"3001112233","sexo":"M",
                 "fechaDeNacimiento":"1990-01-01","especialidad":"Clinica",
                 "anosDeExperiencia":5,"horarioDeAtencion":"9-5"}""";
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setBearerAuth(loginObtenerToken(email, "secret123"));
        jsonHeaders.set("Content-Type", "application/json");

        ResponseEntity<String> resp = rest.exchange("/api/psicologos", HttpMethod.POST,
                new HttpEntity<>(psicologoJson, jsonHeaders), String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
