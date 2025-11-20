package com.apirest.backendClub.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.DTO.ReunionStatsDTO;
import com.apirest.backendClub.Model.ReunionesModel;

public interface IReunionesRepository extends MongoRepository<ReunionesModel, ObjectId> {
    
    @Aggregation(pipeline = {
        "{ $addFields: { " +
        "    totalInvitados: { $size: { $ifNull: ['$listaInvitados', []] } } " +
        "} }",
        "{ $project: { " +
        "    _id: 1, " +
        "    dateTime: 1, " +
        "    modalidadTipo: '$modalidad.tipo', " +
        "    totalInvitados: 1 " +
        "} }",
        "{ $sort: { totalInvitados: -1 } }"
    })
    List<ReunionStatsDTO> obtenerReunionesMasConcurridas();
}