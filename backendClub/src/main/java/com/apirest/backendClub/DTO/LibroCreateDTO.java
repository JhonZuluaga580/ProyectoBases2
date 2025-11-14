package com.apirest.backendClub.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroCreateDTO {
  private String titulo;
  private String autor;
  private String genero;
  private Integer anioPublicacion;
  private String sinopsis;
  private String portada;      
  private String estadoLectura; // "leido" | "pendiente" | "en lectura"
  private LocalDate fechaSeleccion;
}
