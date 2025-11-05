package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apirest.backendClub.Model.LibrosPropuestosModel;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Voto;
import com.apirest.backendClub.Service.ILibrosPropuestosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/UAO/apirest/librosPropuestos")
@RequiredArgsConstructor
public class LibrosPropuestosController {

    private final ILibrosPropuestosService service;

    @PostMapping("/insertar")
    public ResponseEntity<LibrosPropuestosModel> crearPropuesta(@RequestBody LibrosPropuestosModel propuesta) {
        return new ResponseEntity<>(service.crearPropuesta(propuesta), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LibrosPropuestosModel>> listarPropuestas() {
        return new ResponseEntity<>(service.listarPropuestas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrosPropuestosModel> obtenerPorId(@PathVariable String id) {
        return new ResponseEntity<>(service.obtenerPorId(new ObjectId(id)), HttpStatus.OK);
    }

    @PatchMapping("/{id}/votar")
    public ResponseEntity<LibrosPropuestosModel> agregarVoto(
            @PathVariable String id,
            @RequestBody Voto voto) {
        return new ResponseEntity<>(service.agregarVoto(new ObjectId(id), voto), HttpStatus.OK);
    }
}