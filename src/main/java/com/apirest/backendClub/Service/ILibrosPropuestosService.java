package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;

public interface ILibrosPropuestosService {
    
    LibroPropuestoResponseDTO crearPropuesta(LibroPropuestoCreateDTO propuesta);
    List<LibroPropuestoResponseDTO> listarPropuestas();
    LibroPropuestoResponseDTO buscarPropuestaPorId(ObjectId id);
    LibroPropuestoResponseDTO actualizarEstado(ObjectId id, String nuevoEstado);
    void eliminarPropuesta(ObjectId id);
    LibroPropuestoResponseDTO votarPropuesta(ObjectId propuestaId, VotarDTO voto);
}