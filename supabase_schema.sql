-- =====================================================
-- SCRIPT SQL PARA SUPABASE (PostgreSQL)
-- Sistema de Gestión con Votación
-- =====================================================

-- Eliminar tablas si existen (para desarrollo)
DROP TABLE IF EXISTS votos CASCADE;
DROP TABLE IF EXISTS mensajes_chat CASCADE;
DROP TABLE IF EXISTS reclamaciones CASCADE;
DROP TABLE IF EXISTS candidatos CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- =====================================================
-- TABLA: USUARIOS
-- =====================================================
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(8) NOT NULL UNIQUE,
    dni_digito_verificador VARCHAR(1) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    distrito VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    pin VARCHAR(60) NOT NULL, -- BCrypt hash
    foto_perfil VARCHAR(255),
    role VARCHAR(50) NOT NULL DEFAULT 'USUARIO',
    codigo_verificacion VARCHAR(6),
    codigo_expiracion TIMESTAMP,
    verificado BOOLEAN NOT NULL DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_role CHECK (role IN ('USUARIO', 'ADMINISTRADOR', 'SUPERADMINISTRADOR'))
);

-- Índices para usuarios
CREATE INDEX idx_usuarios_dni ON usuarios(dni);
CREATE INDEX idx_usuarios_correo ON usuarios(correo);
CREATE INDEX idx_usuarios_role ON usuarios(role);

-- =====================================================
-- TABLA: CANDIDATOS
-- =====================================================
CREATE TABLE candidatos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    partido VARCHAR(200) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    propuestas TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    votos INTEGER NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_tipo CHECK (tipo IN ('PRESIDENTE', 'ALCALDE'))
);

-- Índices para candidatos
CREATE INDEX idx_candidatos_tipo ON candidatos(tipo);
CREATE INDEX idx_candidatos_activo ON candidatos(activo);

-- =====================================================
-- TABLA: VOTOS
-- =====================================================
CREATE TABLE votos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    candidato_presidente_id BIGINT,
    candidato_alcalde_id BIGINT,
    fecha_voto TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completado BOOLEAN NOT NULL DEFAULT FALSE,
    
    CONSTRAINT fk_voto_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_voto_presidente FOREIGN KEY (candidato_presidente_id) REFERENCES candidatos(id) ON DELETE SET NULL,
    CONSTRAINT fk_voto_alcalde FOREIGN KEY (candidato_alcalde_id) REFERENCES candidatos(id) ON DELETE SET NULL,
    CONSTRAINT uq_usuario_voto UNIQUE (usuario_id)
);

-- Índices para votos
CREATE INDEX idx_votos_usuario ON votos(usuario_id);
CREATE INDEX idx_votos_completado ON votos(completado);

-- =====================================================
-- TABLA: RECLAMACIONES
-- =====================================================
CREATE TABLE reclamaciones (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    evidencia VARCHAR(255),
    respuesta TEXT,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta TIMESTAMP,
    
    CONSTRAINT fk_reclamacion_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT chk_tipo_reclamacion CHECK (tipo IN ('QUEJA', 'RECLAMO', 'SUGERENCIA')),
    CONSTRAINT chk_estado CHECK (estado IN ('PENDIENTE', 'EN_PROCESO', 'RESUELTO', 'CERRADO'))
);

-- Índices para reclamaciones
CREATE INDEX idx_reclamaciones_usuario ON reclamaciones(usuario_id);
CREATE INDEX idx_reclamaciones_tipo ON reclamaciones(tipo);
CREATE INDEX idx_reclamaciones_estado ON reclamaciones(estado);
CREATE INDEX idx_reclamaciones_fecha ON reclamaciones(fecha_creacion DESC);

