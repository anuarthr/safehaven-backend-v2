-- ============================================================
-- CREACIÓN DE TABLAS
-- ============================================================

CREATE TABLE roles (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE usuarios (
    id                   BIGSERIAL PRIMARY KEY,
    nombre               VARCHAR(100),
    apellido             VARCHAR(100),
    correo_electronico   VARCHAR(150),
    password             VARCHAR(255),
    edad                 INTEGER,
    telefono             BIGINT,
    sexo                 VARCHAR(10),
    fecha_de_nacimiento  DATE,
    id_rol               BIGINT REFERENCES roles(id)
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
    id                  BIGINT PRIMARY KEY REFERENCES usuarios(id),
    aseguradora         VARCHAR(150),
    estado_de_salud     VARCHAR(100),
    fecha_de_registro   DATE
);

CREATE TABLE consultorios (
    id                  BIGSERIAL PRIMARY KEY,
    nombre              VARCHAR(100),
    ubicacion           VARCHAR(200),
    tipo                VARCHAR(100),
    capacidad           INTEGER,
    horario_de_apertura TIME,
    horario_de_cierre   TIME,
    activo              BOOLEAN DEFAULT TRUE
);

CREATE TABLE citas (
    id              BIGSERIAL PRIMARY KEY,
    motivo          VARCHAR(255),
    duracion        TIME,
    tipo_cita       VARCHAR(100),
    insert_by       VARCHAR(150),
    update_by       VARCHAR(150),
    fecha           DATE,
    hora            TIME,
    insert_at       DATE,
    update_at       DATE,
    paciente_id     BIGINT NOT NULL REFERENCES pacientes(id),
    psicologo_id    BIGINT NOT NULL REFERENCES psicologos(id),
    consultorio_id  BIGINT NOT NULL REFERENCES consultorios(id)
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
