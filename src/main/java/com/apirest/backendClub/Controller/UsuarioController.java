package com.apirest.backendClub.Controller;

import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.ActualizarUsuarioDTO;
import com.apirest.backendClub.DTO.UsuarioCreateDTO;
import com.apirest.backendClub.DTO.UsuarioResponseDTO;
import com.apirest.backendClub.Service.IUsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




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
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarLibro(@PathVariable String id, @RequestBody ActualizarUsuarioDTO usuario) {
        return new ResponseEntity<>(usuariosService.actualizarUsuario(new ObjectId(id), usuario), HttpStatus.OK);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuarioPorId(@PathVariable String id) {
        usuariosService.eliminarUsuarioPorId(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
    }
    
    

