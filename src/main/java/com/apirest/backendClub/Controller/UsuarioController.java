package com.apirest.backendClub.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;
import com.apirest.backendClub.Service.IUsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping ("/UAO/apirest/usuarios")

public class UsuarioController {
    @Autowired IUsuariosService usuariosService;
    @PostMapping("/insertar")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioCreateDTO usuario){
        return new ResponseEntity<UsuarioResponseDTO>(usuariosService.guardaUsuario(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/Listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return new ResponseEntity<List<UsuarioResponseDTO>>(usuariosService.listarUsuarios(), HttpStatus.OK);
    }


    }
    
    

