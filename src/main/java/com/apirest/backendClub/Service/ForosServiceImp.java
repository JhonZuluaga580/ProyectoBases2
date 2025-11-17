package com.apirest.backendClub.Service;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ActualizarForoDTO;
import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
import com.apirest.backendClub.DTO.ForoStatsDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.ForoMapper;
import com.apirest.backendClub.Model.ForosModel;
import com.apirest.backendClub.Repository.IForosRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

@Service
public class ForosServiceImp implements IForosService{
    @Autowired IForosRepository forosRepository;
    @Autowired IUsuariosRepository usuariosRepository;
    @Autowired ForoMapper foroMapper;

    @Override
    public ForoResponseDTO crearForo(ForoCreateDTO dto) {
        // Validaciones básicas del moderador embebido
        if (dto == null || dto.getModerador() == null || dto.getModerador().getUsuarioId() == null) {
            throw new RecursoNoEncontradoException("Se requiere un moderador para crear el foro");
        }

        // Recuperar el usuario completo desde la colección de usuarios
        var moderadorId = new ObjectId(dto.getModerador().getUsuarioId());
        var usuario = usuariosRepository.findById(moderadorId)
            .orElseThrow(()-> new RecursoNoEncontradoException("El Usuario Moderador no existe"));
        

        // Verificar rol: solo 'moderador' puede crear foros
        if (usuario.getRol() == null || !"moderador".equalsIgnoreCase(usuario.getRol())) {
            throw new RecursoNoEncontradoException("El usuario no tiene el rol de moderador");
        }

        ForosModel model = foroMapper.toModel(dto);
        model.getModerador().setUsuarioId(moderadorId);
        model.getModerador().setNombreCompleto(usuario.getNombreCompleto());
        model.setFechaPublicacion(Instant.now());
        if (model.getEstado() == null || model.getEstado().isBlank()) {
        model.setEstado("Abierto");
}
        ForosModel guardado = forosRepository.save(model);
        return foroMapper.toResponseDTO(guardado);
    }

    @Override
    public List<ForoResponseDTO> listarForos() {
        return foroMapper.toResponseDTOList(forosRepository.findAll());
    }
    @Override
         public ForoResponseDTO actualizarForo(ObjectId idForo, ActualizarForoDTO foro) {
        ForosModel forosModel = forosRepository.findById(idForo)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Libro con el ID " + idForo + " no existe."
            ));
    // Actualizar campos de la base de datos con valores del libro
        forosModel.setTitulo(foro.getTitulo());
        forosModel.setCategoria(foro.getCategoria());
        forosModel.setDescripcion(foro.getDescripcion());
        forosModel.setEstado(foro.getEstado());
    
    ForosModel saved = forosRepository.save(forosModel);
        return foroMapper.toResponseDTO(saved);
    }
    @Override
     public void eliminarForoPorId(ObjectId id) {
        ForosModel foro = forosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Usuario con el ID " + id + " no existe."
            ));
        forosRepository.delete(foro);
    }
     public List<ForoStatsDTO> obtenerForosConEstadisticas() {
        return forosRepository.listarForosConEstadisticas();
    }
}
