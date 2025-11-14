package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionResponseDTO {
    private String id;
    private LocalDateTime dateTime;
    private ModalidadDTO modalidad;
    private List<InvitadoReunionDTO> listaInvitados;
    private List<LibroDiscutirDTO> libroDiscutir;
}