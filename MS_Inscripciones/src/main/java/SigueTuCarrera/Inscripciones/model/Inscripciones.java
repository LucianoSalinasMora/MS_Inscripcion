package SigueTuCarrera.Inscripciones.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscripcionId;
    
    private String estudianteId;    
    private String asignaturaCodigo; 
    private LocalDate fechaInscripcion;
}
