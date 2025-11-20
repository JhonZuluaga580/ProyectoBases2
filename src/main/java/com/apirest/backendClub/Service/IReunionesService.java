package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;
import com.apirest.backendClub.DTO.ReunionStatsDTO;

public interface IReunionesService {
    
    ReunionResponseDTO crearReunion(ReunionCreateDTO reunion);
    List<ReunionResponseDTO> listarReuniones();
    ReunionResponseDTO buscarReunionPorId(ObjectId id);
    ReunionResponseDTO actualizarReunion(ObjectId id, ReunionCreateDTO reunion);
    void eliminarReunion(ObjectId id);
    List<ReunionStatsDTO> obtenerReunionesMasConcurridas();

}