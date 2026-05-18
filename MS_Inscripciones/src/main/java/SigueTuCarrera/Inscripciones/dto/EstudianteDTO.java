package SigueTuCarrera.Inscripciones.dto;

import lombok.Data;

@Data
public class EstudianteDTO {
    private String rut;
    private String estadoMatricula; // Para mapear el Enum de Estudiantes como String
}
