package com.apirest.backendClub.Service;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.RetoMapper;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Repository.*;

@Service
public class RetosServiceImp implements IRetosService {
    
    @Autowired
    private IRetosRepository retosRepository;
    
    @Autowired
    private ILibrosRepository librosRepository;
    
    @Autowired
    private RetoMapper retoMapper;

    @Override
    public RetoResponseDTO crearReto(RetoCreateDTO reto) {
        if (reto.getListaLibrosAsociados() != null) {
            for (var libroDTO : reto.getListaLibrosAsociados()) {
                ObjectId libroId = new ObjectId(libroDTO.getLibroId());
                if (!librosRepository.existsById(libroId)) {
                    throw new RecursoNoEncontradoException("Libro no existe: " + libroId);
                }
            }
        }
        
        RetosModel model = retoMapper.toModel(reto);
        RetosModel saved = retosRepository.save(model);
        return retoMapper.toResponseDTO(saved);
    }

    @Override
    public List<RetoResponseDTO> listarRetos() {
        return retoMapper.toResponseDTOList(retosRepository.findAll());
    }

    @Override
    public RetoResponseDTO buscarRetoPorId(ObjectId id) {
        RetosModel reto = retosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado: " + id));
        return retoMapper.toResponseDTO(reto);
    }

    @Override
    public RetoResponseDTO actualizarReto(ObjectId id, RetoCreateDTO reto) {
        RetosModel existente = retosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado: " + id));
        
        existente.setTitulo(reto.getTitulo());
        existente.setDescripcion(reto.getDescripcion());
        existente.setFechaInicio(reto.getFechaInicio());
        existente.setFechaFinalizacion(reto.getFechaFinalizacion());
        
        return retoMapper.toResponseDTO(retosRepository.save(existente));
    }

    @Override
    public void eliminarReto(ObjectId id) {
        if (!retosRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Reto no encontrado: " + id);
        }
        retosRepository.deleteById(id);
    }
}