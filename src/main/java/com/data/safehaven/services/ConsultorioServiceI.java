package com.data.safehaven.services;
import com.data.safehaven.dtos.ConsultorioDto;
import com.data.safehaven.entities.Consultorio;
import java.util.List;
import java.util.Optional;
public interface ConsultorioServiceI {
    List<ConsultorioDto> findAll();
    Optional<ConsultorioDto> findById(long id);
    Optional<Consultorio> findConsultorioById(long id);
    Optional<ConsultorioDto> findByNombre(String nombre);
    ConsultorioDto saveConsultorio(ConsultorioDto consultorio);
    void deleteConsultorio(long id);
    Optional<ConsultorioDto> updateConsultorio(long id, ConsultorioDto consultorio);
}