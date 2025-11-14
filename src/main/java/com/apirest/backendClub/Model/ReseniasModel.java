package com.apirest.backendClub.Model;

import java.util.ArrayList;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.apirest.backendClub.Model.Reseniasembb.ComentarioResenia;
import com.apirest.backendClub.Model.Reseniasembb.LibroResenia;
import com.apirest.backendClub.Model.Reseniasembb.ValoracionItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document ("resenias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{'libro.libroId': 1, 'usuario.usuarioId': 1}", unique = true)
public class ReseniasModel {
    @Id
    private ObjectId id;
    private LibroResenia libro;
    private UsuarioResenia usuario;
    private Long calificacion;
    private String opinion;
    private List<String> archivosAdjuntos = new ArrayList<>();
    private List<ComentarioResenia> comentario_opinion = new ArrayList<>();
    private List<ValoracionItem> valoracion = new ArrayList<>();

    @JsonProperty("id")
    public String getIdAsString(){
        return id != null ? id.toHexString():null;
    }
}
enum Calificacion {
    UNO(1), DOS(2), TRES(3), CUATRO(4), CINCO(5);
    
    private final int valor;
    
    Calificacion(int valor) {
        this.valor = valor;
    }
    
    public int getValor() {
        return valor;
    }
   
}