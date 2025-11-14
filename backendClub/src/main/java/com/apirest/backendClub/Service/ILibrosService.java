package com.apirest.backendClub.Service;

import java.util.List;
import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.LibrosModel;

public interface ILibrosService {
    LibrosModel crearLibro(LibrosModel libro);
    List<LibrosModel> listarLibros();
    LibrosModel buscarLibrosPorId(ObjectId idLibro);
    LibrosModel actualizarLibro(ObjectId objectId, LibrosModel libro);
    void eliminarLibroPorId(ObjectId id);

}
