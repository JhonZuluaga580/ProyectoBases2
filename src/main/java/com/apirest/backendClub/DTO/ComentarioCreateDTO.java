package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioCreateDTO {
    private String foroId;
    private String comentario;
    private String parentId;
    private AutorDTO autor;
    private LocalDateTime  fechaPublicacion;
    private String estado;
}
