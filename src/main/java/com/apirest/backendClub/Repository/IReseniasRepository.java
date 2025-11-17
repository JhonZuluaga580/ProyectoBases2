package com.apirest.backendClub.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.DTO.ReseniaTopDTO;
import com.apirest.backendClub.Model.ReseniasModel;



public interface IReseniasRepository extends MongoRepository<ReseniasModel,ObjectId>{
	/*
	Comprueba si existe una reseña del mismo libro por el mismo usuario.
	Usamos la ruta por propiedades embebidas: libro.libroId y usuario.usuarioId
	*/
	boolean existsByLibroLibroIdAndUsuarioUsuarioId(ObjectId libroId, ObjectId usuarioId);
  @Aggregation(pipeline = {
        "{ $addFields: { " +
        "    totalValoraciones: { " +
        "      $size: { $ifNull: [ '$valoracion', [] ] } " +
        "    } " +
        "} }",
        "{ $setWindowFields: { " +
        "    sortBy: { totalValoraciones: -1 }, " +
        "    output: { " +
        "      rankValoracion: { $rank: {} } " +
        "    } " +
        "} }",
        "{ $match: { rankValoracion: 1 } }",
        "{ $project: { " +
        "    _id: 1, " +
        "    libroNombre: '$libro.nombreLibro', " +
        "    usuarioNombre: '$usuario.nombreUsuario', " +
        "    calificacion: 1, " +
        "    opinion: 1, " +
        "    totalValoraciones: 1, " +
        "    rankValoracion: 1 " +
        "} }"
    })
    List<ReseniaTopDTO> obtenerReseniasMasValoradas();
}
