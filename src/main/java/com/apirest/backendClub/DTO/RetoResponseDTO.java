package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetoResponseDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinalizacion;
    private List<LibroAsociadoDTO> listaLibrosAsociados;
    private List<ParticipanteDTO> participantes;
}