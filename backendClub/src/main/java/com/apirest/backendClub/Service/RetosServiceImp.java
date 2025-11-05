package com.apirest.backendClub.Service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.ParticipanteReto;
import com.apirest.backendClub.Model.Retosembb.ProgresoLibro;
import com.apirest.backendClub.Repository.IRetosRepository;

@Service
public class RetosServiceImp implements IRetosService {

    @Autowired
    private IRetosRepository retosRepository;

    @Override
    public RetosModel crearReto(RetosModel reto) {
        if (reto == null) {
            throw new IllegalArgumentException("El reto no puede ser nulo.");
        }
        if (reto.getTitulo() == null || reto.getDescripcion() == null
                || reto.getFechaInicio() == null || reto.getFechaFinalizacion() == null) {
            throw new IllegalArgumentException("Faltan campos obligatorios del reto (titulo, descripcion, fechaInicio, fechaFinalizacion).");
        }
        if (reto.getListaLibrosAsociados() == null || reto.getListaLibrosAsociados().isEmpty()) {
            throw new IllegalArgumentException("La listaLibrosAsociados es obligatoria y no puede estar vacía.");
        }
        return retosRepository.save(reto);
    }

    @Override
    public java.util.List<RetosModel> listarRetos() {
        return retosRepository.findAll();
    }

    @Override
    public RetosModel obtenerPorId(ObjectId id) {
        return retosRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El reto con id " + id + " no existe."));
    }

    @Override
    public RetosModel agregarParticipante(ObjectId retoId, ParticipanteReto participante) {
        RetosModel reto = retosRepository.findById(retoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El reto con id " + retoId + " no existe."));

        if (participante == null || participante.getUsuarioId() == null) {
            throw new IllegalArgumentException("El participante y su usuarioId son requeridos.");
        }

        boolean yaParticipa = reto.getParticipantes().stream()
                .anyMatch(p -> p.getUsuarioId().equals(participante.getUsuarioId()));

        if (yaParticipa) {
            throw new IllegalStateException("El usuario ya es participante del reto.");
        }

        if (participante.getProgreso() == null) {
            participante.setProgreso(new java.util.ArrayList<>());
        }

        reto.getParticipantes().add(participante);
        return retosRepository.save(reto);
    }

    @Override
    public RetosModel actualizarProgreso(ObjectId retoId, ObjectId usuarioId, ProgresoLibro progreso) {
        RetosModel reto = retosRepository.findById(retoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El reto con id " + retoId + " no existe."));

        if (progreso == null || progreso.getLibroId() == null) {
            throw new IllegalArgumentException("El progreso debe contener libroId, porcentaje y estado.");
        }

        // Validar que el libro esté en la lista de libros del reto
        boolean libroEnReto = reto.getListaLibrosAsociados().stream()
                .anyMatch(l -> l.getLibroId().equals(progreso.getLibroId()));
        if (!libroEnReto) {
            throw new IllegalArgumentException("El libro indicado no está incluido en este reto.");
        }

        // Buscar participante
        Optional<com.apirest.backendClub.Model.Retosembb.ParticipanteReto> participanteOpt = reto.getParticipantes().stream()
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .findFirst();

        if (participanteOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no participa en el reto.");
        }

        com.apirest.backendClub.Model.Retosembb.ParticipanteReto participante = participanteOpt.get();

        // Validar duplicidad de progreso para el mismo libro y usuario
        boolean progresoExistente = participante.getProgreso().stream()
                .anyMatch(pr -> pr.getLibroId().equals(progreso.getLibroId()));
        if (progresoExistente) {
            throw new IllegalStateException("Ya existe un registro de progreso para ese libro por este usuario en el reto.");
        }

        // Validar campos porcentaje/estado
        if (progreso.getPorcentaje() == null || progreso.getEstado() == null) {
            throw new IllegalArgumentException("El progreso debe incluir porcentaje y estado.");
        }

        participante.getProgreso().add(progreso);
        return retosRepository.save(reto);
    }
}