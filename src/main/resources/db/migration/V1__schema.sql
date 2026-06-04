-- ============================================================
-- CREACIÓN DE TABLAS
-- ============================================================

CREATE TABLE roles (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

CREATE TABLE usuarios (
    id                   BIGSERIAL PRIMARY KEY,
    nombre               VARCHAR(100) NOT NULL,
    apellido             VARCHAR(100) NOT NULL,
    correo_electronico   VARCHAR(150) NOT NULL UNIQUE,
    password             VARCHAR(255) NOT NULL,
    edad                 INTEGER,
    telefono             VARCHAR(15),
    sexo                 VARCHAR(20),
    fecha_de_nacimiento  DATE,
    id_rol               BIGINT NOT NULL REFERENCES roles(id)
);

CREATE TABLE administradores (
    id    BIGINT PRIMARY KEY REFERENCES usuarios(id),
    cargo VARCHAR(100)
);

CREATE TABLE psicologos (
    id                    BIGINT PRIMARY KEY REFERENCES usuarios(id),
    especialidad          VARCHAR(150),
    anos_de_experiencia   INTEGER,
    horario_de_atencion   VARCHAR(100)
);

CREATE TABLE pacientes (
    id                BIGINT PRIMARY KEY REFERENCES usuarios(id),
    aseguradora       VARCHAR(100),
    estado_de_salud   VARCHAR(100),
    fecha_de_registro DATE
);

CREATE TABLE consultorios (
    id                  BIGSERIAL PRIMARY KEY,
    nombre              VARCHAR(100) NOT NULL,
    ubicacion           VARCHAR(200),
    tipo                VARCHAR(100),
    capacidad           INTEGER,
    horario_de_apertura VARCHAR(10),
    horario_de_cierre   VARCHAR(10),
    activo              BOOLEAN DEFAULT TRUE
);

CREATE TABLE citas (
    id              BIGSERIAL PRIMARY KEY,
    motivo          VARCHAR(255) NOT NULL,
    duracion        TIME,
    tipo_cita       VARCHAR(100),
    insert_by       VARCHAR(150),
    update_by       VARCHAR(150),
    fecha           DATE NOT NULL,
    hora            TIME NOT NULL,
    id_paciente     BIGINT NOT NULL REFERENCES pacientes(id),
    id_psicologo    BIGINT NOT NULL REFERENCES psicologos(id),
    id_consultorio  BIGINT NOT NULL REFERENCES consultorios(id),
    estado          VARCHAR(50) DEFAULT 'Programada'
);

CREATE TABLE servicios (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100),
    descripcion VARCHAR(255),
    precio      DOUBLE PRECISION
);

CREATE TABLE servicios_citas (
    id_servicio_cita BIGSERIAL PRIMARY KEY,
    id_servicio      BIGINT REFERENCES servicios(id),
    id_cita          BIGINT REFERENCES citas(id)
);

CREATE TABLE facturas (
    id              BIGSERIAL PRIMARY KEY,
    monto           DOUBLE PRECISION,
    insert_by       VARCHAR(150),
    update_by       VARCHAR(150),
    fecha_de_pago   DATE,
    insert_at       DATE,
    update_at       DATE,
    id_cita         BIGINT REFERENCES citas(id),
    id_paciente     BIGINT REFERENCES pacientes(id)
);

CREATE TABLE tipo_estado_citas (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100),
    descripcion VARCHAR(255)
);

CREATE TABLE estados_cita (
    id_estado_cita                    BIGSERIAL PRIMARY KEY,
    fecha_inicio_estado               DATE,
    fecha_fin_estado                  DATE,
    fecha_inicio_de_registro_estado   DATE,
    fecha_fin_de_registro_estado      DATE,
    id_cita                           BIGINT REFERENCES citas(id),
    id_tipo_estado_cita               BIGINT REFERENCES tipo_estado_citas(id)
);

CREATE TABLE tipo_estado_facturas (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100),
    descripcion VARCHAR(255)
);

CREATE TABLE estados_factura (
    factura_id                        BIGINT REFERENCES facturas(id),
    tipodeestadofactura_id            BIGINT REFERENCES tipo_estado_facturas(id),
    fecha_inicio_estado               DATE,
    fecha_fin_estado                  DATE,
    fecha_inicio_de_registro_estado   DATE,
    fecha_fin_de_registro_estado      DATE,
    PRIMARY KEY (factura_id, tipodeestadofactura_id)
);

CREATE TABLE diagnosticos (
    id           BIGSERIAL PRIMARY KEY,
    descripcion  VARCHAR(500),
    fecha        DATE,
    id_paciente  BIGINT REFERENCES pacientes(id),
    id_psicologo BIGINT REFERENCES psicologos(id)
);

CREATE TABLE historiales_clinicos (
    id                  BIGSERIAL PRIMARY KEY,
    fecha_de_creacion   DATE,
    comentarios         TEXT,
    id_paciente         BIGINT REFERENCES pacientes(id)
);

CREATE TABLE tratamientos (
    id                   BIGSERIAL PRIMARY KEY,
    nombre               VARCHAR(150),
    descripcion          VARCHAR(500),
    duracion             INTEGER,
    id_psicologo         BIGINT REFERENCES psicologos(id),
    id_diagnostico       BIGINT REFERENCES diagnosticos(id),
    id_historial_clinico BIGINT REFERENCES historiales_clinicos(id)
);
