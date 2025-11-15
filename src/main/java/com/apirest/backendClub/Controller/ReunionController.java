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

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;
import com.apirest.backendClub.Service.IReunionesService;


@RestController
@RequestMapping("/UAO/apirest/reuniones")
public class ReunionController {
    
    @Autowired
    private IReunionesService reunionesService;
    
    //Crea una nueva reunión.
    @PostMapping("/insertar")
    public ResponseEntity<ReunionResponseDTO> crearReunion(@RequestBody ReunionCreateDTO reunion) {
        ReunionResponseDTO creada = reunionesService.crearReunion(reunion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }
    
    //Lista todas las reuniones.
    @GetMapping("/listar")
    public ResponseEntity<List<ReunionResponseDTO>> listarReuniones() {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReuniones();
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    
    //Actualiza una reunión existente.
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ReunionResponseDTO> actualizarReunion(
            @PathVariable String id, 
            @RequestBody ReunionCreateDTO reunion) {
        ReunionResponseDTO actualizada = reunionesService.actualizarReunion(
            new ObjectId(id), 
            reunion
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
    //Elimina una reunión.
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReunion(@PathVariable String id) {
        reunionesService.eliminarReunion(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }

}