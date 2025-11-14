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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.Model.LibrosModel;
import com.apirest.backendClub.Service.ILibrosService;

@RestController
@RequestMapping ("/UAO/apirest/libros")
public class LibroController {
    @Autowired ILibrosService librosService;

    @PostMapping("/insertar")
    ResponseEntity<LibrosModel> crearLibro(@RequestBody LibrosModel libro){
        return new ResponseEntity<LibrosModel>(librosService.crearLibro(libro), HttpStatus.CREATED);
    }
    @GetMapping("/listar")
    ResponseEntity<List<LibrosModel>> listarLibros(){
        return new ResponseEntity<List<LibrosModel>>(librosService.listarLibros(), HttpStatus.OK);
    }
     @GetMapping("/libroporid/{id}")
    public ResponseEntity<LibrosModel> buscarLibrosPorId(@PathVariable ObjectId id){
        return new ResponseEntity<LibrosModel>(librosService.buscarLibrosPorId(id),HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<LibrosModel> actualizarLibro(@PathVariable String id, @RequestBody LibrosModel libro){
        return new ResponseEntity<>(librosService.actualizarLibro(new ObjectId(id), libro),  HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarLibroPorId(@PathVariable String id) {
    librosService.eliminarLibroPorId(new ObjectId(id));
    return ResponseEntity.noContent().build(); // 204
}
/* 
    @GetMapping("/buscar/titulo/{titulo}")
    ResponseEntity<LibrosModel> buscarPorTitulo(@PathVariable String titulo);

    @GetMapping("/buscar/autor/{autor}")
    ResponseEntity<List<LibrosModel>> buscarPorAutor(@PathVariable String autor);

    @GetMapping("/buscar/genero/{genero}")
    ResponseEntity<List<LibrosModel>> buscarPorGenero(@PathVariable String genero);*/
}
