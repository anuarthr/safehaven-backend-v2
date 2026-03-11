package com.data.safehaven.services;
import com.data.safehaven.dtos.AdministradorDto;
import com.data.safehaven.dtos.RegistroAdministradorDto;
import java.util.List;
import java.util.Optional;
public interface AdministradorServiceI {
    List<AdministradorDto> findAll();
    Optional<AdministradorDto> findById(long id);
    Optional<AdministradorDto> findByNombre(String nombre);
    AdministradorDto saveAdministrador(RegistroAdministradorDto administrador);
    void deleteAdministrador(long id);
    Optional<AdministradorDto> updateAdministrador(long id, AdministradorDto administrador);
}