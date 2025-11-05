package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Voto;

public interface ILibrosPropuestosService {
    LibrosPropuestosModel crearPropuesta(LibrosPropuestosModel propuesta);
    List<LibrosPropuestosModel> listarPropuestas();
    LibrosPropuestosModel obtenerPorId(ObjectId id);
    LibrosPropuestosModel agregarVoto(ObjectId propuestaId, Voto voto);
}