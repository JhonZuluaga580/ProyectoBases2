package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.RetoCreateDTO;
import com.apirest.backendClub.DTO.RetoResponseDTO;

public interface IRetosService {
    RetoResponseDTO crearReto(RetoCreateDTO reto);
    List<RetoResponseDTO> listarRetos();
    RetoResponseDTO buscarRetoPorId(ObjectId id);
    RetoResponseDTO actualizarReto(ObjectId id, RetoCreateDTO reto);
    void eliminarReto(ObjectId id);
}