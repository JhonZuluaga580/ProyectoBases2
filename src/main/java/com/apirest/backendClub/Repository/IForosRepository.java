package com.apirest.backendClub.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.DTO.ForoStatsDTO;
import com.apirest.backendClub.Model.ForosModel;

public interface IForosRepository extends MongoRepository<ForosModel, ObjectId> {
  @Aggregation(pipeline = {
        "{ $lookup: { from: 'comentarios', localField: '_id', foreignField: 'foroId', as: 'comentarios' } }",
        "{ $addFields: { " +
        "   totalComentarios: { $size: '$comentarios' }, " +
        "   ultimaActividad: { $max: '$comentarios.fechaPublicacion' } " +
        "} }",
        "{ $project: { " +
        "   _id: 1, " +
        "   titulo: 1, " +
        "   categoria: 1, " +
        "   descripcion: 1, " +
        "   moderadorNombre: '$moderador.nombreCompleto', " +
        "   totalComentarios: 1, " +
        "   ultimaActividad: 1 " +
        "} }",
        "{ $sort: { ultimaActividad: -1 } }"
    })
    List<ForoStatsDTO> listarForosConEstadisticas();
    } 
    

