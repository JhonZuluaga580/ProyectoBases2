package com.apirest.backendClub.Mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.*;

@Component
public class LibroPropuestoMapper {
    
    // Model a ResponseDTO
    public LibroPropuestoResponseDTO toResponseDTO(LibrosPropuestosModel model) {
        LibroPropuestoResponseDTO dto = new LibroPropuestoResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setFecha(model.getFecha());
        dto.setEstado(model.getEstado());
        
        if (model.getLibroPropuesto() != null) {
            dto.setLibroPropuesto(new LibroPropuestoDTO(
                model.getLibroPropuesto().getLibroId().toHexString(),
                model.getLibroPropuesto().getTitulo(),
                model.getLibroPropuesto().getGenero()
            ));
        }
        
        if (model.getPropuestoPor() != null) {
            dto.setPropuestoPor(new PropuestoPorDTO(
                model.getPropuestoPor().getUsuarioId().toHexString(),
                model.getPropuestoPor().getNombreCompleto()
            ));
        }
        
        if (model.getVotacion() != null) {
            dto.setVotacion(
                model.getVotacion().stream()
                    .map(v -> new VotacionDTO(
                        v.getUsuarioId().toHexString(),
                        v.getNombreCompleto(),
                        v.getFechaVotacion(),
                        v.getVoto()
                    ))
                    .collect(Collectors.toList())
            );
        }
        
        EstadisticasVotacionDTO stats = new EstadisticasVotacionDTO();
        stats.setTotalVotos(model.getVotacion() != null ? model.getVotacion().size() : 0);
        stats.setVotosAFavor(model.contarVotosAFavor());
        stats.setVotosEnContra(model.contarVotosEnContra());
        stats.setVotosNeutros(model.contarVotosNeutros());
        if (stats.getTotalVotos() > 0) {
            stats.setPorcentajeAFavor((stats.getVotosAFavor() * 100.0) / stats.getTotalVotos());
        }
        dto.setEstadisticas(stats);
        
        return dto;
    }
    
    public List<LibroPropuestoResponseDTO> toResponseDTOList(List<LibrosPropuestosModel> models) {
        return models.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    
    public LibroPropuesto createLibroPropuesto(ObjectId libroId, String titulo, String genero) {
        return new LibroPropuesto(libroId, titulo, genero);
    }
    
    public PropuestoPor createPropuestoPor(ObjectId usuarioId, String nombreCompleto) {
        return new PropuestoPor(usuarioId, nombreCompleto);
    }
    
    public Votacion createVotacion(ObjectId usuarioId, String nombreCompleto, String voto) {
        Votacion v = new Votacion();
        v.setUsuarioId(usuarioId);
        v.setNombreCompleto(nombreCompleto);
        v.setFechaVotacion(java.time.LocalDateTime.now());
        v.setVoto(voto);
        return v;
    }
}