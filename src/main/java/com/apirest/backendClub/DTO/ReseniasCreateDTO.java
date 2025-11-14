package com.apirest.backendClub.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniasCreateDTO {
    private LibroReseniaDTO libro;
    private UsuarioReseniaDTO usuario;
    private Long calificacion;
    private String opinion;
    private List<String> archivosAdjuntos;
    private List<ComentarioOpinionDTO> comentario_opinion;
    //cambiar
}
