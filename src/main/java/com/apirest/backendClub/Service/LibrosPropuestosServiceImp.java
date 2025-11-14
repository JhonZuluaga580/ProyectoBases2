package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.LibroPropuestoMapper;
import com.apirest.backendClub.Model.LibrosModel;
import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Votacion;
import com.apirest.backendClub.Repository.ILibrosRepository;
import com.apirest.backendClub.Repository.ILibrosPropuestosRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

/**
 * Implementación del servicio de libros propuestos.
 * Contiene la lógica de negocio para gestionar propuestas y votaciones.
 */
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
        // Validaciones básicas
        if (propuesta.getLibroId() == null || propuesta.getLibroId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del libro es requerido");
        }
        
        if (propuesta.getUsuarioId() == null || propuesta.getUsuarioId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        // Convertir IDs a ObjectId
        ObjectId libroId;
        ObjectId usuarioId;
        
        try {
            libroId = new ObjectId(propuesta.getLibroId());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("ID de libro inválido: " + propuesta.getLibroId());
        }
        
        try {
            usuarioId = new ObjectId(propuesta.getUsuarioId());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("ID de usuario inválido: " + propuesta.getUsuarioId());
        }
        
        // Verificar que el libro exista y obtener sus datos
        LibrosModel libro = librosRepository.findById(libroId)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Libro no encontrado con ID: " + propuesta.getLibroId()));
        
        // Verificar que el usuario exista y obtener sus datos
        UsuariosModel usuario = usuariosRepository.findById(usuarioId)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Usuario no encontrado con ID: " + propuesta.getUsuarioId()));
        
        // Verificar que no exista una propuesta activa del mismo libro
        List<String> estadosActivos = Arrays.asList("pendiente", "en_votacion");
        boolean existePropuestaActiva = propuestasRepository.existsByLibroIdAndEstadoIn(
            libroId, 
            estadosActivos
        );
        
        if (existePropuestaActiva) {
            throw new IllegalStateException(
                "Ya existe una propuesta activa para este libro. " +
                "No se pueden crear propuestas duplicadas mientras haya una pendiente o en votación."
            );
        }
        
        // Crear el modelo de propuesta
        LibrosPropuestosModel model = new LibrosPropuestosModel();
        
        // Establecer información del libro
        model.setLibroPropuesto(
            mapper.createLibroPropuesto(libroId, libro.getTitulo(), libro.getGenero())
        );
        
        // Establecer información del usuario que propone
        model.setPropuestoPor(
            mapper.createPropuestoPor(usuarioId, usuario.getNombreCompleto())
        );
        
        // Establecer fecha y estado por defecto
        model.setFecha(LocalDateTime.now());
        model.setEstado("pendiente");
        
        // Guardar en la base de datos
        LibrosPropuestosModel saved = propuestasRepository.save(model);
        
        // Convertir a DTO de respuesta
        return mapper.toResponseDTO(saved);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestas() {
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findAll();
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public LibroPropuestoResponseDTO buscarPropuestaPorId(ObjectId id) {
        LibrosPropuestosModel propuesta = propuestasRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Propuesta no encontrada con ID: " + id));
        return mapper.toResponseDTO(propuesta);
    }

    @Override
    public LibroPropuestoResponseDTO actualizarEstado(ObjectId id, String nuevoEstado) {
        // Validar estado
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es requerido");
        }
        
        String estadoNormalizado = nuevoEstado.toLowerCase();
        List<String> estadosValidos = Arrays.asList("pendiente", "en_votacion", "aprobada", "rechazada");
        
        if (!estadosValidos.contains(estadoNormalizado)) {
            throw new IllegalArgumentException(
                "Estado inválido. Estados válidos: pendiente, en_votacion, aprobada, rechazada"
            );
        }
        
        // Buscar la propuesta
        LibrosPropuestosModel propuesta = propuestasRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Propuesta no encontrada con ID: " + id));
        
        // Actualizar estado
        propuesta.setEstado(estadoNormalizado);
        
        // Guardar cambios
        LibrosPropuestosModel actualizada = propuestasRepository.save(propuesta);
        return mapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminarPropuesta(ObjectId id) {
        LibrosPropuestosModel propuesta = propuestasRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Propuesta no encontrada con ID: " + id));
        propuestasRepository.delete(propuesta);
    }

    @Override
    public LibroPropuestoResponseDTO votarPropuesta(ObjectId propuestaId, VotarDTO voto) {
        // Validaciones
        if (voto.getUsuarioId() == null || voto.getUsuarioId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        if (voto.getVoto() == null || voto.getVoto().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de voto es requerido");
        }
        
        // Validar tipo de voto
        String tipoVoto = voto.getVoto().toLowerCase();
        List<String> votosValidos = Arrays.asList("a favor", "en contra", "neutro");
        
        if (!votosValidos.contains(tipoVoto)) {
            throw new IllegalArgumentException(
                "Tipo de voto inválido. Votos válidos: a favor, en contra, neutro"
            );
        }
        
        // Convertir ID de usuario
        ObjectId usuarioId;
        try {
            usuarioId = new ObjectId(voto.getUsuarioId());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("ID de usuario inválido: " + voto.getUsuarioId());
        }
        
        // Verificar que el usuario exista
        UsuariosModel usuario = usuariosRepository.findById(usuarioId)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Usuario no encontrado con ID: " + voto.getUsuarioId()));
        
        // Buscar la propuesta
        LibrosPropuestosModel propuesta = propuestasRepository.findById(propuestaId)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Propuesta no encontrada con ID: " + propuestaId));
        
        // Verificar que el usuario no haya votado previamente
        boolean yaVoto = propuestasRepository.findByIdAndVotacionUsuarioId(propuestaId, usuarioId)
            .isPresent();
        
        if (yaVoto) {
            throw new IllegalStateException(
                "El usuario ya ha votado en esta propuesta. No se permite votar más de una vez."
            );
        }
        
        // Verificar que la propuesta esté en estado "en_votacion"
        if (!"en_votacion".equalsIgnoreCase(propuesta.getEstado())) {
            throw new IllegalStateException(
                "Solo se puede votar en propuestas que estén en estado 'en_votacion'. " +
                "Estado actual: " + propuesta.getEstado()
            );
        }
        
        // Crear el voto
        Votacion votacion = mapper.createVotacion(usuarioId, usuario.getNombreCompleto(), tipoVoto);
        
        // Agregar el voto a la lista
        propuesta.getVotacion().add(votacion);
        
        // Guardar cambios
        LibrosPropuestosModel actualizada = propuestasRepository.save(propuesta);
        return mapper.toResponseDTO(actualizada);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es requerido");
        }
        
        String estadoNormalizado = estado.toLowerCase();
        List<String> estadosValidos = Arrays.asList("pendiente", "en_votacion", "aprobada", "rechazada");
        
        if (!estadosValidos.contains(estadoNormalizado)) {
            throw new IllegalArgumentException(
                "Estado inválido. Estados válidos: pendiente, en_votacion, aprobada, rechazada"
            );
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByEstado(estadoNormalizado);
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestasDeUsuario(ObjectId usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        // Verificar que el usuario exista
        if (!usuariosRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByPropuestoPorUsuarioId(usuarioId);
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestasDeLibro(ObjectId libroId) {
        if (libroId == null) {
            throw new IllegalArgumentException("El ID del libro es requerido");
        }
        
        // Verificar que el libro exista
        if (!librosRepository.existsById(libroId)) {
            throw new RecursoNoEncontradoException("Libro no encontrado con ID: " + libroId);
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByLibroPropuestoLibroId(libroId);
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son requeridas");
        }
        
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByFechaBetween(inicio, fin);
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestasRecientes() {
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findAllByOrderByFechaDesc();
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPropuestasVotadasPorUsuario(ObjectId usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        // Verificar que el usuario exista
        if (!usuariosRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByVotacionUsuarioId(usuarioId);
        return mapper.toResponseDTOList(propuestas);
    }

    @Override
    public List<LibroPropuestoResponseDTO> listarPorGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("El género es requerido");
        }
        
        List<LibrosPropuestosModel> propuestas = propuestasRepository.findByLibroPropuestoGenero(genero);
        return mapper.toResponseDTOList(propuestas);
    }
}