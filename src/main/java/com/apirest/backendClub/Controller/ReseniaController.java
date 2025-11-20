package com.apirest.backendClub.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.ComentarioOpinionDTO;
import com.apirest.backendClub.DTO.ReseniaResponseDTO;
import com.apirest.backendClub.DTO.ReseniaTopDTO;
import com.apirest.backendClub.DTO.ReseniasCreateDTO;
import com.apirest.backendClub.DTO.ValoracionResponseDTO;
import com.apirest.backendClub.DTO.ValorarRequestDTO;
import com.apirest.backendClub.Service.IReseniasService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/UAO/apirest/resenias")
public class ReseniaController {
    @Autowired
    IReseniasService reseniasService;

    @PostMapping("/insertar")
    ResponseEntity<ReseniaResponseDTO> crearLibro(@RequestBody ReseniasCreateDTO resenia) {
        return new ResponseEntity<ReseniaResponseDTO>(reseniasService.crearResenia(resenia), HttpStatus.CREATED);
    }

    @PostMapping("/{reseniaId}/valorar")
    public ResponseEntity<ValoracionResponseDTO> valorar(
            @PathVariable String reseniaId,
            @RequestBody ValorarRequestDTO body) {
        return new ResponseEntity<>(reseniasService.valorar(reseniaId, body), HttpStatus.OK);
    }

    @PostMapping("/{reseniaId}/comentarios")
    public ResponseEntity<ComentarioOpinionDTO> agregarComentario(
            @PathVariable String reseniaId,
            @RequestBody ComentarioOpinionDTO body) {
        ComentarioOpinionDTO dto = reseniasService.agregarComentario(reseniaId, body);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReseniaPorId(@PathVariable String id) {
        reseniasService.eliminarReseniaPorId(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/top/")
    public ResponseEntity<List<ReseniaTopDTO>> listarReseniasMasValoradas() {
        List<ReseniaTopDTO> lista = reseniasService.obtenerReseniasMasValoradas();
        return ResponseEntity.ok(lista);
    }
    
}
