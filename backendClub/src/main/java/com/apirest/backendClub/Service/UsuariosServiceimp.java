package com.apirest.backendClub.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Repository.IUsuariosRepository;
@Service

public class UsuariosServiceimp implements IUsuariosService{
    @Autowired IUsuariosRepository usuariosRepository;
    @Override
    public UsuariosModel guardaUsuario(UsuariosModel usuario) {
        return usuariosRepository.save(usuario);
    }

    @Override
    public List<UsuariosModel> listarUsuarios() {
        return usuariosRepository.findAll();
    }
    
}
