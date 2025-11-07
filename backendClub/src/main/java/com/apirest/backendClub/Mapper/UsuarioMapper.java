package com.apirest.backendClub.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;
import com.apirest.backendClub.Model.UsuariosModel;
@Component
public class UsuarioMapper {
    public UsuariosModel toModel(UsuarioCreateDTO dto){
        UsuariosModel model = new UsuariosModel();
        model.setNombreCompleto(dto.getNombreCompleto());
        model.setEdad(dto.getEdad());
        model.setOcupacion(dto.getOcupacion());
        model.setCorreo(dto.getCorreo());
        model.setTelefono(dto.getTelefono());
        model.setRol(dto.getRol());
        model.setFechaRegistro(dto.getFechaRegistro());
        return model;
    }
public UsuarioResponseDTO toResponseDTO(UsuariosModel model){
    UsuarioResponseDTO dto = new UsuarioResponseDTO();
    dto.setId(model.getIdAsString());
    dto.setNombreCompleto(model.getNombreCompleto());
    dto.setEdad(model.getEdad());
    dto.setOcupacion(model.getOcupacion());
    dto.setCorreo(model.getCorreo());
    dto.setTelefono(model.getTelefono());
    dto.setRol(model.getRol());
    dto.setFechaRegistro(model.getFechaRegistro());
    return dto;
}
public List<UsuarioResponseDTO> toResponseDTOList(List<UsuariosModel> models){
    return models.stream()
            .map(this::toResponseDTO)
            .toList();
}
}
