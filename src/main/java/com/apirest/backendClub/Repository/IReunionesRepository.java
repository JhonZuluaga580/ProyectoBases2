package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.ReunionesModel;

public interface IReunionesRepository extends MongoRepository<ReunionesModel, ObjectId> {
    
    //Buscar reuniones posteriores a una fecha específica; útil para obtener reuniones próximas o futuras
    List<ReunionesModel> findByDateTimeAfter(LocalDateTime fecha);
    
    //Buscar reuniones anteriores a una fecha específica; útil para obtener reuniones pasadas
    List<ReunionesModel> findByDateTimeBefore(LocalDateTime fecha);
    
    //Buscar reuniones en un rango de fechas
    List<ReunionesModel> findByDateTimeBetween(LocalDateTime inicio, LocalDateTime fin);
}