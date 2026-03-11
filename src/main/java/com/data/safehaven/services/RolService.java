package com.data.safehaven.services;

import com.data.safehaven.dtos.RolDto;
import com.data.safehaven.dtos.RolMapper;
import com.data.safehaven.entities.Rol;
import com.data.safehaven.repositories.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolService implements RolServiceI {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolService(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public List<RolDto> findAll() {
        return rolRepository.findAll().stream().map(rolMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<RolDto> findById(long id) {
        return rolRepository.findById(id).map(rolMapper::toDTO);
    }

    @Override
    public Rol findRoleById(long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }

    @Override
    public Optional<RolDto> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre).map(rolMapper::toDTO);
    }

    @Override
    public RolDto saveRol(RolDto rol) {
        Rol rolEntity = rolMapper.toEntity(rol);
        return rolMapper.toDTO(rolRepository.save(rolEntity));
    }

    @Override
    public void deleteRol(long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<RolDto> updateRol(long id, RolDto rol) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return rolRepository.findById(id).map(oldRol -> {
            oldRol.setNombre(rol.nombre());
            oldRol.setDescripcion(rol.descripcion());
            return rolRepository.save(oldRol);
        }).map(rolMapper::toDTO);
    }
}


