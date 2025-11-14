package com.apirest.backendClub.Model;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResenia {
    private ObjectId usuarioId;
    private String nombreUsuario;
}
