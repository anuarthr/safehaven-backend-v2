package com.data.safehaven.services;
import com.data.safehaven.dtos.PsicologoDto;
import com.data.safehaven.dtos.RegistroPsicologoDto;
import com.data.safehaven.entities.Psicologo;
import java.util.List;
import java.util.Optional;
public interface PsicologoServiceI {
    List<PsicologoDto> findAll();
    Optional<PsicologoDto> findById(long id);
    Optional<Psicologo> findPsicologoById(long id);
    Optional<PsicologoDto> findByNombre(String nombre);
    PsicologoDto savePsicologo(RegistroPsicologoDto psicologo);
    void deletePsicologo(long id);
    Optional<PsicologoDto> updatePsicologo(long id, PsicologoDto psicologo);
}