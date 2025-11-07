package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.ComentariosModel;

public interface IComentariosService {
    ComentariosModel crearComentario(ComentariosModel comentario);
    List<ComentariosModel> listarComentariosPorForo(ObjectId foroId);
    List<ComentariosModel> listarRespuestas(ObjectId parentId);
    ComentariosModel actualizarComentario(ObjectId id, ComentariosModel comentario);
    void eliminarComentario(ObjectId id);
}

