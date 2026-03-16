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
-- Contraseña: admin123 (en texto plano — cambiar en producción)
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol)
VALUES ('Admin', 'SafeHaven', 'admin@safehaven.com', 'admin123', 30, 3001234567, 'M', '1995-01-01', 2);

-- Registro en tabla administradores usando el id del usuario recién insertado
INSERT INTO administradores (id, cargo)
VALUES ((SELECT id FROM usuarios WHERE correo_electronico = 'admin@safehaven.com'), 'Administrador General');
