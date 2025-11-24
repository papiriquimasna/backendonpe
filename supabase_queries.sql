-- =====================================================
-- CONSULTAS ÚTILES PARA EL FRONTEND
-- =====================================================

-- =====================================================
-- USUARIOS
-- =====================================================

-- Obtener usuario por DNI
SELECT * FROM usuarios WHERE dni = '12345678';

-- Obtener usuario por correo
SELECT * FROM usuarios WHERE correo = 'usuario@example.com';

-- Verificar si un usuario ya votó
SELECT 
    u.id,
    u.nombres,
    u.apellidos,
    CASE WHEN v.id IS NOT NULL THEN TRUE ELSE FALSE END as ha_votado
FROM usuarios u
LEFT JOIN votos v ON u.id = v.usuario_id AND v.completado = TRUE
WHERE u.dni = '12345678';

-- Listar todos los usuarios (solo para admins)
SELECT 
    id,
    nombres,
    apellidos,
    dni,
    correo,
    distrito,
    departamento,
    role,
    verificado,
    activo,
    fecha_creacion
FROM usuarios
ORDER BY fecha_creacion DESC;

-- =====================================================
-- CANDIDATOS
-- =====================================================

-- Obtener candidatos a Presidente
SELECT 
    id,
    nombre,
    partido,
    propuestas,
    votos
FROM candidatos
WHERE tipo = 'PRESIDENTE' AND activo = TRUE
ORDER BY nombre;

-- Obtener candidatos a Alcalde
SELECT 
    id,
    nombre,
    partido,
    propuestas,
    votos
FROM candidatos
WHERE tipo = 'ALCALDE' AND activo = TRUE
ORDER BY nombre;

