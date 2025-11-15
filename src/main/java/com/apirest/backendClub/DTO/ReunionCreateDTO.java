package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionCreateDTO {
    private LocalDateTime dateTime;
    private ModalidadDTO modalidad;
    private List<String> listaInvitadosIds;
    private List<LibroDiscutirDTO> libroDiscutir;
}