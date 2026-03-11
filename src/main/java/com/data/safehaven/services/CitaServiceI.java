package com.data.safehaven.services;
import com.data.safehaven.dtos.CitaDto;
import com.data.safehaven.entities.Cita;
import java.util.List;
import java.util.Optional;
public interface CitaServiceI {
    List<CitaDto> findAll();
    Optional<CitaDto> findById(long id);
    Optional<Cita> findCitaById(long id);
    CitaDto saveCita(CitaDto cita);
    void deleteCita(long id);
    Optional<CitaDto> updateCita(long id, CitaDto cita);
}