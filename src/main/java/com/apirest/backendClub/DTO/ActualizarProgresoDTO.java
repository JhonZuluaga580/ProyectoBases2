package com.apirest.backendClub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarProgresoDTO {
    private String libroId;
    private String porcentaje;
    private String estado;
}