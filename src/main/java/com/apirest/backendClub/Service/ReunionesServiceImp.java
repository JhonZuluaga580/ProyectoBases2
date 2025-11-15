package com.apirest.backendClub.Service;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.ReunionMapper;
import com.apirest.backendClub.Model.*;
import com.apirest.backendClub.Model.Reunionesembb.InvitadoReunion;
import com.apirest.backendClub.Repository.*;

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
            throw new IllegalArgumentException("Fecha y hora requeridas");
        }
        
        ReunionesModel model = reunionMapper.toModel(reunion);
        
        // Procesar invitados: validar y agregar nombres
        List<InvitadoReunion> invitados = new ArrayList<>();
        if (reunion.getListaInvitadosIds() != null) {
            for (String idStr : reunion.getListaInvitadosIds()) {
                ObjectId usuarioId = new ObjectId(idStr);
                UsuariosModel usuario = usuariosRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no existe: " + idStr));
                
                invitados.add(reunionMapper.createInvitado(usuarioId, usuario.getNombreCompleto()));
            }
        }
        model.setListaInvitados(invitados);
        
        // Validar libros existen (opcional, pero buena práctica)
        if (model.getLibroDiscutir() != null) {
            for (var libro : model.getLibroDiscutir()) {
                if (!librosRepository.existsById(libro.getLibroId())) {
                    throw new RecursoNoEncontradoException("Libro no existe: " + libro.getLibroId());
                }
            }
        }
        
        ReunionesModel saved = reunionesRepository.save(model);
        return reunionMapper.toResponseDTO(saved);
    }

    @Override
    public List<ReunionResponseDTO> listarReuniones() {
        return reunionMapper.toResponseDTOList(reunionesRepository.findAll());
    }

    @Override
    public ReunionResponseDTO buscarReunionPorId(ObjectId id) {
        ReunionesModel reunion = reunionesRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reunión no encontrada: " + id));
        return reunionMapper.toResponseDTO(reunion);
    }

    @Override
    public ReunionResponseDTO actualizarReunion(ObjectId id, ReunionCreateDTO reunion) {
        ReunionesModel existente = reunionesRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reunión no encontrada: " + id));
        
        // Actualizar campos básicos
        if (reunion.getDateTime() != null) {
            existente.setDateTime(reunion.getDateTime());
        }
        
        if (reunion.getModalidad() != null) {
            existente.getModalidad().setTipo(reunion.getModalidad().getTipo());
            existente.getModalidad().setUbicacion(reunion.getModalidad().getUbicacion());
            existente.getModalidad().setEnlace(reunion.getModalidad().getEnlace());
        }
        
        return reunionMapper.toResponseDTO(reunionesRepository.save(existente));
    }

    @Override
    public void eliminarReunion(ObjectId id) {
        if (!reunionesRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Reunión no encontrada: " + id);
        }
        reunionesRepository.deleteById(id);
    }
}