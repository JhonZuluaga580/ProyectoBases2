package com.apirest.backendClub.Model.Retosembb;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroAsociado {
    private ObjectId libroId;
    private String nombreLibro;
}