-- =====================================================
-- TABLA: MENSAJES_CHAT
-- =====================================================
CREATE TABLE mensajes_chat (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    mensaje TEXT NOT NULL,
    respuesta TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_mensaje_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índices para mensajes_chat
CREATE INDEX idx_mensajes_usuario ON mensajes_chat(usuario_id);
CREATE INDEX idx_mensajes_fecha ON mensajes_chat(fecha_creacion DESC);

-- =====================================================
-- DATOS INICIALES
-- =====================================================

-- Insertar SuperAdministrador (PIN: 999999)
-- Hash BCrypt de "999999": $2a$10$7/gMogUEJvSz2lVfkOAEAeVChIfSchruionAl9dlaemSUFANvoHvW
INSERT INTO usuarios (nombres, apellidos, dni, dni_digito_verificador, correo, distrito, departamento, pin, role, verificado)
VALUES ('Super', 'Administrador', '99999999', '9', 'admin@sistema.com', 'Lima', 'Lima', 
        '$2a$10$7/gMogUEJvSz2lVfkOAEAeVChIfSchruionAl9dlaemSUFANvoHvW', 
        'SUPERADMINISTRADOR', TRUE);

-- Insertar Candidatos a Presidente
INSERT INTO candidatos (nombre, partido, tipo, propuestas) VALUES
('María Elena Torres', 'Partido Progresista', 'PRESIDENTE', 'Educación gratuita y salud universal'),
('Carlos Mendoza', 'Alianza Nacional', 'PRESIDENTE', 'Desarrollo económico y empleo'),
('Ana Lucía Vargas', 'Frente Democrático', 'PRESIDENTE', 'Seguridad ciudadana y justicia');

-- Insertar Candidatos a Alcalde
INSERT INTO candidatos (nombre, partido, tipo, propuestas) VALUES
('Jorge Ramírez', 'Movimiento Municipal', 'ALCALDE', 'Mejora de parques y áreas verdes'),
('Patricia Flores', 'Lima para Todos', 'ALCALDE', 'Transporte público eficiente'),
('Roberto Silva', 'Independiente', 'ALCALDE', 'Seguridad y limpieza pública');

-- =====================================================
-- FUNCIONES ÚTILES
-- =====================================================

-- Función para obtener estadísticas de votación
CREATE OR REPLACE FUNCTION obtener_estadisticas_votacion()
RETURNS TABLE (
    total_usuarios BIGINT,
    total_votos BIGINT,
    porcentaje_participacion NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        COUNT(DISTINCT u.id) as total_usuarios,
        COUNT(DISTINCT v.id) as total_votos,
        ROUND((COUNT(DISTINCT v.id)::NUMERIC / NULLIF(COUNT(DISTINCT u.id), 0)) * 100, 2) as porcentaje_participacion
    FROM usuarios u
    LEFT JOIN votos v ON u.id = v.usuario_id AND v.completado = TRUE
    WHERE u.verificado = TRUE;
END;
$$ LANGUAGE plpgsql;

-- Función para obtener resultados por candidato
CREATE OR REPLACE FUNCTION obtener_resultados_candidato(tipo_candidato VARCHAR)
RETURNS TABLE (
    candidato_id BIGINT,
    nombre VARCHAR,
    partido VARCHAR,
    total_votos BIGINT,
    porcentaje NUMERIC
) AS $$
BEGIN
    IF tipo_candidato = 'PRESIDENTE' THEN
        RETURN QUERY
        SELECT 
            c.id,
            c.nombre,
            c.partido,
            COUNT(v.id) as total_votos,
            ROUND((COUNT(v.id)::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
        FROM candidatos c
        LEFT JOIN votos v ON c.id = v.candidato_presidente_id AND v.completado = TRUE
        WHERE c.tipo = 'PRESIDENTE' AND c.activo = TRUE
        GROUP BY c.id, c.nombre, c.partido
        ORDER BY total_votos DESC;
    ELSE
        RETURN QUERY
        SELECT 
            c.id,
            c.nombre,
            c.partido,
            COUNT(v.id) as total_votos,
            ROUND((COUNT(v.id)::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
        FROM candidatos c
        LEFT JOIN votos v ON c.id = v.candidato_alcalde_id AND v.completado = TRUE
        WHERE c.tipo = 'ALCALDE' AND c.activo = TRUE
        GROUP BY c.id, c.nombre, c.partido
        ORDER BY total_votos DESC;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- =====================================================
-- POLÍTICAS DE SEGURIDAD (Row Level Security)
-- =====================================================

-- Habilitar RLS en todas las tablas
ALTER TABLE usuarios ENABLE ROW LEVEL SECURITY;
ALTER TABLE candidatos ENABLE ROW LEVEL SECURITY;
ALTER TABLE votos ENABLE ROW LEVEL SECURITY;
ALTER TABLE reclamaciones ENABLE ROW LEVEL SECURITY;
ALTER TABLE mensajes_chat ENABLE ROW LEVEL SECURITY;

-- Política: Los usuarios pueden ver su propia información
CREATE POLICY usuarios_select_own ON usuarios
    FOR SELECT
    USING (auth.uid()::text = id::text OR role IN ('ADMINISTRADOR', 'SUPERADMINISTRADOR'));

-- Política: Los usuarios pueden actualizar su propia información
CREATE POLICY usuarios_update_own ON usuarios
    FOR UPDATE
    USING (auth.uid()::text = id::text);

-- Política: Todos pueden ver candidatos activos
CREATE POLICY candidatos_select_all ON candidatos
    FOR SELECT
    USING (activo = TRUE);

-- Política: Los usuarios pueden ver sus propios votos
CREATE POLICY votos_select_own ON votos
    FOR SELECT
    USING (auth.uid()::text = usuario_id::text);

-- Política: Los usuarios pueden insertar su propio voto
CREATE POLICY votos_insert_own ON votos
    FOR INSERT
    WITH CHECK (auth.uid()::text = usuario_id::text);

-- Política: Los usuarios pueden ver sus propias reclamaciones
CREATE POLICY reclamaciones_select_own ON reclamaciones
    FOR SELECT
    USING (auth.uid()::text = usuario_id::text);

-- Política: Los usuarios pueden insertar sus propias reclamaciones
CREATE POLICY reclamaciones_insert_own ON reclamaciones
    FOR INSERT
    WITH CHECK (auth.uid()::text = usuario_id::text);

-- Política: Los usuarios pueden ver sus propios mensajes
CREATE POLICY mensajes_select_own ON mensajes_chat
    FOR SELECT
    USING (auth.uid()::text = usuario_id::text);

-- Política: Los usuarios pueden insertar sus propios mensajes
CREATE POLICY mensajes_insert_own ON mensajes_chat
    FOR INSERT
    WITH CHECK (auth.uid()::text = usuario_id::text);

-- =====================================================
-- VISTAS ÚTILES
-- =====================================================

-- Vista: Usuarios con información de votación
CREATE OR REPLACE VIEW vista_usuarios_votacion AS
SELECT 
    u.id,
    u.nombres,
    u.apellidos,
    u.dni,
    u.correo,
    u.distrito,
    u.departamento,
    u.role,
    u.verificado,
    CASE WHEN v.id IS NOT NULL THEN TRUE ELSE FALSE END as ha_votado,
    v.fecha_voto
FROM usuarios u
LEFT JOIN votos v ON u.id = v.usuario_id AND v.completado = TRUE;

-- Vista: Resumen de reclamaciones
CREATE OR REPLACE VIEW vista_resumen_reclamaciones AS
SELECT 
    tipo,
    estado,
    COUNT(*) as total,
    COUNT(CASE WHEN fecha_respuesta IS NOT NULL THEN 1 END) as respondidas
FROM reclamaciones
GROUP BY tipo, estado;

-- =====================================================
-- COMENTARIOS EN TABLAS
-- =====================================================

COMMENT ON TABLE usuarios IS 'Tabla de usuarios del sistema con autenticación y roles';
COMMENT ON TABLE candidatos IS 'Candidatos para elecciones (Presidente y Alcalde)';
COMMENT ON TABLE votos IS 'Registro de votos de usuarios (un voto por usuario)';
COMMENT ON TABLE reclamaciones IS 'Libro de reclamaciones (quejas, reclamos, sugerencias)';
COMMENT ON TABLE mensajes_chat IS 'Historial de conversaciones con el chatbot';

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================

-- Verificar que todo se creó correctamente
SELECT 'Tablas creadas exitosamente' as mensaje;
SELECT table_name FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;