-- Obtener todos los candidatos con sus votos
SELECT 
    id,
    nombre,
    partido,
    tipo,
    propuestas,
    votos,
    ROUND((votos::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
FROM candidatos
WHERE activo = TRUE
ORDER BY tipo, votos DESC;

-- =====================================================
-- VOTACIÓN
-- =====================================================

-- Registrar voto por presidente (paso 1)
INSERT INTO votos (usuario_id, candidato_presidente_id, completado)
VALUES (1, 2, FALSE)
ON CONFLICT (usuario_id) 
DO UPDATE SET candidato_presidente_id = EXCLUDED.candidato_presidente_id;

-- Completar voto con alcalde (paso 2)
UPDATE votos
SET candidato_alcalde_id = 5, completado = TRUE, fecha_voto = CURRENT_TIMESTAMP
WHERE usuario_id = 1 AND completado = FALSE;

-- Incrementar contador de votos del candidato
UPDATE candidatos SET votos = votos + 1 WHERE id = 2;

-- Verificar si usuario tiene voto en progreso
SELECT 
    v.id,
    v.candidato_presidente_id,
    v.candidato_alcalde_id,
    v.completado,
    cp.nombre as nombre_presidente
FROM votos v
LEFT JOIN candidatos cp ON v.candidato_presidente_id = cp.id
WHERE v.usuario_id = 1 AND v.completado = FALSE;

-- Obtener voto completo de un usuario
SELECT 
    v.id,
    v.fecha_voto,
    cp.nombre as presidente,
    cp.partido as partido_presidente,
    ca.nombre as alcalde,
    ca.partido as partido_alcalde
FROM votos v
LEFT JOIN candidatos cp ON v.candidato_presidente_id = cp.id
LEFT JOIN candidatos ca ON v.candidato_alcalde_id = ca.id
WHERE v.usuario_id = 1 AND v.completado = TRUE;

-- =====================================================
-- RESULTADOS DE VOTACIÓN
-- =====================================================

-- Resultados de Presidentes
SELECT * FROM obtener_resultados_candidato('PRESIDENTE');

-- Resultados de Alcaldes
SELECT * FROM obtener_resultados_candidato('ALCALDE');

-- Estadísticas generales
SELECT * FROM obtener_estadisticas_votacion();

-- Ganador Presidente
SELECT 
    nombre,
    partido,
    votos,
    ROUND((votos::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
FROM candidatos
WHERE tipo = 'PRESIDENTE' AND activo = TRUE
ORDER BY votos DESC
LIMIT 1;

-- Ganador Alcalde
SELECT 
    nombre,
    partido,
    votos,
    ROUND((votos::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
FROM candidatos
WHERE tipo = 'ALCALDE' AND activo = TRUE
ORDER BY votos DESC
LIMIT 1;

-- =====================================================
-- RECLAMACIONES
-- =====================================================

-- Crear reclamación
INSERT INTO reclamaciones (usuario_id, asunto, descripcion, tipo)
VALUES (1, 'Problema con el servicio', 'Descripción detallada...', 'QUEJA')
RETURNING *;

-- Crear reclamación con evidencia
INSERT INTO reclamaciones (usuario_id, asunto, descripcion, tipo, evidencia)
VALUES (1, 'Cobro indebido', 'Descripción...', 'RECLAMO', 'evidencia_123.jpg')
RETURNING *;

-- Obtener reclamaciones de un usuario
SELECT 
    id,
    asunto,
    descripcion,
    tipo,
    estado,
    evidencia,
    respuesta,
    fecha_creacion,
    fecha_respuesta
FROM reclamaciones
WHERE usuario_id = 1
ORDER BY fecha_creacion DESC;

-- Obtener todas las reclamaciones (para admins)
SELECT 
    r.id,
    r.asunto,
    r.descripcion,
    r.tipo,
    r.estado,
    r.evidencia,
    r.respuesta,
    r.fecha_creacion,
    r.fecha_respuesta,
    u.nombres || ' ' || u.apellidos as usuario,
    u.correo,
    u.dni
FROM reclamaciones r
INNER JOIN usuarios u ON r.usuario_id = u.id
ORDER BY r.fecha_creacion DESC;

-- Actualizar estado de reclamación (admin)
UPDATE reclamaciones
SET estado = 'EN_PROCESO'
WHERE id = 1;

-- Responder reclamación (admin)
UPDATE reclamaciones
SET respuesta = 'Hemos revisado tu caso...', 
    estado = 'RESUELTO',
    fecha_respuesta = CURRENT_TIMESTAMP
WHERE id = 1;

-- Estadísticas de reclamaciones
SELECT * FROM vista_resumen_reclamaciones;

-- =====================================================
-- CHATBOT
-- =====================================================

-- Guardar mensaje del chat
INSERT INTO mensajes_chat (usuario_id, mensaje, respuesta)
VALUES (1, '¿Cómo puedo votar?', 'Para votar, di "quiero votar" y te guiaré...')
RETURNING *;

-- Obtener historial de chat de un usuario
SELECT 
    id,
    mensaje,
    respuesta,
    fecha_creacion
FROM mensajes_chat
WHERE usuario_id = 1
ORDER BY fecha_creacion DESC
LIMIT 50;

-- Obtener último mensaje
SELECT 
    mensaje,
    respuesta,
    fecha_creacion
FROM mensajes_chat
WHERE usuario_id = 1
ORDER BY fecha_creacion DESC
LIMIT 1;

-- Estadísticas de uso del chatbot
SELECT 
    COUNT(*) as total_mensajes,
    COUNT(DISTINCT usuario_id) as usuarios_unicos,
    DATE(fecha_creacion) as fecha
FROM mensajes_chat
GROUP BY DATE(fecha_creacion)
ORDER BY fecha DESC;

-- =====================================================
-- GESTIÓN DE ROLES (SUPERADMIN)
-- =====================================================

-- Cambiar role de usuario
UPDATE usuarios
SET role = 'ADMINISTRADOR'
WHERE id = 2;

-- Listar usuarios por role
SELECT 
    id,
    nombres,
    apellidos,
    dni,
    correo,
    role,
    fecha_creacion
FROM usuarios
WHERE role = 'ADMINISTRADOR'
ORDER BY fecha_creacion DESC;

-- Activar/Desactivar usuario
UPDATE usuarios SET activo = FALSE WHERE id = 2;
UPDATE usuarios SET activo = TRUE WHERE id = 2;

-- =====================================================
-- PERFIL DE USUARIO
-- =====================================================

-- Obtener perfil completo
SELECT 
    id,
    nombres,
    apellidos,
    dni,
    dni_digito_verificador,
    correo,
    distrito,
    departamento,
    foto_perfil,
    role,
    verificado,
    fecha_creacion
FROM usuarios
WHERE id = 1;

-- Actualizar foto de perfil
UPDATE usuarios
SET foto_perfil = 'foto_123.jpg'
WHERE id = 1;

-- =====================================================
-- DASHBOARD / ESTADÍSTICAS
-- =====================================================

-- Resumen general del sistema
SELECT 
    (SELECT COUNT(*) FROM usuarios WHERE verificado = TRUE) as total_usuarios,
    (SELECT COUNT(*) FROM votos WHERE completado = TRUE) as total_votos,
    (SELECT COUNT(*) FROM reclamaciones) as total_reclamaciones,
    (SELECT COUNT(*) FROM reclamaciones WHERE estado = 'PENDIENTE') as reclamaciones_pendientes,
    (SELECT COUNT(*) FROM candidatos WHERE activo = TRUE) as total_candidatos;

-- Actividad reciente
SELECT 
    'voto' as tipo,
    u.nombres || ' ' || u.apellidos as usuario,
    v.fecha_voto as fecha
FROM votos v
INNER JOIN usuarios u ON v.usuario_id = u.id
WHERE v.completado = TRUE
UNION ALL
SELECT 
    'reclamacion' as tipo,
    u.nombres || ' ' || u.apellidos as usuario,
    r.fecha_creacion as fecha
FROM reclamaciones r
INNER JOIN usuarios u ON r.usuario_id = u.id
ORDER BY fecha DESC
LIMIT 20;

-- Usuarios registrados por día
SELECT 
    DATE(fecha_creacion) as fecha,
    COUNT(*) as total
FROM usuarios
WHERE verificado = TRUE
GROUP BY DATE(fecha_creacion)
ORDER BY fecha DESC
LIMIT 30;

-- =====================================================
-- BÚSQUEDAS
-- =====================================================

-- Buscar usuarios
SELECT 
    id,
    nombres,
    apellidos,
    dni,
    correo,
    role
FROM usuarios
WHERE 
    nombres ILIKE '%juan%' OR
    apellidos ILIKE '%juan%' OR
    dni LIKE '%123%' OR
    correo ILIKE '%juan%'
LIMIT 20;

-- Buscar reclamaciones
SELECT 
    r.id,
    r.asunto,
    r.tipo,
    r.estado,
    u.nombres || ' ' || u.apellidos as usuario
FROM reclamaciones r
INNER JOIN usuarios u ON r.usuario_id = u.id
WHERE 
    r.asunto ILIKE '%problema%' OR
    r.descripcion ILIKE '%problema%'
ORDER BY r.fecha_creacion DESC
LIMIT 20;

-- =====================================================
-- LIMPIEZA Y MANTENIMIENTO
-- =====================================================

-- Eliminar códigos de verificación expirados
UPDATE usuarios
SET codigo_verificacion = NULL, codigo_expiracion = NULL
WHERE codigo_expiracion < CURRENT_TIMESTAMP;

-- Eliminar votos incompletos antiguos (más de 24 horas)
DELETE FROM votos
WHERE completado = FALSE 
AND fecha_voto < CURRENT_TIMESTAMP - INTERVAL '24 hours';

-- =====================================================
-- EXPORTAR DATOS
-- =====================================================

-- Exportar resultados de votación (CSV)
COPY (
    SELECT 
        c.nombre,
        c.partido,
        c.tipo,
        c.votos,
        ROUND((c.votos::NUMERIC / NULLIF((SELECT COUNT(*) FROM votos WHERE completado = TRUE), 0)) * 100, 2) as porcentaje
    FROM candidatos c
    WHERE c.activo = TRUE
    ORDER BY c.tipo, c.votos DESC
) TO '/tmp/resultados_votacion.csv' WITH CSV HEADER;

-- Exportar lista de votantes
COPY (
    SELECT 
        u.dni,
        u.nombres,
        u.apellidos,
        u.distrito,
        v.fecha_voto
    FROM usuarios u
    INNER JOIN votos v ON u.id = v.usuario_id
    WHERE v.completado = TRUE
    ORDER BY v.fecha_voto
) TO '/tmp/lista_votantes.csv' WITH CSV HEADER;
