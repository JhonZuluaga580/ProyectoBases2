package com.apirest.backendClub.Model.Reseniasembb;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValoracionItem {
    private ObjectId usuarioId;
    private LocalDateTime fecha;    
}
