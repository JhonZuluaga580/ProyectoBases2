package com.apirest.backendClub.Service;

import java.util.List;
import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ActualizarLibroDTO;
import com.apirest.backendClub.DTO.LibroCreateDTO;
import com.apirest.backendClub.DTO.LibroResponseDTO;

public interface ILibrosService {
    public LibroResponseDTO crearLibro(LibroCreateDTO libro);
    public List<LibroResponseDTO> listarLibros();
    // método para obtener el modelo completo (usado por el controlador actual)
    LibroResponseDTO buscarLibrosPorId(ObjectId idLibro);

    // actualizar retorna el modelo actualizado
    LibroResponseDTO actualizarLibro(ObjectId idLibro, ActualizarLibroDTO libro);

    void eliminarLibroPorId(ObjectId id);

}
