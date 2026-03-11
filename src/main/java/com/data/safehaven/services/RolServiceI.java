package com.data.safehaven.services;
import com.data.safehaven.dtos.RolDto;
import com.data.safehaven.entities.Rol;
import java.util.List;
import java.util.Optional;
public interface RolServiceI {
    List<RolDto> findAll();
    Optional<RolDto> findById(long id);
    Rol findRoleById(long id);
    Optional<RolDto> findByNombre(String nombre);
    RolDto saveRol(RolDto rol);
    void deleteRol(long id);
    Optional<RolDto> updateRol(long id, RolDto rol);
}