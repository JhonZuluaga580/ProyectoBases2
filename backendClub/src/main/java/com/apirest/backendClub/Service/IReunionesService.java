package com.apirest.backendClub.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.apirest.backendClub.Model.ReunionesModel;
import com.apirest.backendClub.Model.Reunionesembb.Invitado;
import com.apirest.backendClub.Model.Reunionesembb.LibroDiscutir;


public interface IReunionesService {

    ReunionesModel crearReunion(ReunionesModel reunion);
    List<ReunionesModel> listarReuniones();
    ReunionesModel obtenerPorId(ObjectId id);
    ReunionesModel registrarInvitado(ObjectId reunionId, Invitado invitado);
    ReunionesModel agregarLibroDiscutir(ObjectId reunionId, LibroDiscutir libro);
}