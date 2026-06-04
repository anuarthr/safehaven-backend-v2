-- ============================================================
-- DATOS DE DEMO — capturas de portafolio
-- Todos los passwords: psicologo123 / paciente123 (BCrypt)
-- ============================================================

-- ─────────────────────────────────────────────────────────────
-- CONSULTORIOS
-- ─────────────────────────────────────────────────────────────
INSERT INTO consultorios (nombre, ubicacion, tipo, capacidad, horario_de_apertura, horario_de_cierre, activo) VALUES
    ('Consultorio B', 'Piso 2, Oficina 202', 'Privado',  1, '08:00', '18:00', TRUE),
    ('Consultorio C', 'Piso 3, Oficina 301', 'Grupal',   8, '09:00', '17:00', TRUE),
    ('Consultorio D', 'Piso 1, Sala 105',    'Privado',  1, '10:00', '19:00', TRUE),
    ('Consultorio E', 'Piso 3, Oficina 305', 'Privado',  1, '07:00', '15:00', FALSE);

-- ─────────────────────────────────────────────────────────────
-- PSICÓLOGOS ADICIONALES
-- Contraseña de todos: psicologo123
-- ─────────────────────────────────────────────────────────────
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol) VALUES
    ('Ana',     'Gómez',      'ana.gomez@safehaven.com',      '$2a$10$OBVi1mJhakbPGkgSPIZkNe4Bqvx/aXLPHv.AP1T.8w28IGOzv4ZNa', 34, '3155551001', 'Femenino',   '1990-03-12', 3),
    ('Carlos',  'Hernández',  'carlos.hernandez@safehaven.com','$2a$10$OBVi1mJhakbPGkgSPIZkNe4Bqvx/aXLPHv.AP1T.8w28IGOzv4ZNa', 42, '3165551002', 'Masculino',  '1982-07-28', 3),
    ('Lucía',   'Martínez',   'lucia.martinez@safehaven.com',  '$2a$10$OBVi1mJhakbPGkgSPIZkNe4Bqvx/aXLPHv.AP1T.8w28IGOzv4ZNa', 29, '3175551003', 'Femenino',   '1995-11-05', 3),
    ('Diego',   'Fernández',  'diego.fernandez@safehaven.com', '$2a$10$OBVi1mJhakbPGkgSPIZkNe4Bqvx/aXLPHv.AP1T.8w28IGOzv4ZNa', 38, '3185551004', 'Masculino',  '1986-09-17', 3);

INSERT INTO psicologos (id, especialidad, anos_de_experiencia, horario_de_atencion) VALUES
    ((SELECT id FROM usuarios WHERE correo_electronico = 'ana.gomez@safehaven.com'),
     'Psicología Infantil',  10, '08:00-16:00'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'carlos.hernandez@safehaven.com'),
     'Psicología de Pareja', 15, '09:00-18:00'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'lucia.martinez@safehaven.com'),
     'Psicología Laboral',    5, '10:00-19:00'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'diego.fernandez@safehaven.com'),
     'Terapia Cognitivo-Conductual', 12, '07:00-15:00');

