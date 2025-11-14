package com.apirest.backendClub.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.ComentariosModel;

public interface IComentariosRepository extends MongoRepository<ComentariosModel, ObjectId>{
	java.util.List<ComentariosModel> findByForoId(ObjectId foroId);
	java.util.List<ComentariosModel> findByParentId(ObjectId parentId);
	java.util.List<ComentariosModel> findByForoIdAndParentIdIsNull(ObjectId foroId);
}