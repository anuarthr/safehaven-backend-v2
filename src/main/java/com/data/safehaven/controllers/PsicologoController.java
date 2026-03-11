package com.data.safehaven.controllers;

import com.data.safehaven.dtos.PsicologoDto;
import com.data.safehaven.dtos.RegistroPsicologoDto;
import com.data.safehaven.services.PsicologoServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/psicologos")
public class PsicologoController {

    private final PsicologoServiceI psicologoService;

    public PsicologoController(PsicologoServiceI psicologoService) {
        this.psicologoService = psicologoService;
    }

    @GetMapping
    public ResponseEntity<List<PsicologoDto>> obtenerPsicologos() {
        return ResponseEntity.ok(psicologoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsicologoDto> obtenerPsicologoById(@PathVariable Long id) {
        return psicologoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PsicologoDto> crearPsicologo(@RequestBody RegistroPsicologoDto psicologo) {
        PsicologoDto nuevoPsicologo = psicologoService.savePsicologo(psicologo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoPsicologo.id()).toUri();
        return ResponseEntity.created(location).body(nuevoPsicologo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PsicologoDto> eliminarPsicologo(@PathVariable Long id) {
        return psicologoService.findById(id)
                .map(p -> {
                    psicologoService.deletePsicologo(id);
                    return ResponseEntity.ok().body(p);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PsicologoDto> actualizarPsicologo(@PathVariable Long id, @RequestBody PsicologoDto psicologo) {
        return psicologoService.updatePsicologo(id, psicologo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
