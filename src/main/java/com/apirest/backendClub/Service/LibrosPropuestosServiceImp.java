package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.LibroPropuestoMapper;
import com.apirest.backendClub.Model.*;
import com.apirest.backendClub.Repository.*;

@Service
public class LibrosPropuestosServiceImp implements ILibrosPropuestosService {
    
    @Autowired
    private ILibrosPropuestosRepository propuestasRepository;
    
    @Autowired
    private ILibrosRepository librosRepository;
    
    @Autowired
    private IUsuariosRepository usuariosRepository;
    
    @Autowired
    private LibroPropuestoMapper mapper;

    @Override
    public LibroPropuestoResponseDTO crearPropuesta(LibroPropuestoCreateDTO propuesta) {
        ObjectId libroId = new ObjectId(propuesta.getLibroId());
        ObjectId usuarioId = new ObjectId(propuesta.getUsuarioId());
        
        // Verificar existencia
        LibrosModel libro = librosRepository.findById(libroId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Libro no existe"));
        
        UsuariosModel usuario = usuariosRepository.findById(usuarioId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no existe"));
        
        // Evitar duplicados
        List<String> estadosActivos = Arrays.asList("pendiente", "en_votacion");
        if (propuestasRepository.existsByLibroPropuestoLibroIdAndEstadoIn(libroId, estadosActivos)) {
            throw new IllegalStateException("Ya existe propuesta activa para este libro");
        }
        
        // Crear propuesta
        LibrosPropuestosModel model = new LibrosPropuestosModel();
        model.setLibroPropuesto(mapper.createLibroPropuesto(libroId, libro.getTitulo(), libro.getGenero()));
        model.setPropuestoPor(mapper.createPropuestoPor(usuarioId, usuario.getNombreCompleto()));
        model.setFecha(LocalDateTime.now());
        model.setEstado("pendiente");
        
        return mapper.toResponseDTO(propuestasRepository.save(model));
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestas() {
        return mapper.toResponseDTOList(propuestasRepository.findAll());
    }

    @Override
    public LibroPropuestoResponseDTO buscarPropuestaPorId(ObjectId id) {
        LibrosPropuestosModel propuesta = propuestasRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Propuesta no encontrada: " + id));
        return mapper.toResponseDTO(propuesta);
    }

    @Override
    public LibroPropuestoResponseDTO actualizarEstado(ObjectId id, String nuevoEstado) {
        List<String> estadosValidos = Arrays.asList("pendiente", "en_votacion", "aprobada", "rechazada");
        
        if (!estadosValidos.contains(nuevoEstado.toLowerCase())) {
            throw new IllegalArgumentException("Estado inválido");
        }
        
        LibrosPropuestosModel propuesta = propuestasRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Propuesta no encontrada: " + id));
        
        propuesta.setEstado(nuevoEstado.toLowerCase());
        return mapper.toResponseDTO(propuestasRepository.save(propuesta));
    }

    @Override
    public void eliminarPropuesta(ObjectId id) {
        if (!propuestasRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Propuesta no encontrada: " + id);
        }
        propuestasRepository.deleteById(id);
    }

    @Override
    public LibroPropuestoResponseDTO votarPropuesta(ObjectId propuestaId, VotarDTO voto) {
        ObjectId usuarioId = new ObjectId(voto.getUsuarioId());
        
        // Validar tipo de voto
        List<String> votosValidos = Arrays.asList("a favor", "en contra", "neutro");
        if (!votosValidos.contains(voto.getVoto().toLowerCase())) {
            throw new IllegalArgumentException("Voto inválido");
        }
        
        // Verificar usuario existe
        UsuariosModel usuario = usuariosRepository.findById(usuarioId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no existe"));
        
        // Buscar propuesta
        LibrosPropuestosModel propuesta = propuestasRepository.findById(propuestaId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Propuesta no encontrada"));
        
        // Verificar no ha votado antes
        boolean yaVoto = propuesta.getVotacion().stream()
            .anyMatch(v -> v.getUsuarioId().equals(usuarioId));
        
        if (yaVoto) {
            throw new IllegalStateException("Usuario ya votó en esta propuesta");
        }
        
        // Agregar voto
        propuesta.getVotacion().add(
            mapper.createVotacion(usuarioId, usuario.getNombreCompleto(), voto.getVoto().toLowerCase())
        );
        
        return mapper.toResponseDTO(propuestasRepository.save(propuesta));
    }

}