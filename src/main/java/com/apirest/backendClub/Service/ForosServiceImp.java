package com.apirest.backendClub.Service;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
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
        if (model.getEstado() == null) model.setEstado("Abierto");

        ForosModel guardado = forosRepository.save(model);
        return foroMapper.toResponseDTO(guardado);
    }

    @Override
    public List<ForoResponseDTO> listarForos() {
        return foroMapper.toResponseDTOList(forosRepository.findAll());
    }
    
}
