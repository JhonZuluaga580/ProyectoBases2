package com.apirest.backendClub.Model.Retosembb;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participante {
    private ObjectId usuarioId;
    private List<Progreso> listaProgresos = new ArrayList<>();

}
