package com.apirest.backendClub.Service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.Reunionesembb.Invitado;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;
import com.apirest.backendClub.Repository.IReunionesRepository;

@Service
public class ReunionesServiceImp implements IReunionesService {

    @Autowired
    private IReunionesRepository reunionesRepository;

    @Override
    public ReunionesModel crearReunion(ReunionesModel reunion) {
        if (reunion == null) {
            throw new IllegalArgumentException("La reunión no puede ser nula.");
        }
        if (reunion.getDateTime() == null || reunion.getModalidad() == null
                || reunion.getListaInvitados() == null || reunion.getListaInvitados().isEmpty()) {
            throw new IllegalArgumentException("Faltan campos obligatorios en la reunión (dateTime, modalidad o listaInvitados).");
        }

        String tipo = reunion.getModalidad().getTipo();
        if (tipo == null || !(tipo.equalsIgnoreCase("presencial") || tipo.equalsIgnoreCase("virtual"))) {
            throw new IllegalArgumentException("modalidad.tipo debe ser 'presencial' o 'virtual'.");
        }

        return reunionesRepository.save(reunion);
    }

    @Override
    public java.util.List<ReunionesModel> listarReuniones() {
        return reunionesRepository.findAll();
    }

    @Override
    public ReunionesModel obtenerPorId(ObjectId id) {
        return reunionesRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reunión con id " + id + " no existe."));
    }

    @Override
    public ReunionesModel registrarInvitado(ObjectId reunionId, Invitado invitado) {
        ReunionesModel reunion = reunionesRepository.findById(reunionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reunión con id " + reunionId + " no existe."));

        if (invitado == null || invitado.getUsuarioId() == null) {
            throw new IllegalArgumentException("El invitado y su usuarioId son requeridos.");
        }

        boolean yaInvitado = reunion.getListaInvitados().stream()
                .anyMatch(i -> i.getUsuarioId().equals(invitado.getUsuarioId()));
        if (yaInvitado) {
            throw new IllegalStateException("El usuario ya está en la lista de invitados/asistentes.");
        }

        reunion.getListaInvitados().add(invitado);
        return reunionesRepository.save(reunion);
    }

    @Override
    public ReunionesModel agregarLibroDiscutir(ObjectId reunionId, LibroDiscutir libro) {
        ReunionesModel reunion = reunionesRepository.findById(reunionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reunión con id " + reunionId + " no existe."));

        if (libro == null || libro.getLibroId() == null) {
            throw new IllegalArgumentException("El libro a discutir debe contener libroId.");
        }

        boolean existe = reunion.getLibroDiscutir().stream()
                .anyMatch(l -> l.getLibroId().equals(libro.getLibroId()));
        if (existe) {
            throw new IllegalStateException("El libro ya está en la lista de libros a discutir.");
        }

        reunion.getLibroDiscutir().add(libro);
        return reunionesRepository.save(reunion);
    }
}