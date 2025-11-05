package com.apirest.backendClub.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.apirest.backendClub.Model.LibrosPropuestosembb.LibroPropuesto;
import com.apirest.backendClub.Model.LibrosPropuestosembb.PropuestoPor;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Voto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("librosPropuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibrosPropuestosModel {
    @Id
    private ObjectId id;

    private LibroPropuesto libroPropuesto;
    private PropuestoPor propuestoPor;
    private LocalDateTime fecha;
    private String estado;
    private List<Voto> votacion = new ArrayList<>();

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}