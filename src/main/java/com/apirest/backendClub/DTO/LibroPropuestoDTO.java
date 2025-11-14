package com.apirest.backendClub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroPropuestoDTO {
    private String libroId;
    private String titulo;
    private String genero;
}
