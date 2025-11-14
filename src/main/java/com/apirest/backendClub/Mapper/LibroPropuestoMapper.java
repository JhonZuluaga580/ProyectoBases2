package com.apirest.backendClub.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.EstadisticasVotacionDTO;
import com.apirest.backendClub.DTO.LibroPropuestoDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.PropuestoPorDTO;
import com.apirest.backendClub.DTO.VotarDTO;
import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.LibroPropuesto;
import com.apirest.backendClub.Model.LibrosPropuestosembb.PropuestoPor;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Votacion;

/**
 * Mapper para convertir entre DTOs y Models de Libros Propuestos.
 * Facilita la transformación de datos entre las capas de la aplicación.
 */
@Component
public class LibroPropuestoMapper {
    
    /**
     * Convierte un Model a ResponseDTO (para respuestas al cliente)
     * Incluye cálculo de estadísticas de votación
     * 
     * @param model Modelo de libro propuesto
     * @return DTO de respuesta con estadísticas
     */
    public LibroPropuestoResponseDTO toResponseDTO(LibrosPropuestosModel model) {
        LibroPropuestoResponseDTO dto = new LibroPropuestoResponseDTO();
        dto.setId(model.getIdAsString());
        dto.setLibroPropuesto(toLibroPropuestoDTO(model.getLibroPropuesto()));
        dto.setPropuestoPor(toPropuestoPorDTO(model.getPropuestoPor()));
        dto.setFecha(model.getFecha());
        dto.setEstado(model.getEstado());
        
        // Convertir lista de votaciones
        if (model.getVotacion() != null) {
            List<VotarDTO> votaciones = model.getVotacion().stream()
                .map(this::toVotacionDTO)
                .collect(Collectors.toList());
            dto.setVotacion(votaciones);
        } else {
            dto.setVotacion(new ArrayList<>());
        }
        
        // Calcular estadísticas
        dto.setEstadisticas(calcularEstadisticas(model));
        
        return dto;
    }
    
    /**
     * Convierte una lista de Models a lista de ResponseDTOs
     * 
     * @param models Lista de modelos de libros propuestos
     * @return Lista de DTOs de respuesta
     */
    public List<LibroPropuestoResponseDTO> toResponseDTOList(List<LibrosPropuestosModel> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        return models.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    // ========== MÉTODOS PRIVADOS AUXILIARES ==========
    
    /**
     * Convierte LibroPropuesto (Model) a LibroPropuestoDTO
     */
    private LibroPropuestoDTO toLibroPropuestoDTO(LibroPropuesto model) {
        if (model == null) return null;
        LibroPropuestoDTO dto = new LibroPropuestoDTO();
        dto.setLibroId(model.getLibroId().toHexString());
        dto.setTitulo(model.getTitulo());
        dto.setGenero(model.getGenero());
        return dto;
    }
    
    /**
     * Convierte PropuestoPor (Model) a PropuestoPorDTO
     */
    private PropuestoPorDTO toPropuestoPorDTO(PropuestoPor model) {
        if (model == null) return null;
        PropuestoPorDTO dto = new PropuestoPorDTO();
        dto.setUsuarioId(model.getUsuarioId().toHexString());
        dto.setNombreCompleto(model.getNombreCompleto());
        return dto;
    }
    
    /**
     * Convierte Votacion (Model) a VotacionDTO
     */
    private VotarDTO toVotacionDTO(Votacion model) {
        if (model == null) return null;
        VotarDTO dto = new VotarDTO();
        dto.setUsuarioId(model.getUsuarioId().toHexString());
        dto.setNombreCompleto(model.getNombreCompleto());
        dto.setFechaVotacion(model.getFechaVotacion());
        dto.setVoto(model.getVoto());
        return dto;
    }
    
    /**
     * Calcula las estadísticas de votación de una propuesta
     */
    private EstadisticasVotacionDTO calcularEstadisticas(LibrosPropuestosModel model) {
        EstadisticasVotacionDTO stats = new EstadisticasVotacionDTO();
        
        long votosAFavor = model.contarVotosAFavor();
        long votosEnContra = model.contarVotosEnContra();
        long votosNeutros = model.contarVotosNeutros();
        long totalVotos = model.getVotacion() != null ? model.getVotacion().size() : 0;
        
        stats.setTotalVotos(totalVotos);
        stats.setVotosAFavor(votosAFavor);
        stats.setVotosEnContra(votosEnContra);
        stats.setVotosNeutros(votosNeutros);
        
        // Calcular porcentaje a favor
        if (totalVotos > 0) {
            double porcentaje = (votosAFavor * 100.0) / totalVotos;
            stats.setPorcentajeAFavor(Math.round(porcentaje * 100.0) / 100.0); // 2 decimales
        } else {
            stats.setPorcentajeAFavor(0.0);
        }
        
        return stats;
    }
    
    /**
     * Crea un objeto LibroPropuesto (Model) a partir de IDs y datos
     * Útil para el servicio cuando se crean propuestas
     */
    public LibroPropuesto createLibroPropuesto(ObjectId libroId, String titulo, String genero) {
        LibroPropuesto libro = new LibroPropuesto();
        libro.setLibroId(libroId);
        libro.setTitulo(titulo);
        libro.setGenero(genero);
        return libro;
    }
    
    /**
     * Crea un objeto PropuestoPor (Model) a partir de IDs y datos
     * Útil para el servicio cuando se crean propuestas
     */
    public PropuestoPor createPropuestoPor(ObjectId usuarioId, String nombreCompleto) {
        PropuestoPor propuesto = new PropuestoPor();
        propuesto.setUsuarioId(usuarioId);
        propuesto.setNombreCompleto(nombreCompleto);
        return propuesto;
    }
    
    /**
     * Crea un objeto Votacion (Model) a partir de datos
     * Útil para el servicio cuando se registran votos
     */
    public Votacion createVotacion(ObjectId usuarioId, String nombreCompleto, String voto) {
        Votacion votacion = new Votacion();
        votacion.setUsuarioId(usuarioId);
        votacion.setNombreCompleto(nombreCompleto);
        votacion.setFechaVotacion(java.time.LocalDateTime.now());
        votacion.setVoto(voto);
        return votacion;
    }
}