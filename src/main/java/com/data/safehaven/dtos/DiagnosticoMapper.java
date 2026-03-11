package com.data.safehaven.dtos;

import com.data.safehaven.entities.Diagnostico;
import com.data.safehaven.entities.Paciente;
import com.data.safehaven.entities.Psicologo;
import com.data.safehaven.services.PacienteService;
import com.data.safehaven.services.PsicologoServiceI;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DiagnosticoMapper {

    @Mapping(source = "paciente", target = "paciente", qualifiedByName = "idToPaciente")
    @Mapping(source = "psicologo", target = "psicologo", qualifiedByName = "idToPsicologo")
    Diagnostico toEntity(DiagnosticoDto diagnosticoDto,
                         @Context PacienteService pacienteService,
                         @Context PsicologoServiceI psicologoService);

    @Mapping(source = "paciente.id", target = "paciente")
    @Mapping(source = "psicologo.id", target = "psicologo")
    DiagnosticoDto toDTO(Diagnostico diagnostico);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "paciente.id", target = "paciente")
    @Mapping(source = "psicologo.id", target = "psicologo")
    DiagnosticoDto toDTOWithoutId(Diagnostico diagnostico);

    @Named("idToPaciente")
    default Paciente mapIdToPaciente(Long id, @Context PacienteService pacienteService) {
        return id != null ? pacienteService.findPacienteById(id).orElse(null) : null;
    }

    @Named("idToPsicologo")
    default Psicologo mapIdToPsicologo(Long id, @Context PsicologoServiceI psicologoService) {
        return id != null ? psicologoService.findPsicologoById(id).orElse(null) : null;
    }
}