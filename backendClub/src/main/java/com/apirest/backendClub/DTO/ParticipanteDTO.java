package com.apirest.backendClub.DTO;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.Retosembb.Progreso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParticipanteDTO {
    private ObjectId usuarioId;  
    private List<Progreso> listaProgresos = new ArrayList<>();
}
