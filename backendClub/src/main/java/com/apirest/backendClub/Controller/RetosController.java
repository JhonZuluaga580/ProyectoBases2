package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apirest.backendClub.Model.RetosModel;
import com.apirest.backendClub.Model.Retosembb.ParticipanteReto;
import com.apirest.backendClub.Model.Retosembb.ProgresoLibro;
import com.apirest.backendClub.Service.IRetosService;

@RestController
@RequestMapping("/UAO/apirest/retos")
public class RetosController {

    @Autowired
    private IRetosService retosService;

    @PostMapping("/insertar")
    public ResponseEntity<RetosModel> crearReto(@RequestBody RetosModel reto) {
        return new ResponseEntity<>(retosService.crearReto(reto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RetosModel>> listarRetos() {
        return new ResponseEntity<>(retosService.listarRetos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetosModel> obtenerPorId(@PathVariable String id) {
        return new ResponseEntity<>(retosService.obtenerPorId(new ObjectId(id)), HttpStatus.OK);
    }

    @PatchMapping("/{id}/agregar-participante")
    public ResponseEntity<RetosModel> agregarParticipante(
            @PathVariable String id,
            @RequestBody ParticipanteReto participante) {
        return new ResponseEntity<>(
                retosService.agregarParticipante(new ObjectId(id), participante),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}/progreso")
    public ResponseEntity<RetosModel> actualizarProgreso(
            @PathVariable String id,
            @RequestParam String usuarioId,
            @RequestBody ProgresoLibro progreso) {
        return new ResponseEntity<>(
                retosService.actualizarProgreso(new ObjectId(id), new ObjectId(usuarioId), progreso),
                HttpStatus.OK);
    }
}