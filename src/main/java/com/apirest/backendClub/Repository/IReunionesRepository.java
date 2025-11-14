package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.apirest.backendClub.Model.ReunionesModel;

public interface IReunionesRepository extends MongoRepository<ReunionesModel, ObjectId> {
    
    //Buscar reuniones posteriores a una fecha específica; útil para obtener reuniones próximas o futuras
    List<ReunionesModel> findByDateTimeAfter(LocalDateTime fecha);
    
    //Buscar reuniones anteriores a una fecha específica; útil para obtener reuniones pasadas
    List<ReunionesModel> findByDateTimeBefore(LocalDateTime fecha);
    
    //Buscar reuniones en un rango de fechas
    List<ReunionesModel> findByDateTimeBetween(LocalDateTime inicio, LocalDateTime fin);
    
    //Buscar reuniones por tipo de modalidad (presencial o virtual)
    @Query("{ 'modalidad.tipo': ?0 }")
    List<ReunionesModel> findByModalidadTipo(String tipo);
    
    //Buscar reuniones donde un usuario específico esté invitado
    @Query("{ 'listaInvitados.usuarioId': ?0 }")
    List<ReunionesModel> findByInvitado(ObjectId usuarioId);
    
    //Buscar reuniones donde se discuta un libro específico
    @Query("{ 'libroDiscutir.libroId': ?0 }")
    List<ReunionesModel> findByLibroDiscutir(ObjectId libroId);
}