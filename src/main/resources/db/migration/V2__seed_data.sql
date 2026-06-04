-- ============================================================
-- DATOS INICIALES (SEED DATA)
-- ============================================================

-- Roles del sistema (IDs fijos para garantizar coherencia con el frontend)
INSERT INTO roles (id, nombre, descripcion) OVERRIDING SYSTEM VALUE VALUES
    (1, 'SuperUser',     'Nivel de acceso más alto con todos los permisos'),
    (2, 'Administrador', 'Gestiona la configuración del sistema y el acceso de usuarios'),
    (3, 'Psicologo',     'Profesional que brinda asistencia psicológica'),
    (4, 'Paciente',      'Usuario final que recibe servicios psicológicos');

-- Reajustar la secuencia para que los próximos IDs no colisionen
SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));

-- Usuario administrador inicial
-- Credenciales de demo: admin@safehaven.com / admin123  (hash BCrypt generado con BCryptPasswordEncoder)
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol)
VALUES ('Admin', 'SafeHaven', 'admin@safehaven.com', '$2a$10$990oHJNLzbiWtTl8dueEOO5mnYItOau81jW2j8bBdN/tT2zvfi6NG', 30, '3001234567', 'Masculino', '1995-01-01', 2);
-- Contraseña: admin123 (en texto plano — cambiar en producción)
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol)
VALUES ('Admin', 'SafeHaven', 'admin@safehaven.com', 'admin123', 30, 3001234567, 'M', '1995-01-01', 2);

-- Registro en tabla administradores usando el id del usuario recién insertado
INSERT INTO administradores (id, cargo)
VALUES ((SELECT id FROM usuarios WHERE correo_electronico = 'admin@safehaven.com'), 'Administrador General');

-- Usuario psicólogo de demo: juan.garcia@safehaven.com / psicologo123
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol)
VALUES ('Juan', 'García', 'juan.garcia@safehaven.com', '$2a$10$PQp0BkyeVr5EuNm929i2/ObjwaaA7ugYzlzWk01WgOvQrnFVnCcXO', 35, '3109876543', 'Masculino', '1990-05-15', 3);

INSERT INTO psicologos (id, especialidad, anos_de_experiencia, horario_de_atencion)
VALUES ((SELECT id FROM usuarios WHERE correo_electronico = 'juan.garcia@safehaven.com'), 'Psicología Clínica', 8, '08:00-18:00');

-- Consultorio de prueba
INSERT INTO consultorios (nombre, ubicacion, tipo, capacidad, horario_de_apertura, horario_de_cierre, activo)
VALUES ('Consultorio A', 'Piso 2, Oficina 201', 'Privado', 1, '08:00', '18:00', TRUE);

