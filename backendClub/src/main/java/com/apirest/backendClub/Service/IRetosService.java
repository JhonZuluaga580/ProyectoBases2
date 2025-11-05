package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.ParticipanteReto;
import com.apirest.backendClub.Model.Retosembb.ProgresoLibro;


public interface IRetosService {
    RetosModel crearReto(RetosModel reto);
    List<RetosModel> listarRetos();
    RetosModel obtenerPorId(ObjectId id);
    RetosModel agregarParticipante(ObjectId retoId, ParticipanteReto participante);
    RetosModel actualizarProgreso(ObjectId retoId, ObjectId usuarioId, ProgresoLibro progreso);
}