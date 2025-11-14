package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.ActualizarUsuarioDTO;
import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;

public interface IUsuariosService {
    public UsuarioResponseDTO guardaUsuario(UsuarioCreateDTO usuario);
    public List<UsuarioResponseDTO> listarUsuarios();
    UsuarioResponseDTO findById(ObjectId id);
    UsuarioResponseDTO actualizarUsuario(ObjectId id, ActualizarUsuarioDTO usuario);
    void eliminarUsuarioPorId(ObjectId id);
}
