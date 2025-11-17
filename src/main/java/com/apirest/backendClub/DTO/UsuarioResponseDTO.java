package com.apirest.backendClub.DTO;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private String id;
    private String nombreCompleto;
    private Integer edad;
    private String ocupacion;
    private String correo;
    private String telefono;
    private String rol;
    private Instant fechaRegistro;
}
