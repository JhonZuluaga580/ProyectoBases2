package com.apirest.backendClub.Mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.*;

@Component
public class RetoMapper {
    
    public RetosModel toModel(RetoCreateDTO dto) {
        RetosModel model = new RetosModel();
        model.setTitulo(dto.getTitulo());
        model.setDescripcion(dto.getDescripcion());
        model.setFechaInicio(dto.getFechaInicio());
        model.setFechaFinalizacion(dto.getFechaFinalizacion());
        
        if (dto.getListaLibrosAsociados() != null) {
            model.setListaLibrosAsociados(
                dto.getListaLibrosAsociados().stream()
                    .map(l -> new LibroAsociado(new ObjectId(l.getLibroId()), l.getNombreLibro()))
                    .collect(Collectors.toList())
            );
        }
        
        return model;
    }
    
    public RetoResponseDTO toResponseDTO(RetosModel model) {
        RetoResponseDTO dto = new RetoResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setTitulo(model.getTitulo());
        dto.setDescripcion(model.getDescripcion());
        dto.setFechaInicio(model.getFechaInicio());
        dto.setFechaFinalizacion(model.getFechaFinalizacion());
        
        if (model.getListaLibrosAsociados() != null) {
            dto.setListaLibrosAsociados(
                model.getListaLibrosAsociados().stream()
                    .map(l -> new LibroAsociadoDTO(l.getLibroId().toHexString(), l.getNombreLibro()))
                    .collect(Collectors.toList())
            );
        }
        
        if (model.getParticipantes() != null) {
            dto.setParticipantes(
                model.getParticipantes().stream()
                    .map(p -> {
                        ParticipanteDTO pDto = new ParticipanteDTO();
                        pDto.setUsuarioId(p.getUsuarioId().toHexString());
                        if (p.getProgreso() != null) {
                            pDto.setProgreso(
                                p.getProgreso().stream()
                                    .map(pr -> new ProgresoDTO(
                                        pr.getLibroId().toHexString(),
                                        pr.getPorcentaje(),
                                        pr.getEstado()
                                    ))
                                    .collect(Collectors.toList())
                            );
                        }
                        return pDto;
                    })
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    public List<RetoResponseDTO> toResponseDTOList(List<RetosModel> models) {
        return models.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}