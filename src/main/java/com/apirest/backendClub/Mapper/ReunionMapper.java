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
    
    // DTO a Model (para crear)
    public ReunionesModel toModel(ReunionCreateDTO dto) {
        ReunionesModel model = new ReunionesModel();
        model.setDateTime(dto.getDateTime());
        
        // Modalidad
        Modalidad modalidad = new Modalidad();
        modalidad.setTipo(dto.getModalidad().getTipo());
        modalidad.setUbicacion(dto.getModalidad().getUbicacion());
        modalidad.setEnlace(dto.getModalidad().getEnlace());
        model.setModalidad(modalidad);
        
        // Libros a discutir
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
    
    // Model a ResponseDTO (para respuestas)
    public ReunionResponseDTO toResponseDTO(ReunionesModel model) {
        ReunionResponseDTO dto = new ReunionResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setDateTime(model.getDateTime());
        
        // Modalidad
        if (model.getModalidad() != null) {
            dto.setModalidad(new ModalidadDTO(
                model.getModalidad().getTipo(),
                model.getModalidad().getUbicacion(),
                model.getModalidad().getEnlace()
            ));
        }
        
        // Invitados
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
        
        // Libros
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
    
    // Convertir lista
    public List<ReunionResponseDTO> toResponseDTOList(List<ReunionesModel> models) {
        return models.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    
    // Helper para crear invitado (usado en el servicio)
    public InvitadoReunion createInvitado(ObjectId usuarioId, String nombreUsuario) {
        return new InvitadoReunion(usuarioId, nombreUsuario);
    }
}