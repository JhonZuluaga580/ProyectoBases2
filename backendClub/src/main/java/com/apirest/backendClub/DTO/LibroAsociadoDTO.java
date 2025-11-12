package com.apirest.backendClub.DTO;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LibroAsociadoDTO {
    private ObjectId libroId;
    private String porcentaje;
    public void setNombreLibro(String nombreLibro) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNombreLibro'");
    }
}
