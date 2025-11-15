package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;

public interface IReunionesService {
    
    //Crea una nueva reunión en el sistema.
    ReunionResponseDTO crearReunion(ReunionCreateDTO reunion);
    
    //Lista todas las reuniones registradas en el sistema.
    List<ReunionResponseDTO> listarReuniones();
    
    //Busca una reunión específica por su ID.
    ReunionResponseDTO buscarReunionPorId(ObjectId id);
    
    //Actualiza los datos de una reunión existente.
    ReunionResponseDTO actualizarReunion(ObjectId id, ReunionCreateDTO reunion);
    
    //Elimina una reunión del sistema.
    void eliminarReunion(ObjectId id);
}