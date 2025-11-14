package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.ComentarioCreateDTO;
import com.apirest.backendClub.DTO.ComentarioResponseDTO;
import com.apirest.backendClub.Service.IComentariosService;

@RestController
@RequestMapping("/UAO/apirest/comentarios")
public class ComentarioController {

  @Autowired
  private IComentariosService comentariosService;

  @PostMapping("/insertar")
  public ResponseEntity<ComentarioResponseDTO> crear(@RequestBody ComentarioCreateDTO coment){
    
    ComentarioResponseDTO dto = comentariosService.crearComentario(coment);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

    @GetMapping("/foro/{foroId}/arbol")
public ResponseEntity<List<ComentarioResponseDTO>> listarArbol(@PathVariable String foroId) {
    List<ComentarioResponseDTO> dto = comentariosService.listarArbolPorForo(new ObjectId(foroId));
    return ResponseEntity.ok(dto);
}
    @GetMapping("/respuestas/{parentId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarRespuestas(@PathVariable String parentId) {
      List<ComentarioResponseDTO> resp = comentariosService.listarRespuestas(new ObjectId(parentId));
      return ResponseEntity.ok(resp);
}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        comentariosService.eliminarComentario(new ObjectId(id));
        return ResponseEntity.noContent().build();
}

}