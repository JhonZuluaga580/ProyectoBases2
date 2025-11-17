package com.apirest.backendClub.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniaTopDTO {
    private String id;
    private String libroNombre;
    private String usuarioNombre;
    private Integer calificacion;
    private String opinion;
    private Integer totalValoraciones;
    private Integer rankValoracion;
}
