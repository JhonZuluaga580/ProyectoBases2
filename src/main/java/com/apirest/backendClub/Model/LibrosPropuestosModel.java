package com.apirest.backendClub.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.apirest.backendClub.Model.LibrosPropuestosembb.LibroPropuesto;
import com.apirest.backendClub.Model.LibrosPropuestosembb.PropuestoPor;
import com.apirest.backendClub.Model.LibrosPropuestosembb.Votacion;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("librosPropuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibrosPropuestosModel {
    @Id
    private ObjectId id;
    private LibroPropuesto libroPropuesto;
    private PropuestoPor propuestoPor;
    private LocalDateTime fecha;
    private String estado;
    private List<Votacion> votacion = new ArrayList<>();

    
   @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
        public enum Estado {
        PENDIENTE("pendiente"),
        EN_VOTACION("en_votacion"),
        APROBADA("aprobada"),
        RECHAZADA("rechazada");
        
        private final String valor;
        
        Estado(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }
        public enum TipoVoto {
        A_FAVOR("a favor"),
        EN_CONTRA("en contra"),
        NEUTRO("neutro");
        
        private final String valor;
        
        TipoVoto(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }

    //Calcula el total de votos a favor
    public long contarVotosAFavor() {
        return votacion.stream()
            .filter(v -> "a favor".equalsIgnoreCase(v.getVoto()))
            .count();
    }
    
    //Calcula el total de votos en contra
    public long contarVotosEnContra() {
        return votacion.stream()
            .filter(v -> "en contra".equalsIgnoreCase(v.getVoto()))
            .count();
    }
    
    //Calcula el total de votos neutros
    public long contarVotosNeutros() {
        return votacion.stream()
            .filter(v -> "neutro".equalsIgnoreCase(v.getVoto()))
            .count();
    }
}