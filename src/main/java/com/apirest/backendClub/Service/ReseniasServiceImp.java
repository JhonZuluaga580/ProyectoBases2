package com.apirest.backendClub.Service;


import org.springframework.stereotype.Service;

import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Model.ReseniasModel;
import com.apirest.backendClub.Repository.ILibrosRepository;
import com.apirest.backendClub.Repository.IReseniasRepository;
import com.apirest.backendClub.Repository.IUsuariosRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ReseniasServiceImp implements IReseniasService {
    private final IReseniasRepository repo;
    private final ILibrosRepository librosRepo;
    private final IUsuariosRepository usuariosRepo;
    @Override
    public ReseniasModel crearResenia(ReseniasModel r) {
        // Extraer los ObjectId de los documentos embebidos
        if (r.getLibro() == null || r.getLibro().getLibroId() == null) {
            throw new IllegalArgumentException("El id del libro es requerido");
        }
        if (r.getUsuario() == null || r.getUsuario().getUsuarioId() == null) {
            throw new IllegalArgumentException("El id del usuario es requerido");
        }

        org.bson.types.ObjectId libroId = r.getLibro().getLibroId();
        org.bson.types.ObjectId usuarioId = r.getUsuario().getUsuarioId();

        if (!librosRepo.existsById(libroId))
            throw new RecursoNoEncontradoException("El libro no existe");
        if (!usuariosRepo.existsById(usuarioId))
            throw new RecursoNoEncontradoException("El usuario no existe");

        // Usa el método derivado para propiedades embebidas
        if (repo.existsByLibroLibroIdAndUsuarioUsuarioId(libroId, usuarioId))
            throw new IllegalStateException("Ese usuario ya reseñó ese libro");

        // (Opcional) asignar fecha de publicación si el modelo la soporta
        // r.setFechaPublicacion(Date.from(Instant.now()));

        return repo.save(r);
    }
    
}
