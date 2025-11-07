package com.apirest.backendClub.Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.LibrosModel;

public interface ILibrosRepository extends MongoRepository <LibrosModel, ObjectId>{
    // Buscar por título
    Optional<LibrosModel> findByTitulo(String titulo);

    // Buscar por autor
    List<LibrosModel> findByAutor(String autor);

    // Buscar por género
    List<LibrosModel> findByGenero(String genero);

    // Buscar por estado de lectura
    List<LibrosModel> findByEstadoLectura(String estadoLectura);
}
