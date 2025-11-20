package com.apirest.backendClub.Mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.Reunionesembb.*;

@Component
public class ReunionMapper {
    
    public ReunionesModel toModel(ReunionCreateDTO dto) {
        ReunionesModel model = new ReunionesModel();
        model.setDateTime(dto.getDateTime());
        
        Modalidad modalidad = new Modalidad();
        modalidad.setTipo(dto.getModalidad().getTipo());
        modalidad.setUbicacion(dto.getModalidad().getUbicacion());
        modalidad.setEnlace(dto.getModalidad().getEnlace());
        model.setModalidad(modalidad);
        
        if (dto.getLibroDiscutir() != null) {
            model.setLibroDiscutir(
                dto.getLibroDiscutir().stream()
                    .map(l -> new LibroDiscutir(
                        new ObjectId(l.getLibroId()),
                        l.getNombreLibro(),
                        l.getArchivosAdjuntos()
                    ))
                    .collect(Collectors.toList())
            );
        }
        
        return model;
    }
    
    public ReunionResponseDTO toResponseDTO(ReunionesModel model) {
        ReunionResponseDTO dto = new ReunionResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setDateTime(model.getDateTime());
        
        if (model.getModalidad() != null) {
            dto.setModalidad(new ModalidadDTO(
                model.getModalidad().getTipo(),
                model.getModalidad().getUbicacion(),
                model.getModalidad().getEnlace()
            ));
        }
        
        if (model.getListaInvitados() != null) {
            dto.setListaInvitados(
                model.getListaInvitados().stream()
                    .map(i -> new InvitadoReunionDTO(
                        i.getUsuarioId().toHexString(),
                        i.getNombreUsuario()
                    ))
                    .collect(Collectors.toList())
            );
        }
        
        if (model.getLibroDiscutir() != null) {
            dto.setLibroDiscutir(
                model.getLibroDiscutir().stream()
                    .map(l -> new LibroDiscutirDTO(
                        l.getLibroId().toHexString(),
                        l.getNombreLibro(),
                        l.getArchivosAdjuntos()
                    ))
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    public List<ReunionResponseDTO> toResponseDTOList(List<ReunionesModel> models) {
        return models.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    
    public InvitadoReunion createInvitado(ObjectId usuarioId, String nombreUsuario) {
        return new InvitadoReunion(usuarioId, nombreUsuario);
    }
}