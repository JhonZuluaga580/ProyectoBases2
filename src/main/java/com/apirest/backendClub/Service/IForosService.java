package com.apirest.backendClub.Service;

import java.util.List;

import com.apirest.backendClub.DTO.ForoCreateDTO;
import com.apirest.backendClub.DTO.ForoResponseDTO;


public interface IForosService {
 ForoResponseDTO crearForo(ForoCreateDTO foro);
    List<ForoResponseDTO> listarForos();   
}
