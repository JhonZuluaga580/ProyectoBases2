package com.apirest.backendClub.Model.Reunionesembb;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDiscutir {
    private ObjectId libroId;
    private String nombreLibro;
    private List<String> archivosAdjuntos = new ArrayList<>();
}