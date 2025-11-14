package com.apirest.backendClub.Service;


import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.ReseniaResponseDTO;
import com.apirest.backendClub.DTO.ReseniasCreateDTO;
import com.apirest.backendClub.DTO.ValoracionResponseDTO;
import com.apirest.backendClub.DTO.ValorarRequestDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.ReseniaMapper;
import com.apirest.backendClub.Model.ReseniasModel;
import com.apirest.backendClub.Model.Reseniasembb.ValoracionItem;
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
    private final ReseniaMapper mapper;
    @Override
    public ReseniaResponseDTO crearResenia(ReseniasCreateDTO resenia) {
        // Validar existencia de libro y usuario
        ObjectId libroId = new ObjectId(resenia.getLibro().getLibroId());
        ObjectId usuarioId = new ObjectId(resenia.getUsuario().getUsuarioId());

        if(!librosRepo.existsById(libroId))
            throw new RecursoNoEncontradoException("el libro no existe");
        
        if(!usuariosRepo.existsById(usuarioId))
            throw new RecursoNoEncontradoException("el usuario no existe");
        // Evitar duplicado (una reseña por libro-usuario)
        if(repo.existsByLibroLibroIdAndUsuarioUsuarioId(libroId, usuarioId))
        throw new IllegalStateException("Ese usuario ya reseñó ese libro");

    ReseniasModel model = mapper.toModel(resenia);
    model = repo.save(model);
    return mapper.toResponseDTO(model);

    }
    //estudiar
    @Override
    public ValoracionResponseDTO valorar(String reseniaId, ValorarRequestDTO body) {
    var rId = new ObjectId(reseniaId);
    var uId = new ObjectId(body.getUsuarioId());

    var resenia = repo.findById(rId)
        .orElseThrow(() -> new RecursoNoEncontradoException("Reseña no existe"));

    if (!usuariosRepo.existsById(uId))
        throw new RecursoNoEncontradoException("Usuario no existe");

    // evitar doble voto
    boolean yaValoro = resenia.getValoracion().stream()
        .anyMatch(v -> uId.equals(v.getUsuarioId()));

    if (!yaValoro) {
        resenia.getValoracion().add(new ValoracionItem(uId, java.time.LocalDateTime.now()));
        repo.save(resenia);
    }

    int total = resenia.getValoracion() != null ? resenia.getValoracion().size() : 0;
    return new ValoracionResponseDTO(resenia.getId().toHexString(), total);
}

    
}
