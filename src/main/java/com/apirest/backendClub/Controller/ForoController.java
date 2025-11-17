package com.apirest.backendClub.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.apirest.backendClub.DTO.ActualizarForoDTO;
import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
import com.apirest.backendClub.DTO.ForoStatsDTO;
import com.apirest.backendClub.Service.IForosService;


@RestController
@RequestMapping ("/UAO/apirest/foros")
public class ForoController {
    @Autowired 
    private IForosService forosService;
    
    @PostMapping("/insertar")
    public ResponseEntity<ForoResponseDTO> crearForo(@RequestBody ForoCreateDTO body) {
    ForoResponseDTO res = forosService.crearForo(body);
    return new ResponseEntity<>(res, HttpStatus.CREATED);
}

    @GetMapping("/listar")
    public ResponseEntity<List<ForoResponseDTO>> listarForos(){
        return new ResponseEntity<>(forosService.listarForos(), HttpStatus.OK);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ForoResponseDTO> actualizarForo(@PathVariable String id, @RequestBody ActualizarForoDTO foro){
        return new ResponseEntity<>(forosService.actualizarForo(new ObjectId(id), foro),  HttpStatus.OK);
    }
     @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarForoPorId(@PathVariable String id) {
        forosService.eliminarForoPorId(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
     @GetMapping("/stats")
    public ResponseEntity<List<ForoStatsDTO>> listarForosConEstadisticas() {
        List<ForoStatsDTO> lista = forosService.obtenerForosConEstadisticas();
        return ResponseEntity.ok(lista);
    }
    }
    

