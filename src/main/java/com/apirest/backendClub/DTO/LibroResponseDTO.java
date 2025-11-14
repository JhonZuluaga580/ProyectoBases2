package com.apirest.backendClub.DTO;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponseDTO {
    private String id;
  private String titulo;
  private String autor;
  private String genero;
  private Integer anioPublicacion;
  private String sinopsis;
  private String portada;
  private String estadoLectura;
  private LocalDate fechaSeleccion; 
}
