package com.apirest.backendClub.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.apirest.backendClub.Model.Reunionesembb.InvitadoReunion;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;
import com.apirest.backendClub.Model.Reunionesembb.Modalidad;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que representa una reunión del club de lectura.
 * Almacena información sobre fecha, modalidad, asistentes y libros a discutir.
 */
@Document("reuniones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReunionesModel {
    @Id
    private ObjectId id;
    private LocalDateTime dateTime;
    private Modalidad modalidad;
    private List<InvitadoReunion> listaInvitados = new ArrayList<>();
    private List<LibroDiscutir> libroDiscutir = new ArrayList<>();

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}