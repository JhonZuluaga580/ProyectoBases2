package com.apirest.backendClub.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.LibroCreateDTO;
import com.apirest.backendClub.DTO.LibroResponseDTO;
import com.apirest.backendClub.Model.LibrosModel;


@Component

public class LibroMapper {
    

    public LibrosModel toModel(LibroCreateDTO dto){
        LibrosModel model = new LibrosModel();
        model.setTitulo(dto.getTitulo());
        model.setAutor(dto.getAutor());
        model.setGenero(dto.getGenero());
        model.setAnioPublicacion(dto.getAnioPublicacion());
        model.setSinopsis(dto.getSinopsis());
        model.setPortada(dto.getPortada());
        model.setEstadoLectura(dto.getEstadoLectura());
        model.setFechaSeleccion(dto.getFechaSeleccion());
        
        return model;
    }
public LibroResponseDTO toResponseDTO(LibrosModel model){
    LibroResponseDTO dto = new LibroResponseDTO();
    dto.setId(model.getIdAsString());
    dto.setTitulo(model.getTitulo());
    dto.setAutor(model.getAutor());
    dto.setGenero(model.getGenero());
    dto.setAnioPublicacion(model.getAnioPublicacion());
    dto.setSinopsis(model.getSinopsis());
    dto.setPortada(model.getPortada());
    dto.setEstadoLectura(model.getEstadoLectura());
    dto.setFechaSeleccion(model.getFechaSeleccion());
    return dto;
}
public List<LibroResponseDTO> toResponseDTOList(List<LibrosModel> models){
    return models.stream()
            .map(this::toResponseDTO)
            .toList();
}

}
