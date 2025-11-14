package com.apirest.backendClub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.apirest.backendClub.Model.ReseniasModel;
import com.apirest.backendClub.Service.IReseniasService;

@RestController
@RequestMapping ("/UAO/apirest/resenias")
public class ReseniaController {
   @Autowired IReseniasService reseniasService;

    @PostMapping("/insertar")
    ResponseEntity<ReseniasModel> crearLibro(@RequestBody ReseniasModel resenia){
        return new ResponseEntity<ReseniasModel>(reseniasService.crearResenia(resenia), HttpStatus.CREATED);
    } 
}
