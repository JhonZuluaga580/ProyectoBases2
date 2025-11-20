package com.apirest.backendClub.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apirest.backendClub.DTO.*;
import com.apirest.backendClub.Exception.Exception.RecursoNoEncontradoException;
import com.apirest.backendClub.Mapper.RetoMapper;
import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.LibroAsociado;
import com.apirest.backendClub.Model.Retosembb.Participante;
import com.apirest.backendClub.Model.Retosembb.Progreso;
import com.apirest.backendClub.Repository.*;

@Service
public class RetosServiceImp implements IRetosService {
    
    @Autowired
    private IRetosRepository retosRepository;
    
    @Autowired
    private ILibrosRepository librosRepository;
      
    @Autowired
    private IUsuariosRepository usuariosRepository;

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
      @Override
    public RetoResponseDTO inscribirUsuarioEnReto(ObjectId retoId, ObjectId usuarioId) {
        RetosModel reto = retosRepository.findById(retoId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado con ID: " + retoId));
        
        if (!usuariosRepository.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }
        
        Participante participante = new Participante();
        participante.setUsuarioId(usuarioId);
        
        List<Progreso> progresoInicial = new ArrayList<>();
        for (LibroAsociado libro : reto.getListaLibrosAsociados()) {
            Progreso progreso = new Progreso();
            progreso.setLibroId(libro.getLibroId());
            progreso.setPorcentaje("0");
            progreso.setEstado("no iniciado");
            progresoInicial.add(progreso);
        }
        participante.setProgreso(progresoInicial);
        
        if (reto.getParticipantes() == null) {
            reto.setParticipantes(new ArrayList<>());
        }
        reto.getParticipantes().add(participante);
        
        RetosModel actualizado = retosRepository.save(reto);
        return retoMapper.toResponseDTO(actualizado);
    }
     @Override
    public RetoResponseDTO actualizarProgresoUsuario(ObjectId retoId, ObjectId usuarioId, ActualizarProgresoDTO progresoDTO) {
        RetosModel reto = retosRepository.findById(retoId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reto no encontrado con ID: " + retoId));
        
        Optional<Participante> participanteOpt = reto.getParticipantes().stream()
            .filter(p -> p.getUsuarioId().equals(usuarioId))
            .findFirst();
        
        if (!participanteOpt.isPresent()) {
            throw new RecursoNoEncontradoException("El usuario no está inscrito en este reto");
        }
        
        Participante participante = participanteOpt.get();
        ObjectId libroId = new ObjectId(progresoDTO.getLibroId());
        
        Optional<Progreso> progresoOpt = participante.getProgreso().stream()
            .filter(p -> p.getLibroId().equals(libroId))
            .findFirst();
        
        if (progresoOpt.isPresent()) {
            Progreso progreso = progresoOpt.get();
            progreso.setPorcentaje(progresoDTO.getPorcentaje());
            progreso.setEstado(progresoDTO.getEstado());
        } else {
            Progreso nuevoProgreso = new Progreso();
            nuevoProgreso.setLibroId(libroId);
            nuevoProgreso.setPorcentaje(progresoDTO.getPorcentaje());
            nuevoProgreso.setEstado(progresoDTO.getEstado());
            participante.getProgreso().add(nuevoProgreso);
        }
        
        RetosModel actualizado = retosRepository.save(reto);
        return retoMapper.toResponseDTO(actualizado);
    }
    @Override
    public List<RetoStatsDTO> obtenerRetosConEstadisticas() {
        return retosRepository.obtenerRetosConEstadisticas();
    }
}