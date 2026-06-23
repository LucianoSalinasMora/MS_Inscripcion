package SigueTuCarrera.Inscripciones.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferencia con el estado y datos de identificación del estudiante")
public class EstudianteDTO {

    @Schema(description = "RUT único del estudiante, utilizado como identificador principal", example = "20123456-K")
    private String rut;

    @Schema(description = "Estado actual de la matrícula del alumno en el periodo vigente", example = "MATRICULADO")
    private String estadoMatricula;
}