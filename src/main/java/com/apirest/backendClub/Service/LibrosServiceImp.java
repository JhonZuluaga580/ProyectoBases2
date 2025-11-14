package com.apirest.backendClub.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.LibrosModel;
import com.apirest.backendClub.Repository.ILibrosRepository;
import org.bson.types.ObjectId;
@Service
public class LibrosServiceImp implements ILibrosService {
    
    @Autowired 
    private ILibrosRepository librosRepository;

    @Override
    public LibrosModel crearLibro(LibrosModel libro) {
        return librosRepository.save(libro);
    }

    @Override
    public List<LibrosModel> listarLibros() {
        return librosRepository.findAll();
    }
     public LibrosModel buscarLibrosPorId(ObjectId idLibro) {
        return librosRepository.findById(idLibro)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Error! El Libro con el ID " + idLibro + " no existe."));
    }
    public LibrosModel actualizarLibro(ObjectId idLibro, LibrosModel libro){
        LibrosModel db = librosRepository.findById(idLibro)
        .orElseThrow(() -> new RecursoNoEncontradoException(
            "Error! El Libro con el ID "+idLibro+" no existe. "
        ));
// Actualizar campos de la base de datos con valores del libro
        db.setTitulo(libro.getTitulo());
        db.setAutor(libro.getAutor());
        db.setGenero(libro.getGenero());
        db.setAnioPublicacion(libro.getAnioPublicacion());
        db.setSinopsis(libro.getSinopsis());
        db.setPortada(libro.getPortada());
        db.setEstadoLectura(libro.getEstadoLectura());
        db.setFechaSeleccion(libro.getFechaSeleccion());

    return librosRepository.save(db);
    }
    public void eliminarLibroPorId(ObjectId id){
        var libro = librosRepository.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException(
            "Error! El Libro con el ID " + id + " no existe."));
    librosRepository.delete(libro);
    }
}


