package com.data.safehaven.dtos;

import com.data.safehaven.entities.Cita;
import com.data.safehaven.entities.Consultorio;
import com.data.safehaven.entities.Paciente;
import com.data.safehaven.entities.Psicologo;
import com.data.safehaven.services.ConsultorioServiceI;
import com.data.safehaven.services.PacienteServiceI;
import com.data.safehaven.services.PsicologoServiceI;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CitaMapper {

    @Mapping(source = "paciente", target = "paciente", qualifiedByName = "idToPaciente")
    @Mapping(source = "psicologo", target = "psicologo", qualifiedByName = "idToPsicologo")
    @Mapping(source = "consultorio", target = "consultorio", qualifiedByName = "idToConsultorio")
    Cita toEntity(CitaDto citaDto, @Context PacienteServiceI pacienteService, @Context ConsultorioServiceI consultorioService, @Context PsicologoServiceI psicologoService);

    @Mapping(source = "paciente.id", target = "paciente")
    @Mapping(source = "psicologo.id", target = "psicologo")
    @Mapping(source = "consultorio.id", target = "consultorio")
    CitaDto toDTO(Cita cita);

    @Mapping(source = "paciente.id", target = "paciente")
    @Mapping(source = "psicologo.id", target = "psicologo")
    @Mapping(source = "consultorio.id", target = "consultorio")
    @Mapping(target = "id", ignore = true)
    CitaDto toDTOWithoutId(Cita cita);

    @Named("idToPaciente")
    default Paciente mapIdToPaciente(Long id, @Context PacienteServiceI pacienteService) {
        return id != null ? pacienteService.findPacienteById(id).orElse(null) : null;
    }

    @Named("idToPsicologo")
    default Psicologo mapIdToPsicologo(Long id, @Context PsicologoServiceI psicologoService) {
        return id != null ? psicologoService.findPsicologoById(id).orElse(null) : null;
    }

    @Named("idToConsultorio")
    default Consultorio mapIdToConsultorio(Long id, @Context ConsultorioServiceI consultorioService) {
        return id != null ? consultorioService.findConsultorioById(id).orElse(null) : null;
    }
}

