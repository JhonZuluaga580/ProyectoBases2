package com.apirest.backendClub.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.LibrosPropuestosModel;

public interface ILibrosPropuestosRepository extends MongoRepository<LibrosPropuestosModel, ObjectId> {
    
    //Buscar propuestas por estado
    List<LibrosPropuestosModel> findByEstado(String estado);
    
    //Buscar propuestas realizadas por un usuario específico
    List<LibrosPropuestosModel> findByPropuestoPorUsuarioId(ObjectId usuarioId);
   
    //Verificar si existe una propuesta pendiente o en votación de un libro
    boolean existsByLibroPropuestoLibroIdAndEstadoIn(ObjectId libroId, List<String> estados);
    
}