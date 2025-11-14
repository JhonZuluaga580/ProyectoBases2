package com.apirest.backendClub.Model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document ("libros")
@NoArgsConstructor
@AllArgsConstructor

public class LibrosModel {
    @Id
    private ObjectId id;
    private String titulo;
    private String autor;
    private String genero;
    private Integer anioPublicacion;
    private String sinopsis;
    private String portada;
    private String estadoLectura;
    private LocalDateTime fechaSeleccion;
    public enum EstadoLectura { leido, pendiente, en_lectura }
    
    @JsonProperty("id")
    public String getIdAsString(){
        return id != null ? id.toHexString():null;
    }
}
