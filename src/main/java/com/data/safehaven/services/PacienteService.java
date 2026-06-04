package com.data.safehaven.services;

import com.data.safehaven.dtos.PacienteDto;
import com.data.safehaven.dtos.PacienteMapper;
import com.data.safehaven.dtos.RegistroPacienteDto;
import com.data.safehaven.entities.Paciente;
import com.data.safehaven.exceptions.EmailException;
import com.data.safehaven.repositories.PacienteRepository;
import com.data.safehaven.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService implements PacienteServiceI {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper,
                           RolService rolService, PasswordEncoder passwordEncoder,
                           UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDto> findAll() {
        return pacienteRepository.findAll().stream().map(pacienteMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDto> findById(long id) {
        return pacienteRepository.findById(id).map(pacienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findPacienteById(long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDto> findByNombre(String nombre) {
        return pacienteRepository.findByNombre(nombre).map(pacienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDto> findByCorreoElectronico(String correoElectronico) {
        return pacienteRepository.findByCorreoElectronico(correoElectronico).map(pacienteMapper::toDTO);
    }

    @Override
    public PacienteDto savePaciente(RegistroPacienteDto paciente) {
        if (usuarioRepository.existsByCorreoElectronico(paciente.correoElectronico())) {
            throw new EmailException(paciente.correoElectronico());
        }
        Paciente pacienteEntity = pacienteMapper.toEntity(paciente, rolService);
        pacienteEntity.setPassword(passwordEncoder.encode(paciente.password()));
        pacienteEntity.setFechaDeRegistro(LocalDate.now());
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
