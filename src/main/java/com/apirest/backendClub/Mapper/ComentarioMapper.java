package com.apirest.backendClub.Mapper;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.apirest.backendClub.DTO.AutorDTO;
import com.apirest.backendClub.DTO.ComentarioCreateDTO;
import com.apirest.backendClub.DTO.ComentarioResponseDTO;
import com.apirest.backendClub.Model.AutorComentarios;
import com.apirest.backendClub.Model.ComentariosModel;
@Component
public class ComentarioMapper {
    private ObjectId oid(String s) {
        return (s != null && ObjectId.isValid(s)) ? new ObjectId(s) : null;
    }
    public ComentariosModel toModel(ComentarioCreateDTO dto){
        ComentariosModel model = new ComentariosModel();
        model.setForoId(oid(dto.getForoId()));
        model.setComentario(dto.getComentario());
        model.setParentId(oid(dto.getParentId()));        
        if (dto.getAutor() != null) {
            AutorDTO adto = dto.getAutor();
            AutorComentarios autorM = new AutorComentarios();
            autorM.setUsuarioId(oid(adto.getUsuarioId()));    // String → ObjectId
            autorM.setNombreCompleto(adto.getNombreCompleto());
            model.setAutor(autorM);
        }
        model.setFechaPublicacion(dto.getFechaPublicacion());
        model.setEstado(dto.getEstado());

        return model;
    }
    private String hex(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }
    public ComentarioResponseDTO toResponseDTO(ComentariosModel model){
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setId(hex(model.getId()));
        dto.setForoId(hex(model.getForoId()));
        dto.setComentario(model.getComentario());
        dto.setParentId(hex(model.getParentId()));
        
        if (model.getAutor() != null) {
            AutorComentarios autor = model.getAutor();
            dto.setAutor(new AutorDTO(hex(autor.getUsuarioId()), autor.getNombreCompleto()));
        }

        dto.setFechaPublicacion(model.getFechaPublicacion());
        dto.setEstado(model.getEstado());
        return dto;
    }
    public List<ComentarioResponseDTO> toResponseDTOList(List<ComentariosModel> models){
        return models .stream()
        .map(this::toResponseDTO)
        .toList();
}
}