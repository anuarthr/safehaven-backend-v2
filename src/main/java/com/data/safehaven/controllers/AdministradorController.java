package com.data.safehaven.controllers;

import com.data.safehaven.dtos.AdministradorDto;
import com.data.safehaven.dtos.RegistroAdministradorDto;
import com.data.safehaven.services.AdministradorServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorServiceI administradorService;

    public AdministradorController(AdministradorServiceI administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public ResponseEntity<List<AdministradorDto>> obtenerAdministradores() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorDto> obtenerAdministradorById(@PathVariable Long id) {
        return administradorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdministradorDto> crearAdministrador(@RequestBody RegistroAdministradorDto administrador) {
        AdministradorDto nuevoAdministrador = administradorService.saveAdministrador(administrador);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoAdministrador.id()).toUri();
        return ResponseEntity.created(location).body(nuevoAdministrador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdministradorDto> eliminarAdministrador(@PathVariable Long id) {
        return administradorService.findById(id)
                .map(a -> {
                    administradorService.deleteAdministrador(id);
                    return ResponseEntity.ok().body(a);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorDto> actualizarAdministrador(@PathVariable Long id, @RequestBody AdministradorDto administrador) {
        return administradorService.updateAdministrador(id, administrador)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

