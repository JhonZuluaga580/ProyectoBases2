package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.ReunionMapper;
import com.apirest.backendClub.Model.LibrosModel;
import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Model.Reunionesembb.InvitadoReunion;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;
import com.apirest.backendClub.Repository.ILibrosRepository;
import com.apirest.backendClub.Repository.IReunionesRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

/**
 * Implementación del servicio de reuniones.
 * Contiene la lógica de negocio para gestionar las reuniones del club.
 */
@Service
public class ReunionesServiceImp implements IReunionesService {
    
    @Autowired
    private IReunionesRepository reunionesRepository;
    
    @Autowired
    private IUsuariosRepository usuariosRepository;
    
    @Autowired
    private ILibrosRepository librosRepository;
    
    @Autowired
    private ReunionMapper reunionMapper;

    @Override
    public ReunionResponseDTO crearReunion(ReunionCreateDTO reunion) {
        // Validaciones básicas
        if (reunion.getDateTime() == null) {
            throw new IllegalArgumentException("La fecha y hora de la reunión son requeridas");
        }
        
        if (reunion.getModalidad() == null || reunion.getModalidad().getTipo() == null) {
            throw new IllegalArgumentException("La modalidad de la reunión es requerida");
        }
        
        // Validar que el tipo de modalidad sea válido
        String tipo = reunion.getModalidad().getTipo().toLowerCase();
        if (!tipo.equals("presencial") && !tipo.equals("virtual")) {
            throw new IllegalArgumentException("El tipo de modalidad debe ser 'presencial' o 'virtual'");
        }
        
        // Validar modalidad presencial requiere ubicación
        if (tipo.equals("presencial") && 
            (reunion.getModalidad().getUbicacion() == null || reunion.getModalidad().getUbicacion().trim().isEmpty())) {
            throw new IllegalArgumentException("Las reuniones presenciales requieren una ubicación");
        }
        
        // Validar modalidad virtual requiere enlace
        if (tipo.equals("virtual") && 
            (reunion.getModalidad().getEnlace() == null || reunion.getModalidad().getEnlace().trim().isEmpty())) {
            throw new IllegalArgumentException("Las reuniones virtuales requieren un enlace");
        }
        
        // Convertir DTO a Model
        ReunionesModel model = reunionMapper.toModel(reunion);
        
        // Procesar lista de invitados: validar existencia y obtener nombres
        List<InvitadoReunion> invitados = new ArrayList<>();
        if (reunion.getListaInvitadosIds() != null && !reunion.getListaInvitadosIds().isEmpty()) {
            for (String idStr : reunion.getListaInvitadosIds()) {
                ObjectId usuarioId;
                try {
                    usuarioId = new ObjectId(idStr);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("ID de usuario inválido: " + idStr);
                }
                
                // Verificar que el usuario exista y obtener su nombre
                UsuariosModel usuario = usuariosRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con ID: " + idStr));
                
                // Crear invitado con datos completos
                InvitadoReunion invitado = reunionMapper.createInvitado(
                    usuarioId, 
                    usuario.getNombreCompleto()
                );
                invitados.add(invitado);
            }
        } else {
            throw new IllegalArgumentException("Debe invitar al menos un usuario a la reunión");
        }
        model.setListaInvitados(invitados);
        
        // Procesar libros a discutir: validar existencia y completar nombres
        if (reunion.getLibroDiscutir() != null && !reunion.getLibroDiscutir().isEmpty()) {
            List<LibroDiscutir> librosValidados = new ArrayList<>();
            for (LibroDiscutir libro : model.getLibroDiscutir()) {
                // Verificar que el libro exista
                LibrosModel libroDb = librosRepository.findById(libro.getLibroId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Libro no encontrado con ID: " + libro.getLibroId()));
                
                // Asegurar que el nombre del libro esté correcto
                libro.setNombreLibro(libroDb.getTitulo());
                librosValidados.add(libro);
            }
            model.setLibroDiscutir(librosValidados);
        }
        
        // Guardar en la base de datos
        ReunionesModel saved = reunionesRepository.save(model);
        
        // Convertir a DTO de respuesta
        return reunionMapper.toResponseDTO(saved);
    }

    @Override
    public List<ReunionResponseDTO> listarReuniones() {
        List<ReunionesModel> reuniones = reunionesRepository.findAll();
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public ReunionResponseDTO buscarReunionPorId(ObjectId id) {
        ReunionesModel reunion = reunionesRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Reunión no encontrada con ID: " + id));
        return reunionMapper.toResponseDTO(reunion);
    }

    @Override
    public ReunionResponseDTO actualizarReunion(ObjectId id, ReunionCreateDTO reunion) {
        // Verificar que la reunión exista
        ReunionesModel existente = reunionesRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Reunión no encontrada con ID: " + id));
        
        // Validaciones similares a crearReunion
        if (reunion.getDateTime() != null) {
            existente.setDateTime(reunion.getDateTime());
        }
        
        if (reunion.getModalidad() != null) {
            String tipo = reunion.getModalidad().getTipo();
            if (tipo != null) {
                tipo = tipo.toLowerCase();
                if (!tipo.equals("presencial") && !tipo.equals("virtual")) {
                    throw new IllegalArgumentException("El tipo de modalidad debe ser 'presencial' o 'virtual'");
                }
            }
            // Actualizar modalidad
            existente.getModalidad().setTipo(reunion.getModalidad().getTipo());
            existente.getModalidad().setUbicacion(reunion.getModalidad().getUbicacion());
            existente.getModalidad().setEnlace(reunion.getModalidad().getEnlace());
        }
        
        // Actualizar invitados si se proporcionan
        if (reunion.getListaInvitadosIds() != null && !reunion.getListaInvitadosIds().isEmpty()) {
            List<InvitadoReunion> invitados = new ArrayList<>();
            for (String idStr : reunion.getListaInvitadosIds()) {
                ObjectId usuarioId = new ObjectId(idStr);
                UsuariosModel usuario = usuariosRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con ID: " + idStr));
                
                InvitadoReunion invitado = reunionMapper.createInvitado(
                    usuarioId, 
                    usuario.getNombreCompleto()
                );
                invitados.add(invitado);
            }
            existente.setListaInvitados(invitados);
        }
        
        // Actualizar libros a discutir si se proporcionan
        if (reunion.getLibroDiscutir() != null) {
            List<LibroDiscutir> librosValidados = new ArrayList<>();
            for (var libroDTO : reunion.getLibroDiscutir()) {
                ObjectId libroId = new ObjectId(libroDTO.getLibroId());
                LibrosModel libroDb = librosRepository.findById(libroId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Libro no encontrado con ID: " + libroId));
                
                LibroDiscutir libro = new LibroDiscutir();
                libro.setLibroId(libroId);
                libro.setNombreLibro(libroDb.getTitulo());
                libro.setArchivosAdjuntos(libroDTO.getArchivosAdjuntos());
                librosValidados.add(libro);
            }
            existente.setLibroDiscutir(librosValidados);
        }
        
        // Guardar cambios
        ReunionesModel actualizado = reunionesRepository.save(existente);
        return reunionMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminarReunion(ObjectId id) {
        ReunionesModel reunion = reunionesRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Reunión no encontrada con ID: " + id));
        reunionesRepository.delete(reunion);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesProximas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<ReunionesModel> reuniones = reunionesRepository.findByDateTimeAfter(ahora);
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesPasadas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<ReunionesModel> reuniones = reunionesRepository.findByDateTimeBefore(ahora);
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son requeridas");
        }
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        List<ReunionesModel> reuniones = reunionesRepository.findByDateTimeBetween(inicio, fin);
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesPorModalidad(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de modalidad es requerido");
        }
        String tipoNormalizado = tipo.toLowerCase();
        if (!tipoNormalizado.equals("presencial") && !tipoNormalizado.equals("virtual")) {
            throw new IllegalArgumentException("El tipo de modalidad debe ser 'presencial' o 'virtual'");
        }
        List<ReunionesModel> reuniones = reunionesRepository.findByModalidadTipo(tipoNormalizado);
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesDeUsuario(ObjectId usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        // Verificar que el usuario exista
        if (!usuariosRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }
        List<ReunionesModel> reuniones = reunionesRepository.findByInvitado(usuarioId);
        return reunionMapper.toResponseDTOList(reuniones);
    }

    @Override
    public List<ReunionResponseDTO> listarReunionesPorLibro(ObjectId libroId) {
        if (libroId == null) {
            throw new IllegalArgumentException("El ID del libro es requerido");
        }
        // Verificar que el libro exista
        if (!librosRepository.existsById(libroId)) {
            throw new RecursoNoEncontradoException("Libro no encontrado con ID: " + libroId);
        }
        List<ReunionesModel> reuniones = reunionesRepository.findByLibroDiscutir(libroId);
        return reunionMapper.toResponseDTOList(reuniones);
    }
}