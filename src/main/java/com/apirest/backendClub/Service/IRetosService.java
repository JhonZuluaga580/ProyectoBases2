package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.RetoCreateDTO;
import com.apirest.backendClub.DTO.RetoResponseDTO;
import com.apirest.backendClub.DTO.RetoStatsDTO;
import com.apirest.backendClub.DTO.ActualizarProgresoDTO;


public interface IRetosService {
    RetoResponseDTO crearReto(RetoCreateDTO reto);
    List<RetoResponseDTO> listarRetos();
    RetoResponseDTO buscarRetoPorId(ObjectId id);
    RetoResponseDTO actualizarReto(ObjectId id, RetoCreateDTO reto);
    void eliminarReto(ObjectId id);
    RetoResponseDTO inscribirUsuarioEnReto(ObjectId retoId, ObjectId usuarioId);
    RetoResponseDTO actualizarProgresoUsuario(ObjectId retoId, ObjectId usuarioId, ActualizarProgresoDTO progreso);
    List<RetoStatsDTO> obtenerRetosConEstadisticas();
}