-- ─────────────────────────────────────────────────────────────
-- PACIENTES
-- Contraseña de todos: paciente123
-- ─────────────────────────────────────────────────────────────
INSERT INTO usuarios (nombre, apellido, correo_electronico, password, edad, telefono, sexo, fecha_de_nacimiento, id_rol) VALUES
    ('Pedro',     'Ramírez',   'pedro.ramirez@gmail.com',    '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 28, '3001110001', 'Masculino',  '1996-02-14', 4),
    ('Laura',     'Pérez',     'laura.perez@gmail.com',      '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 34, '3012220002', 'Femenino',   '1990-08-22', 4),
    ('Jorge',     'González',  'jorge.gonzalez@gmail.com',   '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 40, '3023330003', 'Masculino',  '1984-05-10', 4),
    ('Marta',     'Sánchez',   'marta.sanchez@gmail.com',    '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 25, '3034440004', 'Femenino',   '1999-11-30', 4),
    ('Andrés',    'López',     'andres.lopez@gmail.com',     '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 31, '3045550005', 'Masculino',  '1993-04-18', 4),
    ('Valentina', 'Torres',    'valentina.torres@gmail.com', '$2a$10$pLJKFXHAzS896gUElX83cehTO5Sk.Xtfwl4ZQdcdja.w69lfXt3Su', 22, '3056660006', 'Femenino',   '2002-07-03', 4);

INSERT INTO pacientes (id, aseguradora, estado_de_salud, fecha_de_registro) VALUES
    ((SELECT id FROM usuarios WHERE correo_electronico = 'pedro.ramirez@gmail.com'),    'Sura',       'Estable',  '2024-01-10'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'laura.perez@gmail.com'),      'Compensar',  'Bueno',    '2024-02-15'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'jorge.gonzalez@gmail.com'),   'Nueva EPS',  'Crónico',  '2024-03-05'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'marta.sanchez@gmail.com'),    'Sanitas',    'Bueno',    '2024-04-20'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'andres.lopez@gmail.com'),     'Colmédica',  'Estable',  '2024-05-08'),
    ((SELECT id FROM usuarios WHERE correo_electronico = 'valentina.torres@gmail.com'), 'Sura',       'Bueno',    '2024-06-01');

-- ─────────────────────────────────────────────────────────────
-- CITAS
-- Cubre los 4 estados para que el frontend muestre todas las vistas
-- ─────────────────────────────────────────────────────────────
INSERT INTO citas (motivo, duracion, tipo_cita, insert_by, fecha, hora, estado, id_paciente, id_psicologo, id_consultorio) VALUES

    -- PENDIENTE (próximas)
    ('Evaluación psicológica inicial',        '01:00', 'Presencial',
     'pedro.ramirez@gmail.com',       '2026-06-20', '09:00', 'PENDIENTE',
     (SELECT id FROM usuarios WHERE correo_electronico = 'pedro.ramirez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'juan.garcia@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio A')),

    ('Seguimiento ansiedad generalizada',     '00:45', 'Presencial',
     'laura.perez@gmail.com',         '2026-06-22', '10:30', 'PENDIENTE',
     (SELECT id FROM usuarios WHERE correo_electronico = 'laura.perez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'ana.gomez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio B')),

    ('Primera sesión terapia de pareja',      '01:30', 'Presencial',
     'jorge.gonzalez@gmail.com',      '2026-06-25', '14:00', 'PENDIENTE',
     (SELECT id FROM usuarios WHERE correo_electronico = 'jorge.gonzalez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'carlos.hernandez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio C')),

    ('Consulta orientación vocacional',       '00:45', 'Virtual',
     'valentina.torres@gmail.com',    '2026-06-28', '11:00', 'PENDIENTE',
     (SELECT id FROM usuarios WHERE correo_electronico = 'valentina.torres@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'lucia.martinez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio D')),

    -- CONFIRMADA (confirmadas para próximamente)
    ('Terapia cognitivo-conductual sesión 3', '01:00', 'Presencial',
     'marta.sanchez@gmail.com',       '2026-06-18', '09:00', 'CONFIRMADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'marta.sanchez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'diego.fernandez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio A')),

    ('Sesión manejo del estrés laboral',      '00:45', 'Virtual',
     'andres.lopez@gmail.com',        '2026-06-19', '15:00', 'CONFIRMADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'andres.lopez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'lucia.martinez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio B')),

    -- COMPLETADA (sesiones pasadas)
    ('Evaluación inicial depresión leve',     '01:00', 'Presencial',
     'pedro.ramirez@gmail.com',       '2026-05-10', '09:00', 'COMPLETADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'pedro.ramirez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'juan.garcia@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio A')),

    ('Seguimiento mensual',                   '00:30', 'Virtual',
     'laura.perez@gmail.com',         '2026-05-20', '10:00', 'COMPLETADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'laura.perez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'ana.gomez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio B')),

    ('Psicoterapia grupal ansiedad',          '01:30', 'Presencial',
     'jorge.gonzalez@gmail.com',      '2026-05-28', '14:00', 'COMPLETADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'jorge.gonzalez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'juan.garcia@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio C')),

    -- CANCELADA
    ('Sesión evaluación stress postraumático','01:00', 'Presencial',
     'marta.sanchez@gmail.com',       '2026-06-05', '09:00', 'CANCELADA',
     (SELECT id FROM usuarios WHERE correo_electronico = 'marta.sanchez@gmail.com'),
     (SELECT id FROM usuarios WHERE correo_electronico = 'diego.fernandez@safehaven.com'),
     (SELECT id FROM consultorios WHERE nombre = 'Consultorio A'));
