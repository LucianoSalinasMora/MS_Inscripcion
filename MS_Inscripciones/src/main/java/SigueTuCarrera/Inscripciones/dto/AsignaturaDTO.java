package SigueTuCarrera.Inscripciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto simplificado para la transferencia de información básica de una asignatura")
public class AsignaturaDTO {

    @Schema(description = "Código alfanumérico único de la asignatura", example = "INF-324")
    private String codigoAsignatura;

    @Schema(description = "Nombre oficial de la asignatura", example = "Estructuras de Datos y Algoritmos")
    private String nombre;
}