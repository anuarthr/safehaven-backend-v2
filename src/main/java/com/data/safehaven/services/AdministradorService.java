package com.data.safehaven.services;

import com.data.safehaven.dtos.AdministradorDto;
import com.data.safehaven.dtos.AdministradorMapper;
import com.data.safehaven.dtos.RegistroAdministradorDto;
import com.data.safehaven.entities.Administrador;
import com.data.safehaven.repositories.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministradorService implements AdministradorServiceI {

    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;
    private final RolService rolService;

    public AdministradorService(AdministradorRepository administradorRepository, AdministradorMapper administradorMapper, RolService rolService) {
        this.administradorRepository = administradorRepository;
        this.administradorMapper = administradorMapper;
        this.rolService = rolService;
    }

    @Override
    public List<AdministradorDto> findAll() {
        return administradorRepository.findAll().stream().map(administradorMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AdministradorDto> findById(long id) {
        return administradorRepository.findById(id).map(administradorMapper::toDTO);
    }

    @Override
    public Optional<AdministradorDto> findByNombre(String nombre) {
        return administradorRepository.findByNombre(nombre).map(administradorMapper::toDTO);
    }

    @Override
    public AdministradorDto saveAdministrador(RegistroAdministradorDto administrador) {
        Administrador administradorEntity = administradorMapper.toEntity(administrador, rolService);
        return administradorMapper.toDTO(administradorRepository.save(administradorEntity));
    }

    @Override
    public void deleteAdministrador(long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public Optional<AdministradorDto> updateAdministrador(long id, AdministradorDto administrador) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return administradorRepository.findById(id).map(oldAdministrador -> {
            oldAdministrador.setNombre(administrador.nombre());
            oldAdministrador.setApellido(administrador.apellido());
            oldAdministrador.setTelefono(administrador.telefono());
            oldAdministrador.setEdad(administrador.edad());
            oldAdministrador.setRol(rolService.findRoleById(administrador.rol()));
            oldAdministrador.setCargo(administrador.cargo());
            oldAdministrador.setCorreoElectronico(administrador.correoElectronico());
            oldAdministrador.setFechaDeNacimiento(administrador.fechaDeNacimiento());
            oldAdministrador.setSexo(administrador.sexo());
            return administradorRepository.save(oldAdministrador);
        }).map(administradorMapper::toDTO);
    }
}


