package com.apirest.backendClub.Model.LibrosPropuestosembb;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropuestoPor {
    private ObjectId usuarioId;
    private String nombreCompleto;
}
