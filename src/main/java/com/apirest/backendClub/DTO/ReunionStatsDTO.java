package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionStatsDTO {
    private String _id;
    private LocalDateTime dateTime;
    private ModalidadDTO modalidad;
    private Integer totalInvitados;
}