package com.data.safehaven.dtos;

import com.data.safehaven.entities.Usuario;
import com.data.safehaven.entities.Rol;
import com.data.safehaven.services.RolService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "rol", target = "rol", qualifiedByName = "rolToRolDto")
    @Mapping(source = "fechaDeNacimiento", target = "fechaDeNacimiento")
    UsuarioDto toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "rol", target = "rol", qualifiedByName = "rolToRolDto")
    @Mapping(source = "fechaDeNacimiento", target = "fechaDeNacimiento")
    UsuarioDto toDTOWithoutId(Usuario usuario);

    @Mapping(source = "rol", target = "rol", qualifiedByName = "idToRol")
    Usuario toEntity(UsuarioDto usuarioDto, @Context RolService rolService);

    @Named("rolToRolDto")
    default UsuarioDto.RolDto mapRolToRolDto(Rol rol) {
        if (rol == null) {
            return null;
        }
        return new UsuarioDto.RolDto(rol.getId(), rol.getNombre());
    }

    @Named("idToRol")
    default Rol mapIdToRol(UsuarioDto.RolDto rolDto, @Context RolService rolService) {
        if (rolDto == null || rolDto.id() == null) {
            return null;
        }
        return rolService.findRoleById(rolDto.id());
    }

}
