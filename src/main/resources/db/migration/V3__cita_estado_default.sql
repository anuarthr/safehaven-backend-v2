-- ============================================================
-- Estandariza el estado de las citas con el vocabulario del frontend:
-- PENDIENTE | CONFIRMADA | CANCELADA | COMPLETADA
-- ============================================================

UPDATE citas SET estado = 'PENDIENTE' WHERE estado IS NULL OR estado = 'Programada';

ALTER TABLE citas ALTER COLUMN estado SET DEFAULT 'PENDIENTE';
