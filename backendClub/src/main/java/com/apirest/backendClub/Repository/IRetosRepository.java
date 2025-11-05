package com.apirest.backendClub.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.RetosModel;

public interface IRetosRepository extends MongoRepository<RetosModel, ObjectId> {

}