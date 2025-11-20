package com.apirest.backendClub.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.DTO.RetoStatsDTO;
import com.apirest.backendClub.Model.RetosModel;

public interface IRetosRepository extends MongoRepository<RetosModel, ObjectId> {
        @Aggregation(pipeline = {
        "{ $addFields: { " +
        "    totalParticipantes: { $size: { $ifNull: ['$participantes', []] } }, " +
        "    totalLibros: { $size: { $ifNull: ['$listaLibrosAsociados', []] } } " +
        "} }",
        "{ $project: { " +
        "    _id: 1, " +
        "    titulo: 1, " +
        "    descripcion: 1, " +
        "    fechaInicio: 1, " +
        "    fechaFinalizacion: 1, " +
        "    totalParticipantes: 1, " +
        "    totalLibros: 1 " +
        "} }",
        "{ $sort: { totalParticipantes: -1 } }"
    })
    List<RetoStatsDTO> obtenerRetosConEstadisticas();
}