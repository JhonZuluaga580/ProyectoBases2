package com.apirest.backendClub.Model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document ("usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuariosModel {
    @Id
    private ObjectId id;
    private String nombreCompleto;
    private Integer edad;
    private String ocupacion;
    private String telefono;
    private String correo;
    private String rol;
    private LocalDateTime fechaRegistro;
    public enum Rol { lector, moderador, administrador }


    @JsonProperty("id")
    public String getIdAsString(){
        return id != null ? id.toHexString():null;
    }
}
