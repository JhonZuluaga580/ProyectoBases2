package com.apirest.backendClub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar un voto en una propuesta.
 * Este DTO se usa en el REQUEST (entrada) cuando un usuario vota.
 * Solo contiene usuarioId y el tipo de voto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotarDTO {
    private String usuarioId;
    private String voto;
}