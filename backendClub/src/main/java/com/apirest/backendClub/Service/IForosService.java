package com.apirest.backendClub.Service;

import java.util.List;

import com.apirest.backendClub.Model.ForosModel;


public interface IForosService {
 ForosModel crearForo(ForosModel foro);
    List<ForosModel> listarForos();   
}
