package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.backendClub.DTO.RetoCreateDTO;
import com.apirest.backendClub.DTO.RetoResponseDTO;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.RetoMapper;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Repository.ILibrosRepository;
import com.apirest.backendClub.Repository.IRetosRepository;

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
        // Validar que los libros asociados existan
        if (reto.getListaLibrosAsociados() != null) {
            for (var libroDTO : reto.getListaLibrosAsociados()) {
                ObjectId libroId = new ObjectId(libroDTO.getLibroId());
                if (!librosRepository.existsById(libroId)) {
                    throw new RecursoNoEncontradoException("El libro con ID " + libroId + " no existe");
                }
            }
        }
        
        RetosModel model = retoMapper.toModel(reto);
        RetosModel saved = retosRepository.save(model);
        return retoMapper.toResponseDTO(saved);
    }

    @Override
    public List<RetoResponseDTO> listarRetos() {
        List<RetosModel> retos = retosRepository.findAll();
        return retoMapper.toResponseDTOList(retos);
    }

    @Override
    public RetoResponseDTO buscarRetoPorId(ObjectId id) {
        RetosModel reto = retosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado con ID: " + id));
        return retoMapper.toResponseDTO(reto);
    }

    @Override
    public RetoResponseDTO actualizarReto(ObjectId id, RetoCreateDTO reto) {
        RetosModel existente = retosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado con ID: " + id));
        
        // Actualizar campos
        existente.setTitulo(reto.getTitulo());
        existente.setDescripcion(reto.getDescripcion());
        existente.setFechaInicio(reto.getFechaInicio());
        existente.setFechaFinalizacion(reto.getFechaFinalizacion());
        
        RetosModel actualizado = retosRepository.save(existente);
        return retoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminarReto(ObjectId id) {
        RetosModel reto = retosRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado con ID: " + id));
        retosRepository.delete(reto);
    }
}