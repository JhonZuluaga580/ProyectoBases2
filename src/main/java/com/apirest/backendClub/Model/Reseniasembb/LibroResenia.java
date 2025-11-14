package com.apirest.backendClub.Model.Reseniasembb;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResenia {
    private ObjectId libroId;
    private String nombreLibro;
}
