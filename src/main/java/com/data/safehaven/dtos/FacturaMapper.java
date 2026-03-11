package com.data.safehaven.dtos;

import com.data.safehaven.entities.Cita;
import com.data.safehaven.entities.Factura;
import com.data.safehaven.entities.Paciente;
import com.data.safehaven.services.CitaServiceI;
import com.data.safehaven.services.PacienteService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    @Mapping(source = "cita", target = "cita", qualifiedByName = "idToCita")
    @Mapping(source = "paciente", target = "paciente", qualifiedByName = "idToPaciente")
    Factura toEntity(FacturaDto facturaDto,
                     @Context CitaServiceI citaService,
                     @Context PacienteService pacienteService);

    @Mapping(source = "cita.id", target = "cita")
    @Mapping(source = "paciente.id", target = "paciente")
    FacturaDto toDTO(Factura factura);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cita.id", target = "cita")
    @Mapping(source = "paciente.id", target = "paciente")
    FacturaDto toDTOWithoutId(Factura factura);

    @Named("idToCita")
    default Cita mapIdToCita(Long id, @Context CitaServiceI citaService) {
        return id != null ? citaService.findCitaById(id).orElse(null) : null;
    }

    @Named("idToPaciente")
    default Paciente mapIdToPaciente(Long id, @Context PacienteService pacienteService) {
        return id != null ? pacienteService.findPacienteById(id).orElse(null) : null;
    }
}
