package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;
import com.apirest.backendClub.Service.ILibrosPropuestosService;

/**
 * Controlador REST para gestionar libros propuestos.
 * Expone endpoints para propuestas, votaciones y consultas.
 */
@RestController
@RequestMapping("/UAO/apirest/propuestas")
public class LibroPropuestoController {
    
    @Autowired
    private ILibrosPropuestosService propuestasService;
    
    //Crea una nueva propuesta de libro.
    @PostMapping("/insertar")
    public ResponseEntity<LibroPropuestoResponseDTO> crearPropuesta(
            @RequestBody LibroPropuestoCreateDTO propuesta) {
        LibroPropuestoResponseDTO creada = propuestasService.crearPropuesta(propuesta);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }
    
    //Lista todas las propuestas.
    @GetMapping("/listar")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestas() {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestas();
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }

    //Actualiza el estado de una propuesta.
    @PatchMapping("/actualizar-estado/{id}")
    public ResponseEntity<LibroPropuestoResponseDTO> actualizarEstado(
            @PathVariable String id,
            @RequestParam String estado) {
        LibroPropuestoResponseDTO actualizada = propuestasService.actualizarEstado(
            new ObjectId(id), 
            estado
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
    //Elimina una propuesta.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPropuesta(@PathVariable String id) {
        propuestasService.eliminarPropuesta(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
    
    //Registra un voto en una propuesta.
    @PostMapping("/votar/{id}")
    public ResponseEntity<LibroPropuestoResponseDTO> votarPropuesta(
            @PathVariable String id,
            @RequestBody VotarDTO voto) {
        LibroPropuestoResponseDTO actualizada = propuestasService.votarPropuesta(
            new ObjectId(id), 
            voto
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
}