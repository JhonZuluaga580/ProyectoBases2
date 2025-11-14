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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.backendClub.DTO.LibroPropuestoCreateDTO;
import com.apirest.backendClub.DTO.LibroPropuestoResponseDTO;
import com.apirest.backendClub.DTO.VotarDTO;
import com.apirest.backendClub.Service.ILibrosPropuestosService;

/**
 * Controlador REST para gestionar libros propuestos.
 * Expone endpoints para propuestas, votaciones y consultas.
 */
@RestController
@RequestMapping("/UAO/apirest/propuestas")
public class LibroPropuestoController {
    
    @Autowired
    private ILibrosPropuestosService propuestasService;
    
    /**
     * Crea una nueva propuesta de libro.
     * 
     * POST /UAO/apirest/propuestas/insertar
     * 
     * @param propuesta DTO con los IDs del libro y usuario
     * @return 201 Created con la propuesta creada
     */
    @PostMapping("/insertar")
    public ResponseEntity<LibroPropuestoResponseDTO> crearPropuesta(
            @RequestBody LibroPropuestoCreateDTO propuesta) {
        LibroPropuestoResponseDTO creada = propuestasService.crearPropuesta(propuesta);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }
    
    /**
     * Lista todas las propuestas.
     * 
     * GET /UAO/apirest/propuestas/listar
     * 
     * @return 200 OK con la lista de propuestas
     */
    @GetMapping("/listar")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestas() {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestas();
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Busca una propuesta por su ID.
     * 
     * GET /UAO/apirest/propuestas/propuestaporid/{id}
     * 
     * @param id ID de la propuesta
     * @return 200 OK con la propuesta encontrada
     */
    @GetMapping("/propuestaporid/{id}")
    public ResponseEntity<LibroPropuestoResponseDTO> buscarPropuestaPorId(
            @PathVariable String id) {
        LibroPropuestoResponseDTO propuesta = propuestasService.buscarPropuestaPorId(
            new ObjectId(id)
        );
        return new ResponseEntity<>(propuesta, HttpStatus.OK);
    }
    
    /**
     * Actualiza el estado de una propuesta.
     * 
     * PATCH /UAO/apirest/propuestas/actualizar-estado/{id}?estado=aprobada
     * 
     * Estados válidos: pendiente, en_votacion, aprobada, rechazada
     * 
     * @param id ID de la propuesta
     * @param estado Nuevo estado
     * @return 200 OK con la propuesta actualizada
     */
    @PatchMapping("/actualizar-estado/{id}")
    public ResponseEntity<LibroPropuestoResponseDTO> actualizarEstado(
            @PathVariable String id,
            @RequestParam String estado) {
        LibroPropuestoResponseDTO actualizada = propuestasService.actualizarEstado(
            new ObjectId(id), 
            estado
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
    /**
     * Elimina una propuesta.
     * 
     * DELETE /UAO/apirest/propuestas/eliminar/{id}
     * 
     * @param id ID de la propuesta
     * @return 204 No Content
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPropuesta(@PathVariable String id) {
        propuestasService.eliminarPropuesta(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Registra un voto en una propuesta.
     * 
     * POST /UAO/apirest/propuestas/votar/{id}
     * 
     * Body: { "usuarioId": "...", "voto": "a favor" }
     * Votos válidos: "a favor", "en contra", "neutro"
     * 
     * @param id ID de la propuesta
     * @param voto DTO con usuario y tipo de voto
     * @return 200 OK con la propuesta actualizada
     */
    @PostMapping("/votar/{id}")
    public ResponseEntity<LibroPropuestoResponseDTO> votarPropuesta(
            @PathVariable String id,
            @RequestBody VotarDTO voto) {
        LibroPropuestoResponseDTO actualizada = propuestasService.votarPropuesta(
            new ObjectId(id), 
            voto
        );
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas por estado.
     * 
     * GET /UAO/apirest/propuestas/por-estado?estado=pendiente
     * 
     * Estados válidos: pendiente, en_votacion, aprobada, rechazada
     * 
     * @param estado Estado a filtrar
     * @return 200 OK con la lista de propuestas
     */
    @GetMapping("/por-estado")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPorEstado(
            @RequestParam String estado) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPorEstado(estado);
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas de un usuario.
     * 
     * GET /UAO/apirest/propuestas/por-usuario?usuarioId=...
     * 
     * @param usuarioId ID del usuario
     * @return 200 OK con la lista de propuestas del usuario
     */
    @GetMapping("/por-usuario")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestasDeUsuario(
            @RequestParam String usuarioId) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestasDeUsuario(
            new ObjectId(usuarioId)
        );
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas de un libro.
     * 
     * GET /UAO/apirest/propuestas/por-libro?libroId=...
     * 
     * @param libroId ID del libro
     * @return 200 OK con la lista de propuestas del libro
     */
    @GetMapping("/por-libro")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestasDeLibro(
            @RequestParam String libroId) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestasDeLibro(
            new ObjectId(libroId)
        );
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas en un rango de fechas.
     * 
     * GET /UAO/apirest/propuestas/por-rango?inicio=...&fin=...
     * 
     * Formato: yyyy-MM-dd'T'HH:mm:ss
     * 
     * @param inicio Fecha de inicio
     * @param fin Fecha de fin
     * @return 200 OK con la lista de propuestas en el rango
     */
    @GetMapping("/por-rango")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPorRangoFechas(
            inicio, 
            fin
        );
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas ordenadas por fecha (más recientes primero).
     * 
     * GET /UAO/apirest/propuestas/recientes
     * 
     * @return 200 OK con la lista de propuestas ordenadas
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestasRecientes() {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestasRecientes();
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas donde un usuario ha votado.
     * 
     * GET /UAO/apirest/propuestas/votadas-por?usuarioId=...
     * 
     * @param usuarioId ID del usuario
     * @return 200 OK con la lista de propuestas votadas
     */
    @GetMapping("/votadas-por")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPropuestasVotadasPorUsuario(
            @RequestParam String usuarioId) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPropuestasVotadasPorUsuario(
            new ObjectId(usuarioId)
        );
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
    
    /**
     * Lista propuestas por género del libro.
     * 
     * GET /UAO/apirest/propuestas/por-genero?genero=Ficción
     * 
     * @param genero Género literario
     * @return 200 OK con la lista de propuestas del género
     */
    @GetMapping("/por-genero")
    public ResponseEntity<List<LibroPropuestoResponseDTO>> listarPorGenero(
            @RequestParam String genero) {
        List<LibroPropuestoResponseDTO> propuestas = propuestasService.listarPorGenero(genero);
        return new ResponseEntity<>(propuestas, HttpStatus.OK);
    }
}