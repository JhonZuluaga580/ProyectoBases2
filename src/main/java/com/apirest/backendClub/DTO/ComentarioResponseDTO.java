package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponseDTO {
  private String id;
  private String foroId;
  private String comentario;
  private String parentId;
  private AutorDTO autor;
  private LocalDateTime fechaPublicacion;
  private String estado;   
  
  
    // 👇 nuevo campo (opcional)
    private List<ComentarioResponseDTO> respuestas = new ArrayList<>();

    // getters & setters ...
    public List<ComentarioResponseDTO> getRespuestas() { return respuestas; }
    public void setRespuestas(List<ComentarioResponseDTO> respuestas) { this.respuestas = respuestas; }
    // resto de getters/setters de siempre
}
