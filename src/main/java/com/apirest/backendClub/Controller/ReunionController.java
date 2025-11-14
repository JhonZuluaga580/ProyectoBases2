package com.apirest.backendClub.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.ReunionCreateDTO;
import com.apirest.backendClub.DTO.ReunionResponseDTO;
import com.apirest.backendClub.Service.IReunionesService;

/**
 * Controlador REST para gestionar las reuniones del club de lectura.
 * Expone endpoints para CRUD y consultas específicas.
 */
@RestController
@RequestMapping("/UAO/apirest/reuniones")
public class ReunionController {
    
    @Autowired
    private IReunionesService reunionesService;
    
    /**
     * Crea una nueva reunión.
     * 
     * POST /UAO/apirest/reuniones/insertar
     * 
     * @param reunion DTO con los datos de la reunión
     * @return 201 Created con la reunión creada
     */
    @PostMapping("/insertar")
    public ResponseEntity<ReunionResponseDTO> crearReunion(@RequestBody ReunionCreateDTO reunion) {
        ReunionResponseDTO creada = reunionesService.crearReunion(reunion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }
    
    /**
     * Lista todas las reuniones.
     * 
     * GET /UAO/apirest/reuniones/listar
     * 
     * @return 200 OK con la lista de reuniones
     */
    @GetMapping("/listar")
    public ResponseEntity<List<ReunionResponseDTO>> listarReuniones() {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReuniones();
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Busca una reunión por su ID.
     * 
     * GET /UAO/apirest/reuniones/reunionporid/{id}
     * 
     * @param id ID de la reunión en formato String
     * @return 200 OK con la reunión encontrada
     */
    @GetMapping("/reunionporid/{id}")
    public ResponseEntity<ReunionResponseDTO> buscarReunionPorId(@PathVariable String id) {
        ReunionResponseDTO reunion = reunionesService.buscarReunionPorId(new ObjectId(id));
        return new ResponseEntity<>(reunion, HttpStatus.OK);
    }
    
    /**
     * Actualiza una reunión existente.
     * 
     * PUT /UAO/apirest/reuniones/actualizar/{id}
     * 
     * @param id ID de la reunión a actualizar
     * @param reunion DTO con los nuevos datos
     * @return 200 OK con la reunión actualizada
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ReunionResponseDTO> actualizarReunion(
            @PathVariable String id, 
            @RequestBody ReunionCreateDTO reunion) {
        ReunionResponseDTO actualizada = reunionesService.actualizarReunion(
            new ObjectId(id), 
            reunion
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
    /**
     * Elimina una reunión.
     * 
     * DELETE /UAO/apirest/reuniones/eliminar/{id}
     * 
     * @param id ID de la reunión a eliminar
     * @return 204 No Content
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReunion(@PathVariable String id) {
        reunionesService.eliminarReunion(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Lista reuniones próximas (futuras).
     * 
     * GET /UAO/apirest/reuniones/proximas
     * 
     * @return 200 OK con la lista de reuniones próximas
     */
    @GetMapping("/proximas")
    public ResponseEntity<List<ReunionResponseDTO>> listarReunionesProximas() {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesProximas();
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Lista reuniones pasadas.
     * 
     * GET /UAO/apirest/reuniones/pasadas
     * 
     * @return 200 OK con la lista de reuniones pasadas
     */
    @GetMapping("/pasadas")
    public ResponseEntity<List<ReunionResponseDTO>> listarReunionesPasadas() {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesPasadas();
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Lista reuniones en un rango de fechas.
     * 
     * GET /UAO/apirest/reuniones/por-rango?inicio=...&fin=...
     * 
     * Formato de fechas: yyyy-MM-dd'T'HH:mm:ss
     * Ejemplo: 2025-01-15T10:00:00
     * 
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return 200 OK con la lista de reuniones en el rango
     */
    @GetMapping("/por-rango")
    public ResponseEntity<List<ReunionResponseDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesPorRangoFechas(inicio, fin);
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Lista reuniones por tipo de modalidad.
     * 
     * GET /UAO/apirest/reuniones/por-modalidad?tipo=presencial
     * 
     * @param tipo Tipo de modalidad ("presencial" o "virtual")
     * @return 200 OK con la lista de reuniones del tipo especificado
     */
    @GetMapping("/por-modalidad")
    public ResponseEntity<List<ReunionResponseDTO>> listarPorModalidad(
            @RequestParam String tipo) {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesPorModalidad(tipo);
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Lista reuniones de un usuario específico.
     * 
     * GET /UAO/apirest/reuniones/por-usuario?usuarioId=...
     * 
     * @param usuarioId ID del usuario
     * @return 200 OK con la lista de reuniones del usuario
     */
    @GetMapping("/por-usuario")
    public ResponseEntity<List<ReunionResponseDTO>> listarPorUsuario(
            @RequestParam String usuarioId) {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesDeUsuario(
            new ObjectId(usuarioId)
        );
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
    
    /**
     * Lista reuniones donde se discute un libro específico.
     * 
     * GET /UAO/apirest/reuniones/por-libro?libroId=...
     * 
     * @param libroId ID del libro
     * @return 200 OK con la lista de reuniones del libro
     */
    @GetMapping("/por-libro")
    public ResponseEntity<List<ReunionResponseDTO>> listarPorLibro(
            @RequestParam String libroId) {
        List<ReunionResponseDTO> reuniones = reunionesService.listarReunionesPorLibro(
            new ObjectId(libroId)
        );
        return new ResponseEntity<>(reuniones, HttpStatus.OK);
    }
}