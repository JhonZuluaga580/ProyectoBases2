package com.apirest.backendClub.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.ForosModel;
import com.apirest.backendClub.Repository.IForosRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

@Service
public class ForosServiceImp implements IForosService{
    @Autowired IForosRepository forosRepository;
    @Autowired IUsuariosRepository usuariosRepository;

    @Override
    public ForosModel crearForo(ForosModel foro) {
        // Validaciones básicas del moderador embebido
        if (foro.getModerador() == null || foro.getModerador().getUsuarioId() == null) {
            throw new RecursoNoEncontradoException("Se requiere un moderador para crear el foro");
        }

        // Recuperar el usuario completo desde la colección de usuarios
        var usuarioOptional = usuariosRepository.findById(foro.getModerador().getUsuarioId());
        var usuario = usuarioOptional.orElseThrow(
            () -> new RecursoNoEncontradoException("El usuario moderador no existe")
        );

        // Verificar rol: solo 'moderador' puede crear foros
        if (usuario.getRol() == null || !"moderador".equalsIgnoreCase(usuario.getRol())) {
            throw new RecursoNoEncontradoException("El usuario no tiene el rol de moderador");
        }

        // Asegurar que el moderador embebido tenga el nombre completo tomado del usuario
        foro.getModerador().setNombreCompleto(usuario.getNombreCompleto());

        // Establecer la fecha de publicación
        foro.setFechaPublicacion(Instant.now());
        // Establecer estado por defecto si no viene en la petición (schema de Mongo lo requiere)
        

        return forosRepository.save(foro);
    }

    @Override
    public List<ForosModel> listarForos() {
        throw new UnsupportedOperationException("Unimplemented method 'listarForos'");
    }
    
}
