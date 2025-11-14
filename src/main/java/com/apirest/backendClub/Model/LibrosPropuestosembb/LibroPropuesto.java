package com.apirest.backendClub.Model.LibrosPropuestosembb;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroPropuesto {
    private ObjectId libroId;
    private String titulo;
    private String genero;
}