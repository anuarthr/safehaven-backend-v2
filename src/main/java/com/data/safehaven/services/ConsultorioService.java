package com.data.safehaven.services;

import com.data.safehaven.dtos.ConsultorioDto;
import com.data.safehaven.dtos.ConsultorioMapper;
import com.data.safehaven.entities.Consultorio;
import com.data.safehaven.repositories.ConsultorioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultorioService implements ConsultorioServiceI {

    private final ConsultorioRepository consultorioRepository;
    private final ConsultorioMapper consultorioMapper;

    public ConsultorioService(ConsultorioRepository consultorioRepository, ConsultorioMapper consultorioMapper) {
        this.consultorioRepository = consultorioRepository;
        this.consultorioMapper = consultorioMapper;
    }

    @Override
    public List<ConsultorioDto> findAll() {
        return consultorioRepository.findAll().stream().map(consultorioMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultorioDto> findById(long id) {
        return consultorioRepository.findById(id).map(consultorioMapper::toDTO);
    }

    @Override
    public Optional<Consultorio> findConsultorioById(long id) {
        return consultorioRepository.findById(id);
    }

    @Override
    public Optional<ConsultorioDto> findByNombre(String nombre) {
        return consultorioRepository.findByNombre(nombre).map(consultorioMapper::toDTO);
    }

    @Override
    public ConsultorioDto saveConsultorio(ConsultorioDto consultorio) {
        Consultorio consultorioEntity = consultorioMapper.toEntity(consultorio);
        return consultorioMapper.toDTO(consultorioRepository.save(consultorioEntity));
    }

    @Override
    public void deleteConsultorio(long id) {
        consultorioRepository.deleteById(id);
    }

    @Override
    public Optional<ConsultorioDto> updateConsultorio(long id, ConsultorioDto consultorio) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return consultorioRepository.findById(id).map(oldConsultorio -> {
            oldConsultorio.setNombre(consultorio.nombre());
            oldConsultorio.setCapacidad(consultorio.capacidad());
            oldConsultorio.setTipo(consultorio.tipo());
            oldConsultorio.setHorarioDeApertura(consultorio.horarioDeApertura());
            oldConsultorio.setHorarioDeCierre(consultorio.horarioDeCierre());
            oldConsultorio.setActivo(consultorio.activo());
            return consultorioRepository.save(oldConsultorio);
        }).map(consultorioMapper::toDTO);
    }
}
