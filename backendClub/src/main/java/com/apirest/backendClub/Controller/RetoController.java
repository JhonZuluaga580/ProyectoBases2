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

import com.apirest.backendClub.DTO.RetoCreateDTO;
import com.apirest.backendClub.DTO.RetoResponseDTO;
import com.apirest.backendClub.Service.IRetosService;

@RestController
@RequestMapping("/UAO/apirest/retos")
public class RetoController {
    
    @Autowired
    private IRetosService retosService;
    
    @PostMapping("/insertar")
    public ResponseEntity<RetoResponseDTO> crearReto(@RequestBody RetoCreateDTO reto) {
        RetoResponseDTO creado = retosService.crearReto(reto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<RetoResponseDTO>> listarRetos() {
        List<RetoResponseDTO> retos = retosService.listarRetos();
        return new ResponseEntity<>(retos, HttpStatus.OK);
    }
    
    @GetMapping("/retoportid/{id}")
    public ResponseEntity<RetoResponseDTO> buscarRetoPorId(@PathVariable String id) {
        RetoResponseDTO reto = retosService.buscarRetoPorId(new ObjectId(id));
        return new ResponseEntity<>(reto, HttpStatus.OK);
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RetoResponseDTO> actualizarReto(
            @PathVariable String id, 
            @RequestBody RetoCreateDTO reto) {
        RetoResponseDTO actualizado = retosService.actualizarReto(new ObjectId(id), reto);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReto(@PathVariable String id) {
        retosService.eliminarReto(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
}