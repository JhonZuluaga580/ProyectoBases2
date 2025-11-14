package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ActualizarUsuarioDTO;
import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.UsuarioMapper;
import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Repository.IUsuariosRepository;
@Service

public class UsuariosServiceimp implements IUsuariosService{
    @Autowired IUsuariosRepository usuariosRepository;
    @Autowired UsuarioMapper usuarioMapper;
    @Override
    public UsuarioResponseDTO guardaUsuario(UsuarioCreateDTO usuario) {
        UsuariosModel usuariosModel = usuarioMapper.toModel(usuario);
        usuariosRepository.save(usuariosModel);
        return usuarioMapper.toResponseDTO(usuariosModel);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioMapper.toResponseDTOList(usuariosRepository.findAll());
    }
    @Override
    public UsuarioResponseDTO findById(ObjectId id) {
        UsuariosModel usuariosModel = usuariosRepository.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponseDTO(usuariosModel);
    }
    @Override
    public UsuarioResponseDTO actualizarUsuario(ObjectId id, ActualizarUsuarioDTO usuario){
        UsuariosModel usuariosModel = usuariosRepository.findById(id)
            .orElseThrow(()-> new RecursoNoEncontradoException(
               "Error! El usuario con el ID " + id + " no existe." 
            ));
            usuariosModel.setNombreCompleto(usuario.getNombreCompleto());
            usuariosModel.setEdad(usuario.getEdad());
            usuariosModel.setOcupacion(usuario.getOcupacion());
            usuariosModel.setTelefono(usuario.getTelefono());
            usuariosModel.setCorreo(usuario.getCorreo());
            usuariosModel.setRol(usuario.getRol());
            usuariosModel.setFechaRegistro(usuario.getFechaRegistro());
    
    UsuariosModel saved = usuariosRepository.save(usuariosModel);
    return usuarioMapper.toResponseDTO(saved);
    }
    @Override
     public void eliminarUsuarioPorId(ObjectId id) {
        UsuariosModel usuario = usuariosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "Error! El Usuario con el ID " + id + " no existe."
            ));
        usuariosRepository.delete(usuario);
    }
}
