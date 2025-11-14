package com.apirest.backendClub.Service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.ComentariosModel;
import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Repository.IComentariosRepository;
import com.apirest.backendClub.Repository.IForosRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

@Service
public class ComentariosServiceImp implements IComentariosService{
    @Autowired
    private IComentariosRepository comentariosRepository;

    @Autowired
    private IForosRepository forosRepository;

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Override
    public ComentariosModel crearComentario(ComentariosModel comentario) {
        // Validaciones básicas
        if (comentario.getForoId() == null) {
            throw new RecursoNoEncontradoException("Se requiere foroId para crear un comentario");
        }

        // Verificar que el foro exista
        if (!forosRepository.existsById(comentario.getForoId())) {
            throw new RecursoNoEncontradoException("Foro no encontrado con ID: " + comentario.getForoId());
        }

        // Verificar autor
        if (comentario.getAutor() == null || comentario.getAutor().getUsuarioId() == null) {
            throw new RecursoNoEncontradoException("Se requiere autor válido para el comentario");
        }

        UsuariosModel usuario = usuariosRepository.findById(comentario.getAutor().getUsuarioId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autor no encontrado"));

        // Rellenar datos del autor (nombre)
        comentario.getAutor().setNombreCompleto(usuario.getNombreCompleto());

    // Establecer fecha y estado por defecto
    comentario.setFechaPublicacion(new Date());
        if (comentario.getEstado() == null || comentario.getEstado().trim().isEmpty()) {
            comentario.setEstado("visible");
        }

        return comentariosRepository.save(comentario);
    }

    @Override
    public List<ComentariosModel> listarComentariosPorForo(ObjectId foroId) {
        return comentariosRepository.findByForoId(foroId);
    }

    @Override
    public List<ComentariosModel> listarRespuestas(ObjectId parentId) {
        return comentariosRepository.findByParentId(parentId);
    }

    @Override
    public ComentariosModel actualizarComentario(ObjectId id, ComentariosModel comentario) {
        ComentariosModel existente = comentariosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));

        // Solo actualizamos el texto y el estado (no el autor ni foro)
        if (comentario.getComentario() != null) existente.setComentario(comentario.getComentario());
        if (comentario.getEstado() != null) existente.setEstado(comentario.getEstado());

        return comentariosRepository.save(existente);
    }

    @Override
    public void eliminarComentario(ObjectId id) {
        ComentariosModel existente = comentariosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));

        // Soft delete: cambiar estado a 'eliminado'
        existente.setEstado("eliminado");
        comentariosRepository.save(existente);
    }
}

