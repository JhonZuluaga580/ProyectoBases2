package com.apirest.backendClub.Model.Retosembb;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Progreso {
    private ObjectId libroId;
    private String porcentaje;
    private String estado;
}
