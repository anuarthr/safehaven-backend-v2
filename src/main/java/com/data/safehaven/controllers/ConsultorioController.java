package com.data.safehaven.controllers;

import com.data.safehaven.dtos.ConsultorioDto;
import com.data.safehaven.services.ConsultorioServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    private final ConsultorioServiceI consultorioService;

    public ConsultorioController(ConsultorioServiceI consultorioService) {
        this.consultorioService = consultorioService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultorioDto>> obtenerConsultorios() {
        return ResponseEntity.ok(consultorioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultorioDto> obtenerConsultorioById(@PathVariable Long id) {
        return consultorioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConsultorioDto> crearConsultorio(@RequestBody ConsultorioDto consultorio) {
        ConsultorioDto nuevoConsultorio = consultorioService.saveConsultorio(consultorio);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoConsultorio.id()).toUri();
        return ResponseEntity.created(location).body(nuevoConsultorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConsultorioDto> eliminarConsultorio(@PathVariable Long id) {
        return consultorioService.findById(id)
                .map(c -> {
                    consultorioService.deleteConsultorio(id);
                    return ResponseEntity.ok().body(c);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultorioDto> actualizarConsultorio(@PathVariable Long id, @RequestBody ConsultorioDto consultorio) {
        return consultorioService.updateConsultorio(id, consultorio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
