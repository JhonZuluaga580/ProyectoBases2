package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Voto;
import com.apirest.backendClub.Repository.ILibrosPropuestosRepository;
import com.apirest.backendClub.Repository.ILibrosRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibrosPropuestosServiceImp implements ILibrosPropuestosService {

    private final ILibrosPropuestosRepository repo;
    private final ILibrosRepository librosRepo;
    private final IUsuariosRepository usuariosRepo;

    @Override
    public LibrosPropuestosModel crearPropuesta(LibrosPropuestosModel propuesta) {
        if (propuesta == null) {
            throw new IllegalArgumentException("La propuesta no puede ser nula.");
        }
        if (propuesta.getLibroPropuesto() == null || propuesta.getPropuestoPor() == null
                || propuesta.getFecha() == null || propuesta.getEstado() == null) {
            throw new IllegalArgumentException("Faltan campos obligatorios: libroPropuesto, propuestoPor, fecha o estado.");
        }

        ObjectId libroId = propuesta.getLibroPropuesto().getLibroId();
        ObjectId usuarioId = propuesta.getPropuestoPor().getUsuarioId();

        // Verificar existencia del libro propuesto en la colección libros (opcional según reglas)
        if (libroId != null && !librosRepo.existsById(libroId)) {
            throw new RecursoNoEncontradoException("El libro propuesto (libroId) no existe en la colección libros.");
        }

        // Verificar existencia del usuario que propone
        if (usuarioId != null && !usuariosRepo.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("El usuario que propone no existe.");
        }

        // Si no viene fecha, asignar ahora
        if (propuesta.getFecha() == null) {
            propuesta.setFecha(LocalDateTime.now());
        }

        if (propuesta.getVotacion() == null) {
            propuesta.setVotacion(new java.util.ArrayList<>());
        }

        return repo.save(propuesta);
    }

    @Override
    public List<LibrosPropuestosModel> listarPropuestas() {
        return repo.findAll();
    }

    @Override
    public LibrosPropuestosModel obtenerPorId(ObjectId id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La propuesta con id " + id + " no existe."));
    }

    @Override
    public LibrosPropuestosModel agregarVoto(ObjectId propuestaId, Voto voto) {
        LibrosPropuestosModel propuesta = repo.findById(propuestaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La propuesta con id " + propuestaId + " no existe."));

        if (voto == null || voto.getUsuarioId() == null) {
            throw new IllegalArgumentException("El voto debe contener usuarioId y voto.");
        }

        // Verificar usuario existe
        if (!usuariosRepo.existsById(voto.getUsuarioId())) {
            throw new RecursoNoEncontradoException("El usuario que vota no existe.");
        }

        // Evitar doble votación por el mismo usuario
        boolean yaVoto = propuesta.getVotacion().stream()
                .anyMatch(v -> v.getUsuarioId().equals(voto.getUsuarioId()));
        if (yaVoto) {
            throw new IllegalStateException("El usuario ya ha votado en esta propuesta.");
        }

        // Asignar fecha de votación si no se proporciona
        if (voto.getFechaVotacion() == null) {
            voto.setFechaVotacion(LocalDateTime.now());
        }

        propuesta.getVotacion().add(voto);
        return repo.save(propuesta);
    }
}