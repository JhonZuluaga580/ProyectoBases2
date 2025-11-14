package com.apirest.backendClub.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.InvitadoReunionDTO;
import com.apirest.backendClub.DTO.LibroDiscutirDTO;
import com.apirest.backendClub.DTO.ModalidadDTO;
import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;
import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.Reunionesembb.InvitadoReunion;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;
import com.apirest.backendClub.Model.Reunionesembb.Modalidad;

/**
 * Mapper para convertir entre DTOs y Models de Reuniones.
 * Facilita la transformación de datos entre las capas de la aplicación.
 */
@Component
public class ReunionMapper {
    
    /**
     * Convierte un CreateDTO a Model (para crear nuevas reuniones)
     * Los nombres de invitados se rellenarán en el servicio consultando la BD
     * 
     * @param dto DTO con los datos de entrada
     * @return Modelo de reunión
     */
    public ReunionesModel toModel(ReunionCreateDTO dto) {
        ReunionesModel model = new ReunionesModel();
        model.setDateTime(dto.getDateTime());
        model.setModalidad(toModalidadModel(dto.getModalidad()));
        
        // Inicializar listas vacías - se llenarán en el servicio
        model.setListaInvitados(new ArrayList<>());
        
        // Convertir libros a discutir
        if (dto.getLibroDiscutir() != null) {
            List<LibroDiscutir> libros = dto.getLibroDiscutir().stream()
                .map(this::toLibroDiscutirModel)
                .collect(Collectors.toList());
            model.setLibroDiscutir(libros);
        } else {
            model.setLibroDiscutir(new ArrayList<>());
        }
        
        return model;
    }
    
    /**
     * Convierte un Model a ResponseDTO (para respuestas al cliente)
     * 
     * @param model Modelo de reunión
     * @return DTO de respuesta
     */
    public ReunionResponseDTO toResponseDTO(ReunionesModel model) {
        ReunionResponseDTO dto = new ReunionResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setDateTime(model.getDateTime());
        dto.setModalidad(toModalidadDTO(model.getModalidad()));
        
        // Convertir lista de invitados
        if (model.getListaInvitados() != null) {
            List<InvitadoReunionDTO> invitados = model.getListaInvitados().stream()
                .map(this::toInvitadoReunionDTO)
                .collect(Collectors.toList());
            dto.setListaInvitados(invitados);
        }
        
        // Convertir libros a discutir
        if (model.getLibroDiscutir() != null) {
            List<LibroDiscutirDTO> libros = model.getLibroDiscutir().stream()
                .map(this::toLibroDiscutirDTO)
                .collect(Collectors.toList());
            dto.setLibroDiscutir(libros);
        }
        
        return dto;
    }
    
    /**
     * Convierte una lista de Models a lista de ResponseDTOs
     * 
     * @param models Lista de modelos de reuniones
     * @return Lista de DTOs de respuesta
     */
    public List<ReunionResponseDTO> toResponseDTOList(List<ReunionesModel> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        return models.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    // ========== MÉTODOS PRIVADOS AUXILIARES ==========
    
    /**
     * Convierte ModalidadDTO a Modalidad (Model)
     */
    private Modalidad toModalidadModel(ModalidadDTO dto) {
        if (dto == null) return null;
        Modalidad model = new Modalidad();
        model.setTipo(dto.getTipo());
        model.setUbicacion(dto.getUbicacion());
        model.setEnlace(dto.getEnlace());
        return model;
    }
    
    /**
     * Convierte Modalidad (Model) a ModalidadDTO
     */
    private ModalidadDTO toModalidadDTO(Modalidad model) {
        if (model == null) return null;
        ModalidadDTO dto = new ModalidadDTO();
        dto.setTipo(model.getTipo());
        dto.setUbicacion(model.getUbicacion());
        dto.setEnlace(model.getEnlace());
        return dto;
    }
    
    /**
     * Convierte InvitadoReunion (Model) a InvitadoReunionDTO
     */
    private InvitadoReunionDTO toInvitadoReunionDTO(InvitadoReunion model) {
        if (model == null) return null;
        InvitadoReunionDTO dto = new InvitadoReunionDTO();
        dto.setUsuarioId(model.getUsuarioId().toHexString());
        dto.setNombreUsuario(model.getNombreUsuario());
        return dto;
    }
    
    /**
     * Convierte LibroDiscutirDTO a LibroDiscutir (Model)
     */
    private LibroDiscutir toLibroDiscutirModel(LibroDiscutirDTO dto) {
        if (dto == null) return null;
        LibroDiscutir model = new LibroDiscutir();
        model.setLibroId(new ObjectId(dto.getLibroId()));
        model.setNombreLibro(dto.getNombreLibro());
        model.setArchivosAdjuntos(dto.getArchivosAdjuntos() != null ? 
            dto.getArchivosAdjuntos() : new ArrayList<>());
        return model;
    }
    
    /**
     * Convierte LibroDiscutir (Model) a LibroDiscutirDTO
     */
    private LibroDiscutirDTO toLibroDiscutirDTO(LibroDiscutir model) {
        if (model == null) return null;
        LibroDiscutirDTO dto = new LibroDiscutirDTO();
        dto.setLibroId(model.getLibroId().toHexString());
        dto.setNombreLibro(model.getNombreLibro());
        dto.setArchivosAdjuntos(model.getArchivosAdjuntos());
        return dto;
    }
    
    /**
     * Crea un InvitadoReunion (Model) a partir de un ID y nombre
     * Útil para el servicio cuando se crean invitados
     */
    public InvitadoReunion createInvitado(ObjectId usuarioId, String nombreUsuario) {
        InvitadoReunion invitado = new InvitadoReunion();
        invitado.setUsuarioId(usuarioId);
        invitado.setNombreUsuario(nombreUsuario);
        return invitado;
    }
}