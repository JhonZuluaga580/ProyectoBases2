package com.apirest.backendClub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear una nueva propuesta de libro.
 * Solo requiere el ID del libro y del usuario, los demás datos se obtienen automáticamente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroPropuestoCreateDTO {
    private String libroId;
    private String usuarioId;
}