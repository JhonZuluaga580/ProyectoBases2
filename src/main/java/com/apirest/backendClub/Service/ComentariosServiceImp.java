package com.apirest.backendClub.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ComentarioCreateDTO;
import com.apirest.backendClub.DTO.ComentarioResponseDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.ComentarioMapper;
import com.apirest.backendClub.Model.AutorComentarios;
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
    private ComentarioMapper comentarioMapper;
    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Override
    public ComentarioResponseDTO crearComentario(ComentarioCreateDTO dto) {
        ComentariosModel model = comentarioMapper.toModel(dto);
        // Validaciones básicas
        ObjectId foroId = model.getForoId();
        if (foroId == null || !forosRepository.existsById(foroId)) {
            throw new RecursoNoEncontradoException("Se requiere foroId para crear un comentario");
        }
        var foro = forosRepository.findById(foroId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Foro no encontrado"));
        if ("Cerrado".equalsIgnoreCase(foro.getEstado()) || "Archivado".equalsIgnoreCase(foro.getEstado())) {
        throw new IllegalStateException("Este foro no permite nuevos comentarios.");
        }

        AutorComentarios autorE = model.getAutor();
        ObjectId autorId = (autorE != null)? autorE.getUsuarioId() : null;
        if (autorId == null) {
            throw new IllegalArgumentException("Se requiere autor válido para el comentario");
        }

         UsuariosModel autor = usuariosRepository.findById(autorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con id " + autorId + " no fue encontrado"));

        ObjectId parentId = model.getParentId();
        if (parentId != null) {
            ComentariosModel parent = comentariosRepository.findById(parentId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Comentario padre no encontrado"));
            if (parent.getForoId() != null && !parent.getForoId().equals(foroId)) {
                throw new IllegalArgumentException("El comentario padre pertenece a otro foro.");
            }
        }
       if (model.getFechaPublicacion() == null) {
            model.setFechaPublicacion(LocalDateTime.now());
        }
        if (model.getEstado() == null || model.getEstado().isBlank()) {
            model.setEstado("activo"); // soporte para soft-delete
        }

        // 4) Persistir
        ComentariosModel guardado = comentariosRepository.save(model);

        // 5) Responder con DTO
        return comentarioMapper.toResponseDTO(guardado);
    }

    // =========================
    // Listar comentarios de primer nivel por foro
    // =========================
    @Override
    public List<ComentarioResponseDTO> listarComentariosPorForo(ObjectId foroId) {
        List<ComentariosModel> lista = comentariosRepository.findByForoIdAndParentIdIsNull(foroId);
        return lista.stream()
                .filter(c -> !"eliminado".equalsIgnoreCase(c.getEstado()))
                .map(comentarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // Listar respuestas (hijos) de un comentario
    // =========================
    @Override
    public List<ComentarioResponseDTO> listarRespuestas(ObjectId parentId) {
        List<ComentariosModel> lista = comentariosRepository.findByParentId(parentId);
        return lista.stream()
                .filter(c -> !"eliminado".equalsIgnoreCase(c.getEstado()))
                .map(comentarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
@Override
public List<ComentarioResponseDTO> listarArbolPorForo(ObjectId foroId) {
    // 1) Traer TODOS los comentarios del foro
    List<ComentariosModel> todos = comentariosRepository.findByForoId(foroId);

    // 2) Filtrar soft-deleted y mapear a DTO
    List<ComentarioResponseDTO> dtos = todos.stream()
            .filter(c -> !"eliminado".equalsIgnoreCase(c.getEstado()))
            .sorted((a, b) -> a.getFechaPublicacion().compareTo(b.getFechaPublicacion())) // opcional: orden cronológico
            .map(comentarioMapper::toResponseDTO)
            .collect(Collectors.toList());

    // 3) Indexar por id para armar árbol rápido
    //    Ojo: en DTO los ids son String (hex), perfecto para usar de clave
    var porId = dtos.stream().collect(Collectors.toMap(ComentarioResponseDTO::getId, x -> x));

    // 4) Conectar hijos a sus padres
    List<ComentarioResponseDTO> raiz = new java.util.ArrayList<>();
    for (ComentarioResponseDTO c : dtos) {
        String parentId = c.getParentId();
        if (parentId == null) {
            raiz.add(c); // comentario de primer nivel
        } else {
            ComentarioResponseDTO padre = porId.get(parentId);
            if (padre != null) {
                padre.getRespuestas().add(c);
            } else {
                // Si el padre no existe (dato raro), lo tratamos como raíz para no perderlo
                raiz.add(c);
            }
        }
    }

    return raiz;
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

