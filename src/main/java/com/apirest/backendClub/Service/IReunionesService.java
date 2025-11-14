package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;

/**
 * Interfaz de servicio para la gestión de reuniones del club de lectura.
 * Define las operaciones de negocio disponibles.
 */
public interface IReunionesService {
    
    /**
     * Crea una nueva reunión en el sistema.
     * Valida que los usuarios invitados y libros existan.
     * 
     * @param reunion DTO con los datos de la reunión a crear
     * @return DTO con la reunión creada incluyendo su ID
     */
    ReunionResponseDTO crearReunion(ReunionCreateDTO reunion);
    
    /**
     * Lista todas las reuniones registradas en el sistema.
     * 
     * @return Lista de todas las reuniones
     */
    List<ReunionResponseDTO> listarReuniones();
    
    /**
     * Busca una reunión específica por su ID.
     * 
     * @param id ID de la reunión
     * @return DTO con los datos de la reunión
     * @throws RecursoNoEncontradoException si la reunión no existe
     */
    ReunionResponseDTO buscarReunionPorId(ObjectId id);
    
    /**
     * Actualiza los datos de una reunión existente.
     * 
     * @param id ID de la reunión a actualizar
     * @param reunion DTO con los nuevos datos
     * @return DTO con la reunión actualizada
     * @throws RecursoNoEncontradoException si la reunión no existe
     */
    ReunionResponseDTO actualizarReunion(ObjectId id, ReunionCreateDTO reunion);
    
    /**
     * Elimina una reunión del sistema.
     * 
     * @param id ID de la reunión a eliminar
     * @throws RecursoNoEncontradoException si la reunión no existe
     */
    void eliminarReunion(ObjectId id);
    
    /**
     * Busca reuniones próximas (posteriores a la fecha actual).
     * 
     * @return Lista de reuniones futuras ordenadas por fecha
     */
    List<ReunionResponseDTO> listarReunionesProximas();
    
    /**
     * Busca reuniones pasadas (anteriores a la fecha actual).
     * 
     * @return Lista de reuniones pasadas
     */
    List<ReunionResponseDTO> listarReunionesPasadas();
    
    /**
     * Busca reuniones en un rango de fechas específico.
     * 
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return Lista de reuniones en el rango
     */
    List<ReunionResponseDTO> listarReunionesPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Busca reuniones por tipo de modalidad.
     * 
     * @param tipo Tipo de modalidad ("presencial" o "virtual")
     * @return Lista de reuniones del tipo especificado
     */
    List<ReunionResponseDTO> listarReunionesPorModalidad(String tipo);
    
    /**
     * Busca reuniones donde un usuario específico está invitado.
     * 
     * @param usuarioId ID del usuario
     * @return Lista de reuniones del usuario
     */
    List<ReunionResponseDTO> listarReunionesDeUsuario(ObjectId usuarioId);
    
    /**
     * Busca reuniones donde se discute un libro específico.
     * 
     * @param libroId ID del libro
     * @return Lista de reuniones donde se discute el libro
     */
    List<ReunionResponseDTO> listarReunionesPorLibro(ObjectId libroId);
}