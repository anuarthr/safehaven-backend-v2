package com.data.safehaven.services;

import com.data.safehaven.dtos.CitaDto;
import com.data.safehaven.dtos.CitaMapper;
import com.data.safehaven.entities.Cita;
import com.data.safehaven.repositories.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService implements CitaServiceI {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;
    private final PacienteService pacienteService;
    private final PsicologoServiceI psicologoService;
    private final ConsultorioServiceI consultorioService;

    public CitaService(CitaRepository citaRepository, CitaMapper citaMapper, PacienteService pacienteService, PsicologoServiceI psicologoService, ConsultorioServiceI consultorioService) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
        this.pacienteService = pacienteService;
        this.psicologoService = psicologoService;
        this.consultorioService = consultorioService;
    }

    @Override
    public List<CitaDto> findAll() {
        return citaRepository.findAll().stream().map(citaMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CitaDto> findById(long id) {
        return citaRepository.findById(id).map(citaMapper::toDTO);
    }

    @Override
    public Optional<Cita> findCitaById(long id) {
        return citaRepository.findById(id);
    }

    @Override
    public CitaDto saveCita(CitaDto cita) {
        Cita citaEntity = citaMapper.toEntity(cita, pacienteService, consultorioService, psicologoService);
        return citaMapper.toDTO(citaRepository.save(citaEntity));
    }

    @Override
    public void deleteCita(long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public Optional<CitaDto> updateCita(long id, CitaDto cita) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido");
        }
        return citaRepository.findById(id).map(oldCita -> {
            oldCita.setPaciente(pacienteService.findPacienteById(cita.paciente()).orElse(null));
            oldCita.setPsicologo(psicologoService.findPsicologoById(cita.psicologo()).orElse(null));
            oldCita.setConsultorio(consultorioService.findConsultorioById(cita.consultorio()).orElse(null));
            oldCita.setFecha(cita.fecha());
            oldCita.setMotivo(cita.motivo());
            oldCita.setHora(cita.hora());
            oldCita.setDuracion(cita.duracion());
            oldCita.setTipoCita(cita.tipoCita());
            return citaRepository.save(oldCita);
        }).map(citaMapper::toDTO);
    }
}
