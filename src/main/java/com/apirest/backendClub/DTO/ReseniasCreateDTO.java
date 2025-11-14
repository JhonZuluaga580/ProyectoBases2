package com.apirest.backendClub.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniasCreateDTO {
    private LibroReseniaDTO libroResenia;
    private UsuarioReseniaDTO usuarioResenia;
    private Integer calificacion;
    private String opinion;
    private List<String> archivosAdjuntos;
    private List<ComentarioOpinionDTO> comentario_opinion;
    private List<String> valoracion;//cambiar
}
