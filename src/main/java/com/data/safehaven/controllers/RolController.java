package com.data.safehaven.controllers;

import com.data.safehaven.dtos.RolDto;
import com.data.safehaven.services.RolServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolServiceI rolService;

    public RolController(RolServiceI rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> obtenerRoles() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> obtenerRolById(@PathVariable Long id) {
        return rolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolDto> crearRol(@RequestBody RolDto rol) {
        RolDto nuevoRol = rolService.saveRol(rol);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nuevoRol.id()).toUri();
        return ResponseEntity.created(location).body(nuevoRol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RolDto> eliminarRol(@PathVariable Long id) {
        return rolService.findById(id)
                .map(r -> {
                    rolService.deleteRol(id);
                    return ResponseEntity.ok().body(r);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> actualizarRol(@PathVariable Long id, @RequestBody RolDto rol) {
        return rolService.updateRol(id, rol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
