package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.apirest.backendClub.Model.LibrosPropuestosModel;

public interface ILibrosPropuestosRepository extends MongoRepository<LibrosPropuestosModel, ObjectId> {
    
    //Buscar propuestas por estado
    List<LibrosPropuestosModel> findByEstado(String estado);
    
    //Buscar propuestas realizadas por un usuario específico
    @Query("{ 'propuestoPor.usuarioId': ?0 }")
    List<LibrosPropuestosModel> findByPropuestoPorUsuarioId(ObjectId usuarioId);
   
    //Buscar propuestas de un libro específico
    @Query("{ 'libroPropuesto.libroId': ?0 }")
    List<LibrosPropuestosModel> findByLibroPropuestoLibroId(ObjectId libroId);
    
    //Verificar si existe una propuesta pendiente o en votación de un libro
    @Query("{ 'libroPropuesto.libroId': ?0, 'estado': { $in: ?1 } }")
    boolean existsByLibroIdAndEstadoIn(ObjectId libroId, List<String> estados);
    
    //Buscar propuestas por rango de fechas
    List<LibrosPropuestosModel> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    //Buscar propuestas ordenadas por fecha descendente
    List<LibrosPropuestosModel> findAllByOrderByFechaDesc();
    
    //Buscar propuestas donde un usuario específico ha votado
    @Query("{ 'votacion.usuarioId': ?0 }")
    List<LibrosPropuestosModel> findByVotacionUsuarioId(ObjectId usuarioId);
    
    //Verificar si un usuario ya votó en una propuesta específica
    @Query("{ '_id': ?0, 'votacion.usuarioId': ?1 }")
    Optional<LibrosPropuestosModel> findByIdAndVotacionUsuarioId(ObjectId propuestaId, ObjectId usuarioId);
    
    //Buscar propuestas por género del libro
    @Query("{ 'libroPropuesto.genero': ?0 }")
    List<LibrosPropuestosModel> findByLibroPropuestoGenero(String genero);
}