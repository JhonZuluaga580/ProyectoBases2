package com.apirest.backendClub.Model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeradorForos {
    private ObjectId usuarioId;
    private String nombreCompleto;
}
