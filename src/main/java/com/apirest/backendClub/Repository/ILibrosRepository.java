package com.apirest.backendClub.Repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.LibrosModel;

public interface ILibrosRepository extends MongoRepository <LibrosModel, ObjectId>{

}
