package com.data.safehaven.controllers;

import com.data.safehaven.dtos.CitaDto;
import com.data.safehaven.services.CitaServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaServiceI citaService;

    public CitaController(CitaServiceI citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaDto>> obtenerCitas() {
        return ResponseEntity.ok(citaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> obtenerCitaById(@PathVariable Long id) {
        return citaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaDto> crearCita(@RequestBody CitaDto cita) {
        CitaDto nuevaCita = citaService.saveCita(cita);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevaCita.id()).toUri();
        return ResponseEntity.created(location).body(nuevaCita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CitaDto> eliminarCita(@PathVariable Long id) {
        return citaService.findById(id)
                .map(c -> {
                    citaService.deleteCita(id);
                    return ResponseEntity.ok().body(c);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> actualizarCita(@PathVariable Long id, @RequestBody CitaDto cita) {
        return citaService.updateCita(id, cita)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
