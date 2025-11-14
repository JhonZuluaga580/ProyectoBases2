package com.apirest.backendClub.Model.LibrosPropuestosembb;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Votacion {
    private ObjectId usuarioId;
    private String nombreCompleto;
    private LocalDateTime fechaVotacion;
    public enum voto { a_favor, en_contra, neutro }
    
}
