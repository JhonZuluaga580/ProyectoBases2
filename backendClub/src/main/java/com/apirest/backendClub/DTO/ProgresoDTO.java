package com.apirest.backendClub.DTO;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProgresoDTO {
    private ObjectId libroId;
    private String porcentaje;
    private String estado;
}
