# 🧪 Probar Conexión con Supabase

## Opción 1: Cambiar a Supabase Ahora

### Paso 1: Detener la aplicación actual
Presiona `Ctrl+C` en la terminal donde está corriendo

### Paso 2: Editar application.properties

Abre: `src/main/resources/application.properties`

**Comenta H2 (agrega # al inicio):**
```properties
# Database H2 (Desarrollo Local)
#spring.datasource.url=jdbc:h2:mem:testdb
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
#spring.h2.console.enabled=true
#spring.jpa.hibernate.ddl-auto=update
```

**Descomenta Supabase (quita el #):**
```properties
# Database PostgreSQL - Supabase (Producción)
spring.datasource.url=jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=naomicomekk123xd
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Paso 3: Reiniciar
```bash
./mvnw spring-boot:run
```

### Paso 4: Verificar en los logs
Deberías ver:
```
Database JDBC URL [jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres]
Hibernate: create table if not exists usuarios ...
```

---

## Opción 2: Usar Perfil Supabase (Sin Modificar Archivos)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase
```

---

## ✅ Verificar que Funciona

### 1. Registrar un usuario de prueba

```bash
POST http://localhost:8080/api/auth/registro
{
  "dni": "87654321",
  "dniDigitoVerificador": "9",
  "nombres": "PRUEBA",
  "apellidos": "SUPABASE TEST",
  "correo": "prueba@supabase.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

### 2. Ver en Supabase Dashboard

1. Ve a: https://ubbnhtkzsrcexiwgimnp.supabase.co
2. Inicia sesión
3. Ve a **Table Editor** (icono de tabla en el menú izquierdo)
4. Busca la tabla `usuarios`
5. Deberías ver el usuario que acabas de registrar

---

## 🎯 ¿Qué Base de Datos Usar?

### Usa H2 (actual) si:
- ✅ Estás desarrollando localmente
- ✅ Quieres probar rápido sin internet
- ✅ No necesitas persistencia de datos

### Usa Supabase si:
- ✅ Quieres que los datos persistan
- ✅ Vas a desplegar en producción
- ✅ Necesitas acceso desde múltiples lugares
- ✅ Quieres backups automáticos

---

## 🔄 Cambiar Entre Bases de Datos

Es fácil cambiar entre H2 y Supabase:

1. Edita `application.properties`
2. Comenta una configuración
3. Descomenta la otra
4. Reinicia la aplicación

O usa perfiles:
- `./mvnw spring-boot:run` → H2
- `./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase` → Supabase

---

## 📊 Estado Actual

✅ PostgreSQL instalado
✅ Credenciales configuradas
✅ Archivo de perfil creado
✅ Listo para conectar

**Solo falta:** Cambiar la configuración y reiniciar
