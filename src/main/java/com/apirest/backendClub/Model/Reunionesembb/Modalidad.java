package com.apirest.backendClub.Model.Reunionesembb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase embebida que representa la modalidad de una reunión.
 * Puede ser presencial o virtual, con ubicación física o enlace respectivamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modalidad {
    private String tipo;
    private String ubicacion;
    private String enlace;
}