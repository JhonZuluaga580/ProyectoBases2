package com.apirest.backendClub.Model;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Document ("comentarios")
@NoArgsConstructor
@AllArgsConstructor
public class ComentariosModel {
    @Id
    private ObjectId id; 
    private ObjectId foroId;
    private String comentario;
    private ObjectId parentId;
    private AutorComentarios autor;
    private java.util.Date fechaPublicacion;
    private String estado;
    
    // Helper para exponer el id como string en JSON (igual patrón que en UsuariosModel)
    public String getIdAsString(){
        return id != null ? id.toHexString():null;
    }
}
