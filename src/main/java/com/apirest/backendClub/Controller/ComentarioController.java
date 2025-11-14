package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.Model.AutorComentarios;
import com.apirest.backendClub.Model.ComentariosModel;
import com.apirest.backendClub.Service.IComentariosService;

@RestController
@RequestMapping("/UAO/apirest/comentarios")
public class ComentarioController {

  @Autowired
  private IComentariosService comentariosService;

  @PostMapping("/insertar")
  public ResponseEntity<ComentariosModel> crear(
      @RequestBody ComentariosModel body,
      @RequestParam String usuarioId,
      @RequestParam String foroId) {

    ObjectId uId = new ObjectId(usuarioId);
    ObjectId fId = new ObjectId(foroId);

    body.setForoId(fId);

    AutorComentarios autor = new AutorComentarios();
    autor.setUsuarioId(uId);
    body.setAutor(autor);

    ComentariosModel creado = comentariosService.crearComentario(body);
    return new ResponseEntity<>(creado, HttpStatus.CREATED);
  }

    @GetMapping("/por-foro")
    public ResponseEntity<List<ComentariosModel>> listarPorForo(@RequestParam String foroId) {
    return ResponseEntity.ok(comentariosService.listarComentariosPorForo(new ObjectId(foroId)));
  }

    @GetMapping("/respuestas")
    public ResponseEntity<List<ComentariosModel>> listarRespuestas(@RequestParam String parentId) {
    return ResponseEntity.ok(comentariosService.listarRespuestas(new ObjectId(parentId)));
  }

}