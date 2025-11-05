package com.apirest.backendClub.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.Model.UsuariosModel;
import com.apirest.backendClub.Service.IUsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping ("/UAO/apirest/usuarios")

public class UsuarioController {
    @Autowired IUsuariosService usuariosService;
    @PostMapping("/insertar")
    public  ResponseEntity<UsuariosModel> crearUsuario(@RequestBody UsuariosModel usuario){
         return new ResponseEntity<UsuariosModel>(usuariosService.guardaUsuario(usuario),HttpStatus.CREATED);
    }
    @GetMapping("/Listar")
    public ResponseEntity<List<UsuariosModel>> listarUsuarios(){
        return new ResponseEntity<List<UsuariosModel>>(usuariosService.listarUsuarios(),HttpStatus.OK);
    }
    }
    
    

