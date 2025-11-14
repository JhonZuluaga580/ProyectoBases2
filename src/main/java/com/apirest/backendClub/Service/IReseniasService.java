package com.apirest.backendClub.Service;

import com.apirest.backendClub.DTO.ReseniaResponseDTO;
import com.apirest.backendClub.DTO.ReseniasCreateDTO;
import com.apirest.backendClub.DTO.ValoracionResponseDTO;
import com.apirest.backendClub.DTO.ValorarRequestDTO;

public interface IReseniasService {
    ReseniaResponseDTO crearResenia(ReseniasCreateDTO resenia);
    ValoracionResponseDTO valorar(String reseniaId, ValorarRequestDTO dto);

}
