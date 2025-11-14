package com.apirest.backendClub.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LibroDiscutirDTO {
    private String libroId;
    private String nombreLibro;
    private List<String> archivosAdjuntos;
}
