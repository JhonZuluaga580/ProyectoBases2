package com.apirest.backendClub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.ReseniaResponseDTO;
import com.apirest.backendClub.DTO.ReseniasCreateDTO;
import com.apirest.backendClub.DTO.ValoracionResponseDTO;
import com.apirest.backendClub.DTO.ValorarRequestDTO;
import com.apirest.backendClub.Service.IReseniasService;

@RestController
@RequestMapping ("/UAO/apirest/resenias")
public class ReseniaController {
   @Autowired IReseniasService reseniasService;

    @PostMapping("/insertar")
    ResponseEntity<ReseniaResponseDTO> crearLibro(@RequestBody ReseniasCreateDTO resenia){
        return new ResponseEntity<ReseniaResponseDTO>(reseniasService.crearResenia(resenia), HttpStatus.CREATED);
    } 
    @PostMapping("/{reseniaId}/valorar")
public ResponseEntity<ValoracionResponseDTO> valorar(
        @PathVariable String reseniaId,
        @RequestBody ValorarRequestDTO body) {
    return new ResponseEntity<>(reseniasService.valorar(reseniaId, body), HttpStatus.OK);
}

}
