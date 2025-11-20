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
        model.getAutor().setNombreCompleto(autor.getNombreCompleto());
       if (model.getFechaPublicacion() == null) {
            model.setFechaPublicacion(LocalDateTime.now());
        }
        if (model.getEstado() == null || model.getEstado().isBlank()) {
            model.setEstado("activo"); 
        }
        
        ComentariosModel guardado = comentariosRepository.save(model);

        return comentarioMapper.toResponseDTO(guardado);
    }

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
    List<ComentariosModel> todos = comentariosRepository.findByForoId(foroId);

    List<ComentarioResponseDTO> dtos = todos.stream()
            .filter(c -> !"eliminado".equalsIgnoreCase(c.getEstado()))
            .sorted((a, b) -> a.getFechaPublicacion().compareTo(b.getFechaPublicacion())) 
            .map(comentarioMapper::toResponseDTO)
            .collect(Collectors.toList());


    var porId = dtos.stream().collect(Collectors.toMap(ComentarioResponseDTO::getId, x -> x));

    List<ComentarioResponseDTO> raiz = new java.util.ArrayList<>();
    for (ComentarioResponseDTO c : dtos) {
        String parentId = c.getParentId();
        if (parentId == null) {
            raiz.add(c); 
        } else {
            ComentarioResponseDTO padre = porId.get(parentId);
            if (padre != null) {
                padre.getRespuestas().add(c);
            } else {
                raiz.add(c);
            }
        }
    }

    return raiz;
}
    @Override
     public void eliminarComentarioPorId(ObjectId id) {
        ComentariosModel comentario = comentariosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Usuario con el ID " + id + " no existe."
            ));
        comentariosRepository.delete(comentario);
    }
}

