MS_Inscripciones
Orquestador del proceso de toma de ramos. Maneja la lógica transaccional cuando un alumno intenta inscribir asignaturas en su periodo académico vigente.

Especificaciones Técnicas
Puerto: 8003

Base de Datos (Laragon): inscripciones_db

Tecnologías: Spring Boot 3.4.1, WebClient (Síncrono), Spring Data JPA, Springdoc OpenAPI (Swagger), JUnit 5, Mockito

Dependencias (WebClient)
Se comunica con Estudiantes (8001) para verificar que la matrícula esté VIGENTE y con Malla (8002) para validar que la asignatura exista y cumpla prerrequisitos. Las direcciones internas y de pruebas unitarias fueron modificadas para acoplarse a los microservicios correspondientes.

Estructura de Desarrollo y Pruebas
Estructura de Carpetas: Configuración global de Swagger bajo el paquete config. Pruebas unitarias organizadas en las carpetas controller y service dentro del directorio src/test/java/SigueTuCarrera/.

Carga Inicial: Archivo SQL configurado al lado de application.properties utilizando sentencias INSERT IGNORE INTO.

Endpoints Principales
POST /auth/inscripciones - Procesar e inscribir una asignatura a un alumno (Valida negocio).

GET /inscripciones/estudiante/{rut} - Ver el listado de ramos tomados por el alumno.

GET /swagger-ui.html - Documentación interactiva de la API.

Compilación y Despliegue Docker
Bash
mvn clean package
docker build -t ms-inscripcion:1.0 .
docker run -d -p 8003:8003 --name ms-inscripcion -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/i
