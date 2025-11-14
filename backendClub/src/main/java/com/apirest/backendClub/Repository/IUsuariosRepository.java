package com.apirest.backendClub.Repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.backendClub.Model.UsuariosModel;

public interface IUsuariosRepository extends MongoRepository<UsuariosModel,ObjectId>{

    
}
