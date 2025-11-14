package com.apirest.backendClub.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.ReseniasModel;



public interface IReseniasRepository extends MongoRepository<ReseniasModel,ObjectId>{
	/*
	Comprueba si existe una reseña del mismo libro por el mismo usuario.
	Usamos la ruta por propiedades embebidas: libro.libroId y usuario.usuarioId
	*/
	boolean existsByLibroLibroIdAndUsuarioUsuarioId(ObjectId libroId, ObjectId usuarioId);

}
