package com.apirest.backendClub.Service;

import java.util.List;


import com.apirest.backendClub.Model.UsuariosModel;

public interface IUsuariosService {
    public UsuariosModel guardaUsuario(UsuariosModel usuario);
    public List<UsuariosModel> listarUsuarios();
    /* 
    public UsuariosModel buscarEmpleadosPorId(ObjectId id);
    public UsuariosModel actualizarUsuario(ObjectId id, UsuariosModel usuario);
    public String eliminarEmpleado(ObjectId id);*/
}
