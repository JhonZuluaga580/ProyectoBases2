package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ComentarioCreateDTO;
import com.apirest.backendClub.DTO.ComentarioResponseDTO;

public interface IComentariosService {
    ComentarioResponseDTO crearComentario(ComentarioCreateDTO comentario);
    List<ComentarioResponseDTO> listarComentariosPorForo(ObjectId foroId);
    List<ComentarioResponseDTO> listarRespuestas(ObjectId parentId);
    List<ComentarioResponseDTO> listarArbolPorForo(ObjectId foroId);
    void eliminarComentario(ObjectId id);
}

