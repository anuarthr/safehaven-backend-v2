# 🛡️ SafeHaven - Backend

API REST para la gestión de una **clínica de psicología** — pacientes, psicólogos, administradores, citas, consultorios y facturación — con autenticación JWT y autorización por roles.

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.3-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI_3-85EA2D?style=flat&logo=swagger&logoColor=black)

Proyecto backend construido con Spring Boot siguiendo una arquitectura en N capas, pensado como base mantenible y escalable para un sistema de gestión clínica. El diseño prioriza separación de responsabilidades, seguridad sin estado y un contrato de API claro y documentado.

---

## ✨ Características destacadas

- 🔐 **Autenticación stateless con JWT** - login y endpoint `/me`; el token transporta el rol del usuario.
- 🛡️ **Autorización por roles** - alta de psicólogos/administradores y todos los `DELETE` requieren `ROLE_ADMINISTRADOR`.
- 👤 **Herencia de usuario con JOINED inheritance** - `Paciente`, `Psicologo` y `Administrador` extienden `Usuario`; sin duplicación de datos.
- 🧱 **Arquitectura en N capas** con inyección por constructor y servicios transaccionales.
- 📦 **DTOs como Java records + MapStruct** para un mapeo entidad–DTO seguro; las contraseñas nunca se exponen en respuestas.
- ✅ **Validación declarativa** (Jakarta Validation) con respuestas de error detalladas por campo.
- 🚦 **Manejo de errores centralizado** - JSON consistente y códigos HTTP correctos (400/401/403/409).
- 📖 **Documentación viva con OpenAPI/Swagger UI** (incluye autenticación Bearer).
- 🗄️ **Migraciones versionadas con Flyway** - esquema y datos semilla gestionados por código; Hibernate solo valida.
- 🧪 **Tests de integración** contra PostgreSQL real con `@SpringBootTest`; sin mocks de base de datos.
- 🐳 **Listo para correr con Docker Compose** para PostgreSQL.

---

## 🧰 Stack tecnológico

| Categoría      | Tecnologías                                          |
| --------------- | ----------------------------------------------------- |
| Lenguaje        | Java 17                                               |
| Framework       | Spring Boot 3.3 (Web, Data JPA, Security, Validation) |
| Seguridad       | Spring Security + JWT (JJWT 0.12), BCrypt             |
| Persistencia    | PostgreSQL, Hibernate/JPA, Flyway                     |
| Mapeo           | MapStruct + Lombok                                    |
| Documentación  | springdoc-openapi (Swagger UI)                        |
| Build & Tooling | Gradle (wrapper), Docker Compose                      |

---

## 🏗️ Arquitectura

```
HTTP Request
    │
    ▼
JwtAuthenticationFilter          ← valida el Bearer token por cada petición
    │
    ▼
Controller  (@RestController)    ← recibe DTOs, delega, construye ResponseEntity
    │
    ▼
Service     (XServiceI / XService) ← lógica de negocio, @Transactional
    │
    ▼
Repository  (JpaRepository)      ← Spring Data JPA
    │
    ▼
Entity / PostgreSQL
```

| Capa           | Paquete           | Responsabilidad                                                                               |
| -------------- | ----------------- | --------------------------------------------------------------------------------------------- |
| Controllers    | `controllers/`  | Endpoints REST, validación de entrada con `@Valid`                                         |
| Services       | `services/`     | Lógica de negocio, transacciones (`XServiceI` = interfaz, `XService` = impl)             |
| Repositories   | `repositories/` | Acceso a datos (Spring Data JPA, consultas derivadas)                                         |
| Entities       | `entities/`     | Modelo de persistencia JPA, herencia JOINED en `Usuario`                                    |
| DTOs & Mappers | `dtos/`         | Records de entrada/salida + interfaces MapStruct                                              |
| Security       | `security/`     | `SecurityConfig`, `JwtService`, `JwtAuthenticationFilter`, `CustomUserDetailsService` |
| Exceptions     | `exceptions/`   | Excepciones de dominio +`GlobalException` (`@ControllerAdvice`)                           |
| Config         | `config/`       | Configuración OpenAPI/Swagger                                                                |

### Modelo de usuario

`Usuario` (tabla `usuarios`) es la entidad base con herencia **JOINED**. `Paciente`, `Psicologo` y `Administrador` extienden `Usuario` y mapean a su propia tabla que comparte la PK con `usuarios`. Cada subtipo tiene su propio controlador, servicio, repositorio y DTOs. La unicidad de email se verifica contra la tabla `usuarios` para cubrir todos los subtipos.

---

## 🚀 Puesta en marcha

**Requisitos:** Java 17 y Docker.

