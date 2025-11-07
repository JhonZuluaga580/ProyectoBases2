package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioOpinionDTO {
    private String usuarioId;
    private String contenido;
    private LocalDateTime fecha;
}
