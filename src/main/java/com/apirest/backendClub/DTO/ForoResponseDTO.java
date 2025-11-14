package com.apirest.backendClub.DTO;
import java.time.LocalDateTime;

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
   private LocalDateTime fechaPublicacion;
   private String estado;
}
