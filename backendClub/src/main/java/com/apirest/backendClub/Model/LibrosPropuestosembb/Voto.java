package com.apirest.backendClub.Model.LibrosPropuestosembb;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto {
    private ObjectId usuarioId;
    private String nombreCompleto;
    private LocalDateTime fechaVotacion;
    private String voto; // p.ej. "si", "no", "talvez"
}