package com.apirest.backendClub.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ActualizarLibroDTO;
import com.apirest.backendClub.DTO.LibroCreateDTO;
import com.apirest.backendClub.DTO.LibroResponseDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.LibroMapper;
import com.apirest.backendClub.Model.LibrosModel;
import com.apirest.backendClub.Repository.ILibrosRepository;
import org.bson.types.ObjectId;

@Service
public class LibrosServiceImp implements ILibrosService {

    
    @Autowired ILibrosRepository librosRepository;
    @Autowired LibroMapper libroMapper;

    @Override
    public LibroResponseDTO crearLibro(LibroCreateDTO libro) {
        LibrosModel librosModel = libroMapper.toModel(libro);
        librosRepository.save(librosModel);
        return libroMapper.toResponseDTO(librosModel);
    }

    @Override
    public List<LibroResponseDTO> listarLibros() {
        return libroMapper.toResponseDTOList(librosRepository.findAll());
    }
    @Override
    public LibroResponseDTO buscarLibrosPorId(ObjectId idLibro) {
        LibrosModel librosModel = librosRepository.findById(idLibro)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Libro con el ID " + idLibro + " no existe."
            ));
        return libroMapper.toResponseDTO(librosModel);
    }
                
    
        @Override
         public LibroResponseDTO actualizarLibro(ObjectId idLibro, ActualizarLibroDTO libro) {
        LibrosModel librosModel = librosRepository.findById(idLibro)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Libro con el ID " + idLibro + " no existe."
            ));
    // Actualizar campos de la base de datos con valores del libro
        librosModel.setTitulo(libro.getTitulo());
        librosModel.setAutor(libro.getAutor());
        librosModel.setGenero(libro.getGenero());
        librosModel.setAnioPublicacion(libro.getAnioPublicacion());
        librosModel.setSinopsis(libro.getSinopsis());
        librosModel.setPortada(libro.getPortada());
        librosModel.setEstadoLectura(libro.getEstadoLectura());
        librosModel.setFechaSeleccion(libro.getFechaSeleccion());

    LibrosModel saved = librosRepository.save(librosModel);
        return libroMapper.toResponseDTO(saved);
    }
    @Override
     public void eliminarLibroPorId(ObjectId id) {
        LibrosModel libro = librosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Libro con el ID " + id + " no existe."
            ));
        librosRepository.delete(libro);
    }
    }



