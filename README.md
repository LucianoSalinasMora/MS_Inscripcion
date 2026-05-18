# MS_Inscripcion
Orquestador del proceso de toma de ramos. Maneja la lógica transaccional cuando un alumno intenta inscribir asignaturas en su periodo académico vigente.
## Especificaciones Técnicas
* **Puerto:** `8003`
* **Base de Datos (Laragon):** `inscripciones_db`
* **Tecnologías:** Spring Boot 4.0.6, WebClient (Síncrono)

## Dependencias (WebClient)
* Habla con Estudiantes (8001) para verificar que la matrícula esté VIGENTE.
* Habla con Malla (8002) para validar que la asignatura exista y cumpla prerrequisitos.

## Endpoints Principales
* `POST /api/v1/inscripciones` - Procesar e inscribir una asignatura a un alumno (Valida negocio).
* `GET /api/v1/inscripciones/estudiante/{rut}` - Ver el listado de ramos tomados por el alumno.
