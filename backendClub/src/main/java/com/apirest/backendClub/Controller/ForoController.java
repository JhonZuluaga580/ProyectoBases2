package com.apirest.backendClub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;

import com.apirest.backendClub.DTO.UsuarioResponseDTO;
import com.apirest.backendClub.Model.ForosModel;
import com.apirest.backendClub.Model.ModeradorForos;
import com.apirest.backendClub.Service.IForosService;
import com.apirest.backendClub.Service.IUsuariosService;

@RestController
@RequestMapping ("/UAO/apirest/foros")
public class ForoController {
    @Autowired 
    private IForosService forosService;
    
    @Autowired
    private IUsuariosService usuariosService;

    @PostMapping("/insertar")
    ResponseEntity<ForosModel> crearForo(
            @RequestBody ForosModel foro,
            @RequestParam String usuarioModeradorId) {
        // Convertir string a ObjectId y validar
        ObjectId usuarioId;
        try {
            usuarioId = new ObjectId(usuarioModeradorId);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Buscar el usuario 
        UsuarioResponseDTO usuario = usuariosService.findById(usuarioId);
        //Verificar que el usuario es moderador si lo es debo recuperar los datos  del usuario y crear el foro en caso contrario debe saltar un mensaje de error
        // Crear el objeto moderador con los datos del usuario
        ModeradorForos moderador = new ModeradorForos();
        moderador.setUsuarioId(usuarioId);
        moderador.setNombreCompleto(usuario.getNombreCompleto());
        
        // Asignar el moderador al foro
        foro.setModerador(moderador);
        
        // Crear el foro
        ForosModel foroCreado = forosService.crearForo(foro);
        return new ResponseEntity<>(foroCreado, HttpStatus.CREATED);
    }
}
