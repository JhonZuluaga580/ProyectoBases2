package com.apirest.backendClub.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear una nueva reunión.
 * No incluye el ID ya que será generado por MongoDB.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionCreateDTO {
    /**
     * Fecha y hora de la reunión
     */
    private LocalDateTime dateTime;
    
    /**
     * Información de modalidad (presencial/virtual)
     */
    private ModalidadDTO modalidad;
    
    /**
     * Lista de IDs de usuarios invitados (solo IDs, el nombre se recuperará)
     */
    private List<String> listaInvitadosIds = new ArrayList<>();
    
    /**
     * Lista de libros a discutir con sus archivos adjuntos opcionales
     */
    private List<LibroDiscutirDTO> libroDiscutir = new ArrayList<>();
}