package com.data.safehaven.controllers;

import com.data.safehaven.dtos.PacienteDto;
import com.data.safehaven.dtos.RegistroPacienteDto;
import com.data.safehaven.services.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> obtenerPacientes() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> obtenerPacienteById(@PathVariable Long id) {
        return pacienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteDto> crearPaciente(@RequestBody RegistroPacienteDto paciente) {
        PacienteDto nuevoPaciente = pacienteService.savePaciente(paciente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoPaciente.id()).toUri();
        return ResponseEntity.created(location).body(nuevoPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PacienteDto> eliminarPaciente(@PathVariable Long id) {
        return pacienteService.findById(id)
                .map(p -> {
                    pacienteService.deletePaciente(id);
                    return ResponseEntity.ok().body(p);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteDto paciente) {
        return pacienteService.updatePaciente(id, paciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
