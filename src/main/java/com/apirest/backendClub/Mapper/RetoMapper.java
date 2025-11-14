package com.apirest.backendClub.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.LibroAsociadoDTO;
import com.apirest.backendClub.DTO.ParticipanteDTO;
import com.apirest.backendClub.DTO.ProgresoDTO;
import com.apirest.backendClub.DTO.RetoCreateDTO;
import com.apirest.backendClub.DTO.RetoResponseDTO;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.LibroAsociado;
import com.apirest.backendClub.Model.Retosembb.Participante;
import com.apirest.backendClub.Model.Retosembb.Progreso;

@Component
public class RetoMapper {
    
    // Convertir de DTO a Model (para crear/insertar)
    public RetosModel toModel(RetoCreateDTO dto) {
        RetosModel model = new RetosModel();
        model.setTitulo(dto.getTitulo());
        model.setDescripcion(dto.getDescripcion());
        model.setFechaInicio(dto.getFechaInicio());
        model.setFechaFinalizacion(dto.getFechaFinalizacion());
        
        // Convertir lista de libros asociados
        if (dto.getListaLibrosAsociados() != null) {
            List<LibroAsociado> libros = dto.getListaLibrosAsociados().stream()
                .map(this::toLibroAsociadoModel)
                .collect(Collectors.toList());
            model.setListaLibrosAsociados(libros);
        } else {
            model.setListaLibrosAsociados(new ArrayList<>());
        }
        
        // Inicializar lista de participantes vacía
        model.setParticipantes(new ArrayList<>());
        
        return model;
    }
    
    // Convertir de Model a ResponseDTO (para respuestas)
    public RetoResponseDTO toResponseDTO(RetosModel model) {
        RetoResponseDTO dto = new RetoResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setTitulo(model.getTitulo());
        dto.setDescripcion(model.getDescripcion());
        dto.setFechaInicio(model.getFechaInicio());
        dto.setFechaFinalizacion(model.getFechaFinalizacion());
        
        // Convertir lista de libros asociados
        if (model.getListaLibrosAsociados() != null) {
            List<LibroAsociadoDTO> libros = model.getListaLibrosAsociados().stream()
                .map(this::toLibroAsociadoDTO)
                .collect(Collectors.toList());
            dto.setListaLibrosAsociados(libros);
        }
        
        // Convertir lista de participantes
        if (model.getParticipantes() != null) {
            List<ParticipanteDTO> participantes = model.getParticipantes().stream()
                .map(this::toParticipanteDTO)
                .collect(Collectors.toList());
            dto.setParticipantes(participantes);
        }
        
        return dto;
    }
    
    // Convertir lista de Models a lista de ResponseDTOs
    public List<RetoResponseDTO> toResponseDTOList(List<RetosModel> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        return models.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    // ========== MÉTODOS PRIVADOS AUXILIARES ==========
    
    // Convertir LibroAsociadoDTO a LibroAsociado (Model)
    private LibroAsociado toLibroAsociadoModel(LibroAsociadoDTO dto) {
        LibroAsociado model = new LibroAsociado();
        model.setLibroId(new ObjectId(dto.getLibroId()));
        
        // Verificar si el DTO tiene el campo nombreLibro o porcentaje
        // Según tu código, parece que LibroAsociadoDTO tiene "porcentaje" en lugar de "nombreLibro"
        // Ajustaremos esto basándonos en tu estructura
        if (dto.getNombreLibro() != null) {
            model.setNombreLibro(dto.getNombreLibro());
        } else {
            model.setNombreLibro(""); // Valor por defecto
        }
        
        return model;
    }
    
    // Convertir LibroAsociado (Model) a LibroAsociadoDTO
    private LibroAsociadoDTO toLibroAsociadoDTO(LibroAsociado model) {
        LibroAsociadoDTO dto = new LibroAsociadoDTO();
        dto.setLibroId(model.getLibroId().toHexString());
        dto.setNombreLibro(model.getNombreLibro());
        return dto;
    }
    
    // Convertir Participante (Model) a ParticipanteDTO
    private ParticipanteDTO toParticipanteDTO(Participante model) {
        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setUsuarioId(model.getUsuarioId().toHexString());
        
        // Convertir lista de progresos
        if (model.getProgreso() != null) {
            List<ProgresoDTO> progresos = model.getProgreso().stream()
                .map(this::toProgresoDTO)
                .collect(Collectors.toList());
            dto.setProgreso(progresos);
        }
        
        return dto;
    }
    
    // Convertir Progreso (Model) a ProgresoDTO
    private ProgresoDTO toProgresoDTO(Progreso model) {
        ProgresoDTO dto = new ProgresoDTO();
        dto.setLibroId(model.getLibroId().toHexString());
        dto.setPorcentaje(model.getPorcentaje());
        dto.setEstado(model.getEstado());
        return dto;
    }
}