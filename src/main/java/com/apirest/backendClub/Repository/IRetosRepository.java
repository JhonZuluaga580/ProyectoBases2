package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.apirest.backendClub.Model.RetosModel;

public interface IRetosRepository extends MongoRepository<RetosModel, ObjectId> {
    
    // Buscar retos activos (que no han finalizado)
    List<RetosModel> findByFechaFinalizacionAfter(LocalDateTime fecha);
    
    // Buscar retos por título
    List<RetosModel> findByTituloContainingIgnoreCase(String titulo);
    
    // Verificar si un usuario ya está inscrito en un reto
    @Query("{ '_id': ?0, 'participantes.usuarioId': ?1 }")
    RetosModel findByIdAndParticipantesUsuarioId(ObjectId retoId, ObjectId usuarioId);
}