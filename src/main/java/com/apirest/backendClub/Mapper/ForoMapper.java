package com.apirest.backendClub.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
import com.apirest.backendClub.DTO.ModeradorDTO;
import com.apirest.backendClub.Model.ForosModel;
import com.apirest.backendClub.Model.ModeradorForos;

@Component
public class ForoMapper {

    public ForosModel toModel(ForoCreateDTO dto){
        ForosModel foroM = new ForosModel();
        foroM.setTitulo(dto.getTitulo());
        foroM.setCategoria(dto.getCategoria());
        foroM.setDescripcion(dto.getDescripcion());
        foroM.setModerador(new ModeradorForos());

        return foroM;
    }
    public  ForoResponseDTO toResponseDTO(ForosModel model){
        ForoResponseDTO dto = new ForoResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setTitulo(model.getTitulo());
        dto.setCategoria(model.getCategoria());
        dto.setDescripcion(model.getDescripcion());
        ModeradorDTO moderador = null;
        if(model.getModerador()!= null){
            moderador = new ModeradorDTO(
            model.getModerador().getUsuarioId()!= null ? model.getModerador().getUsuarioId().toHexString() : null,
            model.getModerador().getNombreCompleto()
            );
    }
        dto.setModerador(moderador);
        dto.setFechaPublicacion(model.getFechaPublicacion());
        dto.setEstado(model.getEstado());
        
        return dto;
    }
    public List<ForoResponseDTO> toResponseDTOList(List<ForosModel> models){
        return models .stream()
        .map(this::toResponseDTO)
        .toList();
    }
    
}