### Con el script incluido (recomendado)

```powershell
# Arrancar la app (levanta Docker automáticamente)
.\safehaven.ps1

# Otros modos
.\safehaven.ps1 test    # correr los tests
.\safehaven.ps1 reset   # borrar BD y arrancar limpio (aplica todas las migraciones)
.\safehaven.ps1 stop    # detener los contenedores
```

### Manualmente

```bash
# 1. Levantar PostgreSQL (puerto host 5433)
docker compose up -d

# 2. Arrancar la aplicación
./gradlew bootRun          # Windows: .\gradlew.bat bootRun
```

La API queda en `http://localhost:8080`.
Documentación interactiva: `http://localhost:8080/swagger-ui.html` — pulsa **Authorize** y pega el JWT obtenido en `/api/auth/login`.

---

## 🔐 Autenticación

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@safehaven.com","password":"admin123"}'
# Respuesta: { "token": "eyJ...", "email": "...", "nombre": "...", "rol": "..." }

# Endpoint protegido
curl http://localhost:8080/api/pacientes \
  -H "Authorization: Bearer eyJ..."
```

### Credenciales de demo

Creadas por las migraciones Flyway (V2 + V4):

| Email                              | Contraseña      | Rol           |
| ---------------------------------- | ---------------- | ------------- |
| `admin@safehaven.com`            | `admin123`     | Administrador |
| `juan.garcia@safehaven.com`      | `psicologo123` | Psicologo     |
| `ana.gomez@safehaven.com`        | `psicologo123` | Psicologo     |
| `carlos.hernandez@safehaven.com` | `psicologo123` | Psicologo     |
| `pedro.ramirez@gmail.com`        | `paciente123`  | Paciente      |
| `laura.perez@gmail.com`          | `paciente123`  | Paciente      |

### Reglas de acceso

| Nivel                 | Aplica a                                                                                           |
| --------------------- | -------------------------------------------------------------------------------------------------- |
| 🌐 Público           | `POST /api/auth/login`, `POST /api/pacientes` (auto-registro), Swagger UI                      |
| 🔒 Solo Administrador | `POST /api/administradores`, `POST /api/psicologos`, `POST /api/roles`, todos los `DELETE` |
| 🔑 Autenticado        | Todo lo demás                                                                                     |

---

## 🗂️ Endpoints principales

| Recurso         | Base path                | Notas                                                      |
| --------------- | ------------------------ | ---------------------------------------------------------- |
| Autenticación  | `/api/auth`            | `/login`, `/me`                                        |
| Pacientes       | `/api/pacientes`       | Auto-registro público                                     |
| Psicólogos     | `/api/psicologos`      | Alta solo para Administrador                               |
| Administradores | `/api/administradores` | Alta solo para Administrador                               |
| Citas           | `/api/citas`           | Estado:`PENDIENTE \| CONFIRMADA \| CANCELADA \| COMPLETADA` |
| Consultorios    | `/api/consultorios`    |                                                            |
| Roles           | `/api/roles`           |                                                            |

Cada recurso expone CRUD estándar (`GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}`).
Errores uniformes: `{ "status", "message", "errorCode" }`.

---

## ⚙️ Configuración

Todos los valores tienen defaults para desarrollo y se sobreescriben con variables de entorno:

| Variable                          | Default (dev)                                  | Descripción                                                  |
| --------------------------------- | ---------------------------------------------- | ------------------------------------------------------------- |
| `DB_URL`                        | `jdbc:postgresql://localhost:5433/safeHaven` | URL JDBC                                                      |
| `DB_USERNAME` / `DB_PASSWORD` | `user` / `123456`                          | Credenciales BD                                               |
| `CORS_ALLOWED_ORIGINS`          | `http://localhost:5173`                      | Orígenes permitidos (coma-separados)                         |
| `JWT_SECRET`                    | *(clave de dev)*                             | Secreto Base64 ≥ 256 bits —**cambiar en producción** |
| `JWT_EXPIRATION_MS`             | `86400000`                                   | Expiración del token (ms) — 24 h                            |

---

## 🧪 Tests

Los tests de integración levantan el contexto completo de Spring contra el PostgreSQL de `docker compose`. No usan mocks de base de datos.

```bash
docker compose up -d   # la base debe estar arriba primero
./gradlew test         # Windows: .\gradlew.bat test
```

Reporte HTML generado en `build/reports/tests/test/index.html`.

---

## 🗺️ Posibles mejoras

- Paginación y filtrado en los listados.
- Pipeline de CI (build + tests) con GitHub Actions.
- Refresh tokens.
- Historial clínico y facturación completos (entidades ya modeladas en el esquema).
