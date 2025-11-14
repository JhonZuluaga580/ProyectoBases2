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

import com.apirest.backendClub.DTO.ActualizarLibroDTO;
import com.apirest.backendClub.DTO.LibroCreateDTO;
import com.apirest.backendClub.DTO.LibroResponseDTO;
import com.apirest.backendClub.Service.ILibrosService;

@RestController
@RequestMapping ("/UAO/apirest/libros")
public class LibroController {
    @Autowired ILibrosService librosService;

    @PostMapping("/insertar")
    public ResponseEntity<LibroResponseDTO> crearLibro(@RequestBody LibroCreateDTO libro){
        return new ResponseEntity<LibroResponseDTO>(librosService.crearLibro(libro), HttpStatus.CREATED);
    }
    @GetMapping("/listar")
    ResponseEntity<List<LibroResponseDTO>> listarLibros(){
        return new ResponseEntity<List<LibroResponseDTO>>(librosService.listarLibros(), HttpStatus.OK);
    }
     @GetMapping("/libroporid/{id}")
    public ResponseEntity<LibroResponseDTO> buscarLibrosPorId(@PathVariable String id) {
    return new ResponseEntity<>(librosService.buscarLibrosPorId(new ObjectId(id)),HttpStatus.OK);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<LibroResponseDTO> actualizarLibro(@PathVariable String id, @RequestBody ActualizarLibroDTO libro){
        return new ResponseEntity<>(librosService.actualizarLibro(new ObjectId(id), libro),  HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarLibroPorId(@PathVariable String id) {
    librosService.eliminarLibroPorId(new ObjectId(id));
    return ResponseEntity.noContent().build(); // 204
}
}