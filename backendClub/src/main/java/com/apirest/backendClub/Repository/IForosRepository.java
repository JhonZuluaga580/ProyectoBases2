package com.apirest.backendClub.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.ForosModel;

public interface IForosRepository extends MongoRepository<ForosModel, ObjectId> {

    } 
    

