package com.apirest.backendClub.Model;

import java.time.Instant;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Document ("foros")
@NoArgsConstructor
@AllArgsConstructor
public class ForosModel {
  @Id
    private ObjectId id; 
    private String titulo;
    private String categoria;
    private String descripcion;
    private ModeradorForos moderador;
    private Instant  fechaPublicacion;
    private String estado;
}
