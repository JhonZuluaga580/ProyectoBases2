package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.Reunionesembb.Invitado;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;
import com.apirest.backendClub.Service.IReunionesService;

@RestController
@RequestMapping("/UAO/apirest/reuniones")
public class ReunionesController {

    @Autowired
    private IReunionesService reunionesService;

    @PostMapping("/insertar")
    public ResponseEntity<ReunionesModel> crearReunion(@RequestBody ReunionesModel reunion) {
        return new ResponseEntity<>(reunionesService.crearReunion(reunion), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReunionesModel>> listarReuniones() {
        return new ResponseEntity<>(reunionesService.listarReuniones(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReunionesModel> obtenerPorId(@PathVariable String id) {
        return new ResponseEntity<>(reunionesService.obtenerPorId(new ObjectId(id)), HttpStatus.OK);
    }

    @PatchMapping("/{id}/asistir")
    public ResponseEntity<ReunionesModel> registrarAsistencia(
            @PathVariable String id,
            @RequestBody Invitado invitado) {
        return new ResponseEntity<>(
                reunionesService.registrarInvitado(new ObjectId(id), invitado),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}/agregar-libro")
    public ResponseEntity<ReunionesModel> agregarLibroDiscutir(
            @PathVariable String id,
            @RequestBody LibroDiscutir libro) {
        return new ResponseEntity<>(
                reunionesService.agregarLibroDiscutir(new ObjectId(id), libro),
                HttpStatus.OK);
    }
}