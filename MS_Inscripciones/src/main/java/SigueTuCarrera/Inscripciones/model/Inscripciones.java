package SigueTuCarrera.Inscripciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Representa el registro formal de la inscripción de una asignatura por parte de un estudiante")
public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autogenerado del registro de inscripción", example = "1")
    private Long inscripcionId;
    
    @Schema(
        description = "Identificador relacional o RUT del estudiante que inscribe", 
        example = "20123456-K",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String estudianteId;    

    @Schema(
        description = "Código alfanumérico de la asignatura que se está inscribiendo", 
        example = "INF-324",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String asignaturaCodigo; 

    @Schema(
        description = "Fecha exacta en la que se procesó la inscripción en el sistema", 
        example = "2026-06-22"
    )
    private LocalDate fechaInscripcion;
}