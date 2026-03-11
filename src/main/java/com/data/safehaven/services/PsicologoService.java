package com.data.safehaven.services;


import com.data.safehaven.dtos.PsicologoDto;
import com.data.safehaven.dtos.PsicologoMapper;
import com.data.safehaven.dtos.RegistroPsicologoDto;
import com.data.safehaven.entities.Psicologo;
import com.data.safehaven.repositories.PsicologoRepository;
import org.springframework.stereotype.Service;import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PsicologoService implements PsicologoServiceI {

    private final PsicologoRepository psicologoRepository;
    private final PsicologoMapper psicologoMapper;
    private final RolService rolService;

    public PsicologoService(PsicologoRepository psicologoRepository, PsicologoMapper psicologoMapper, RolService rolService) {
        this.psicologoRepository = psicologoRepository;
        this.psicologoMapper = psicologoMapper;
        this.rolService = rolService;
    }

    @Override
    public List<PsicologoDto> findAll() {
        return psicologoRepository.findAll().stream().map(psicologoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<PsicologoDto> findById(long id) {
        return psicologoRepository.findById(id).map(psicologoMapper::toDTO);
    }

    @Override
    public Optional<Psicologo> findPsicologoById(long id) {
        return psicologoRepository.findById(id);
    }

    @Override
    public Optional<PsicologoDto> findByNombre(String nombre) {
        return psicologoRepository.findByNombre(nombre).map(psicologoMapper::toDTO);
    }

    @Override
    public PsicologoDto savePsicologo(RegistroPsicologoDto psicologo) {
        Psicologo psicologoEntity = psicologoMapper.toEntity(psicologo, rolService);
        return psicologoMapper.toDTO(psicologoRepository.save(psicologoEntity));
    }

    @Override
    public void deletePsicologo(long id) {
        psicologoRepository.deleteById(id);
    }

    @Override
    public Optional<PsicologoDto> updatePsicologo(long id, PsicologoDto psicologo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return psicologoRepository.findById(id).map(oldPsicologo -> {
            oldPsicologo.setNombre(psicologo.nombre());
            oldPsicologo.setApellido(psicologo.apellido());
            oldPsicologo.setRol(rolService.findRoleById(psicologo.rol()));
            oldPsicologo.setCorreoElectronico(psicologo.correoElectronico());
            oldPsicologo.setEdad(psicologo.edad());
            oldPsicologo.setTelefono(psicologo.telefono());
            oldPsicologo.setSexo(psicologo.sexo());
            oldPsicologo.setFechaDeNacimiento(psicologo.fechaDeNacimiento());
            oldPsicologo.setAnosDeExperiencia(psicologo.anosDeExperiencia());
            oldPsicologo.setEspecialidad(psicologo.especialidad());
            oldPsicologo.setHorarioDeAtencion(psicologo.horarioDeAtencion());
            return psicologoRepository.save(oldPsicologo);
        }).map(psicologoMapper::toDTO);
    }
}