package com.apirest.backendClub.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.apirest.backendClub.Model.ReunionesModel;

/**SD
 * Repositorio para la gestión de reuniones en MongoDB.
 * Extiende MongoRepository para operaciones CRUD básicas.
 */
public interface IReunionesRepository extends MongoRepository<ReunionesModel, ObjectId> {
    
    /**
     * Buscar reuniones posteriores a una fecha específica
     * Útil para obtener reuniones próximas o futuras
     * 
     * @param fecha Fecha de referencia
     * @return Lista de reuniones después de la fecha
     */
    List<ReunionesModel> findByDateTimeAfter(LocalDateTime fecha);
    
    /**
     * Buscar reuniones anteriores a una fecha específica
     * Útil para obtener reuniones pasadas
     * 
     * @param fecha Fecha de referencia
     * @return Lista de reuniones antes de la fecha
     */
    List<ReunionesModel> findByDateTimeBefore(LocalDateTime fecha);
    
    /**
     * Buscar reuniones en un rango de fechas
     * 
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return Lista de reuniones en el rango especificado
     */
    List<ReunionesModel> findByDateTimeBetween(LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Buscar reuniones por tipo de modalidad (presencial o virtual)
     * 
     * @param tipo Tipo de modalidad ("presencial" o "virtual")
     * @return Lista de reuniones del tipo especificado
     */
    @Query("{ 'modalidad.tipo': ?0 }")
    List<ReunionesModel> findByModalidadTipo(String tipo);
    
    /**
     * Buscar reuniones donde un usuario específico esté invitado
     * 
     * @param usuarioId ID del usuario
     * @return Lista de reuniones donde el usuario es invitado
     */
    @Query("{ 'listaInvitados.usuarioId': ?0 }")
    List<ReunionesModel> findByInvitado(ObjectId usuarioId);
    
    /**
     * Buscar reuniones donde se discuta un libro específico
     * 
     * @param libroId ID del libro
     * @return Lista de reuniones donde se discute el libro
     */
    @Query("{ 'libroDiscutir.libroId': ?0 }")
    List<ReunionesModel> findByLibroDiscutir(ObjectId libroId);
}