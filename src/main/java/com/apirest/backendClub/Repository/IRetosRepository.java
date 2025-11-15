package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.RetosModel;

public interface IRetosRepository extends MongoRepository<RetosModel, ObjectId> {
    
    // Buscar retos activos (que no han finalizado)
    List<RetosModel> findByFechaFinalizacionAfter(LocalDateTime fecha);
}