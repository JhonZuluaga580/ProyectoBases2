package com.apirest.backendClub.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.ComentarioOpinionDTO;
import com.apirest.backendClub.DTO.LibroReseniaDTO;
import com.apirest.backendClub.DTO.ReseniaResponseDTO;
import com.apirest.backendClub.DTO.ReseniasCreateDTO;
import com.apirest.backendClub.DTO.UsuarioReseniaDTO;
import com.apirest.backendClub.Model.ReseniasModel;
import com.apirest.backendClub.Model.UsuarioResenia;
import com.apirest.backendClub.Model.Reseniasembb.ComentarioResenia;
import com.apirest.backendClub.Model.Reseniasembb.LibroResenia;
@Component
public class ReseniaMapper {
    public ReseniasModel toModel(ReseniasCreateDTO dto){
        if(dto == null){
            return null;
        }
        ReseniasModel model = new ReseniasModel();
        
        if(dto.getLibro() != null){
            LibroReseniaDTO ldto = dto.getLibro();
            LibroResenia libroM = new LibroResenia();
            if(ldto.getLibroId()!= null)libroM.setLibroId(new ObjectId(ldto.getLibroId()));
            libroM.setNombreLibro(ldto.getNombreLibro());
            model.setLibro(libroM);

        }
        if(dto.getUsuario() != null){
            UsuarioReseniaDTO udto = dto.getUsuario();
            UsuarioResenia usuarioM = new UsuarioResenia();
            if (udto.getUsuarioId() != null) usuarioM.setUsuarioId(new ObjectId(udto.getUsuarioId()));
            usuarioM.setNombreUsuario(udto.getNombreUsuario());
            model.setUsuario(usuarioM);
        }
        model.setCalificacion(dto.getCalificacion());
        model.setOpinion(dto.getOpinion());
        model.setArchivosAdjuntos(dto.getArchivosAdjuntos());

         if (dto.getComentario_opinion() != null) {
            List<ComentarioResenia> lista = dto.getComentario_opinion().stream()
                .map(c -> new ComentarioResenia(
                        c.getUsuarioId() != null ? new ObjectId(c.getUsuarioId()) : null,
                        c.getContenido(),
                        c.getFecha()))
                .collect(Collectors.toList());
            model.setComentario_opinion(lista);
        }
        return model;
}
public ReseniaResponseDTO toResponseDTO(ReseniasModel model){
        if(model == null){
            return null;
        }
        ReseniaResponseDTO dto = new ReseniaResponseDTO();
        
        if (model.getId() != null) dto.setId(model.getId().toHexString());
        
        if (model.getLibro() != null) {
            dto.setLibro(new LibroReseniaDTO(
                model.getLibro().getLibroId() != null ? model.getLibro().getLibroId().toHexString() : null,
                model.getLibro().getNombreLibro()
            ));
}
        if(model.getId()!= null) dto.setId(model.getId().toHexString());
          if(model.getUsuario() != null){
            dto.setUsuario(new UsuarioReseniaDTO(
                model.getUsuario().getUsuarioId()!= null ? model.getUsuario().getUsuarioId().toHexString() : null,
                model.getUsuario().getNombreUsuario()
            ));
}       
        dto.setCalificacion(model.getCalificacion());
        dto.setOpinion(model.getOpinion());
        dto.setArchivosAdjuntos(model.getArchivosAdjuntos());

        if(model.getComentario_opinion() != null){
            List<ComentarioOpinionDTO> lista = model.getComentario_opinion().stream()
                .map(c -> new ComentarioOpinionDTO(
                        c.getUsuarioId() != null ? c.getUsuarioId().toHexString() : null,
                        c.getContenido(),
                        c.getFecha()))
                .collect(Collectors.toList());
            dto.setComentario_opinion(lista);
}
    dto.setUtiles(model.getValoracion() == null ? 0 : model.getValoracion().size());     
       return dto;
}      }
