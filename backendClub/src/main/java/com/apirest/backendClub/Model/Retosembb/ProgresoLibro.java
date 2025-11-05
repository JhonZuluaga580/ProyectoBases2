package com.apirest.backendClub.Model.Retosembb;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoLibro {
    private ObjectId libroId;
    private String porcentaje; // "50" o "50%"
    private String estado; // ej. "en lectura", "completado"
}