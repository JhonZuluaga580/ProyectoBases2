package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;

/**
 * Interfaz de servicio para la gestión de libros propuestos.
 * Define las operaciones de negocio disponibles incluyendo propuestas, votaciones y gestión de estados.
 */
public interface ILibrosPropuestosService {
    
    //Crea una nueva propuesta de libro.
    //Valida que el libro y el usuario existan.
    //Verifica que no haya propuestas duplicadas activas del mismo libro.
    LibroPropuestoResponseDTO crearPropuesta(LibroPropuestoCreateDTO propuesta);
    
    //Lista todas las propuestas registradas.
    List<LibroPropuestoResponseDTO> listarPropuestas();
    
    //Busca una propuesta específica por su ID.
    LibroPropuestoResponseDTO buscarPropuestaPorId(ObjectId id);
    
    //Actualiza el estado de una propuesta.
    //Estados válidos: "pendiente", "en_votacion", "aprobada", "rechazada"
    LibroPropuestoResponseDTO actualizarEstado(ObjectId id, String nuevoEstado);
    
    //Elimina una propuesta del sistema.
    void eliminarPropuesta(ObjectId id);
    
    //Registra un voto de un usuario en una propuesta.
    //Valida que el usuario no haya votado previamente.
    LibroPropuestoResponseDTO votarPropuesta(ObjectId propuestaId, VotarDTO voto);
    
    //Lista propuestas por estado.
    List<LibroPropuestoResponseDTO> listarPorEstado(String estado);
    
    //Lista propuestas realizadas por un usuario específico.
    List<LibroPropuestoResponseDTO> listarPropuestasDeUsuario(ObjectId usuarioId);
    
    //Lista propuestas de un libro específico.
    List<LibroPropuestoResponseDTO> listarPropuestasDeLibro(ObjectId libroId);
    
    //Lista propuestas en un rango de fechas.
    List<LibroPropuestoResponseDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);
    
    //Lista propuestas ordenadas por fecha (más recientes primero).
    List<LibroPropuestoResponseDTO> listarPropuestasRecientes();
    
    //Lista propuestas donde un usuario específico ha votado.
    List<LibroPropuestoResponseDTO> listarPropuestasVotadasPorUsuario(ObjectId usuarioId);
    
    //Lista propuestas por género del libro.
    List<LibroPropuestoResponseDTO> listarPorGenero(String genero);
}