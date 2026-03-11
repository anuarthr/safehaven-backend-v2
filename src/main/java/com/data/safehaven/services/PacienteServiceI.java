package com.data.safehaven.services;


import com.data.safehaven.dtos.PacienteDto;
import com.data.safehaven.dtos.PacienteMapper;
import com.data.safehaven.dtos.RegistroPacienteDto;
import com.data.safehaven.entities.Paciente;
import com.data.safehaven.exceptions.EmailException;
import com.data.safehaven.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceI implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final RolService rolService;

    public PacienteServiceI(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper, RolService rolService) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.rolService = rolService;
    }

    @Override
    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream().map(pacienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDto> findById(long id) {
        return pacienteRepository.findById(id).map(pacienteMapper::toDTO);
    }

    @Override
    public Optional<Paciente> findPacienteById(long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Optional<PacienteDto> findByNombre(String nombre) {
        return pacienteRepository.findByNombre(nombre).map(pacienteMapper::toDTO);
    }

    @Override
    public Optional<PacienteDto> findByCorreoElectronico(String correoElectronico) {
        return pacienteRepository.findByCorreoElectronico(correoElectronico).map(pacienteMapper::toDTO);
    }

    @Override
    public PacienteDto savePaciente(RegistroPacienteDto paciente) {
        if (pacienteRepository.findByCorreoElectronico(paciente.correoElectronico()).isPresent()) {
            throw new EmailException(paciente.correoElectronico());
        }
        Paciente pacienteEntity = pacienteMapper.toEntity(paciente, rolService);
        pacienteEntity.setFechaDeRegistro(new Date());
        return pacienteMapper.toDTO(pacienteRepository.save(pacienteEntity));
    }

    @Override
    public void deletePaciente(long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Optional<PacienteDto> updatePaciente(long id, PacienteDto paciente) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return pacienteRepository.findById(id).map(oldPaciente -> {
            oldPaciente.setNombre(paciente.nombre());
            oldPaciente.setApellido(paciente.apellido());
            oldPaciente.setCorreoElectronico(paciente.correoElectronico());
            oldPaciente.setEdad(paciente.edad());
            oldPaciente.setTelefono(paciente.telefono());
            oldPaciente.setSexo(paciente.sexo());
            oldPaciente.setFechaDeNacimiento(paciente.fechaDeNacimiento());
            oldPaciente.setAseguradora(paciente.aseguradora());
            oldPaciente.setEstadoDeSalud(paciente.estadoDeSalud());
            return pacienteRepository.save(oldPaciente);
        }).map(pacienteMapper::toDTO);
    }
}
