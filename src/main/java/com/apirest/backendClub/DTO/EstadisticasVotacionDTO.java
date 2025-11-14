package com.apirest.backendClub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mostrar estadísticas de votación de una propuesta
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasVotacionDTO {
    private long totalVotos;         // Total de votos recibidos
    private long votosAFavor;        // Votos a favor
    private long votosEnContra;      // Votos en contra
    private long votosNeutros;       // Votos neutros
    private double porcentajeAFavor; // Porcentaje de votos a favor
}