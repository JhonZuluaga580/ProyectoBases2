package com.apirest.backendClub.Model.Reunionesembb;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InvitadoReunion {
    private ObjectId usuarioId;
    private String nombreUsuario;
}