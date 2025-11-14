package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroPropuestoResponseDTO {
    private String id;
    private LibroPropuestoDTO libroPropuesto; 
    private PropuestoPorDTO propuestoPor;
    private LocalDateTime fecha;
    private String estado;
    private List<VotacionDTO> votacion;
    private EstadisticasVotacionDTO estadisticas;

}
