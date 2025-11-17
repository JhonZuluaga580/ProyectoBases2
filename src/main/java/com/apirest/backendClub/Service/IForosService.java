package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ActualizarForoDTO;
import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
import com.apirest.backendClub.DTO.ForoStatsDTO;


public interface IForosService {
 ForoResponseDTO crearForo(ForoCreateDTO foro);
    List<ForoResponseDTO> listarForos();
    ForoResponseDTO actualizarForo(ObjectId idForo, ActualizarForoDTO foro);
    List<ForoStatsDTO> obtenerForosConEstadisticas();
    void eliminarForoPorId(ObjectId id);
}
