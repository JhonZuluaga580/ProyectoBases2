package com.apirest.backendClub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;
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
    }
    

