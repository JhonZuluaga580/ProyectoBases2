package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;

public interface IUsuariosService {
    public UsuarioResponseDTO guardaUsuario(UsuarioCreateDTO usuario);
    public List<UsuarioResponseDTO> listarUsuarios();
    UsuarioResponseDTO findById(ObjectId id);
    /* 
    public UsuariosModel buscarEmpleadosPorId(ObjectId id);
    public UsuariosModel actualizarUsuario(ObjectId id, UsuariosModel usuario);
    public String eliminarEmpleado(ObjectId id);*/
}
