package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotarDTO {
    private String usuarioId;
    private String nombreCompleto;
    private LocalDateTime fechaVotacion;
    public String voto;
}
