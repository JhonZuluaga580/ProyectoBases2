package com.apirest.backendClub.DTO;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForoResponseDTO {
   private String id;
   private String titulo;
   private String categoria;
   private String descripcion;
   private ModeradorDTO moderador;
   private Instant fechaPublicacion;
   private String estado;
}
