package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;

public interface ILibrosPropuestosService {
    
    //Crea una nueva propuesta de libro.
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
}