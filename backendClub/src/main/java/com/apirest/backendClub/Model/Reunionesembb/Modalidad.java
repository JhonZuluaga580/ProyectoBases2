package com.apirest.backendClub.Model.Reunionesembb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modalidad {
    private String tipo;     // "presencial" o "virtual"
    private String ubicacion;
    private String enlace;
}