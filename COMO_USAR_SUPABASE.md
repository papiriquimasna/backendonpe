# 🚀 Cómo Conectar con Supabase

## ✅ Configuración Completada

Tu proyecto ya está configurado para usar Supabase. Ahora tienes 2 opciones:

---

## 🔧 Opción 1: Cambiar Manualmente (Recomendado para Producción)

### Paso 1: Editar `application.properties`

Abre: `src/main/resources/application.properties`

**Comenta las líneas de H2:**
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

**Descomenta las líneas de Supabase:**
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

### Paso 2: Reiniciar la Aplicación

```bash
# Detener la aplicación actual (Ctrl+C)
# Luego ejecutar:
./mvnw spring-boot:run
```

---

## 🎯 Opción 2: Usar Perfiles (Más Flexible)

### Para Desarrollo (H2):
```bash
./mvnw spring-boot:run
```

### Para Producción (Supabase):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase
```

---

## 📊 Verificar la Conexión

### 1. Revisar los Logs

Cuando inicies la aplicación, deberías ver:

**Con H2:**
```
Database JDBC URL [jdbc:h2:mem:testdb]
H2 console available at '/h2-console'
```

**Con Supabase:**
```
Database JDBC URL [jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres]
```

### 2. Probar un Registro

```bash
POST http://localhost:8080/api/auth/registro
{
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "nombres": "TEST",
  "apellidos": "SUPABASE",
  "correo": "test@supabase.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

### 3. Verificar en Supabase

1. Ve a: https://ubbnhtkzsrcexiwgimnp.supabase.co
2. Inicia sesión
3. Ve a **Table Editor**
4. Busca la tabla `usuarios`
5. Deberías ver el registro que acabas de crear

---

## 🗄️ Crear las Tablas en Supabase

Si es la primera vez que usas Supabase, las tablas se crearán automáticamente gracias a:
```properties
spring.jpa.hibernate.ddl-auto=update
```

Pero si prefieres crearlas manualmente, usa el archivo `supabase_schema.sql`:

1. Ve a Supabase Dashboard
2. Haz clic en **SQL Editor**
3. Copia y pega el contenido de `supabase_schema.sql`
4. Ejecuta el script

---

## 🔄 Migrar Datos de H2 a Supabase

Si ya tienes datos en H2 y quieres migrarlos:

### Opción 1: Exportar/Importar Manual
1. Conéctate a H2 Console: http://localhost:8080/h2-console
2. Exporta los datos como SQL
3. Importa en Supabase SQL Editor

### Opción 2: Dejar que Hibernate lo Haga
1. Cambia a Supabase
2. Vuelve a registrar los usuarios
3. Los datos se crearán automáticamente

---

## ⚙️ Configuración Actual

### Credenciales Supabase:
- **URL:** https://ubbnhtkzsrcexiwgimnp.supabase.co
- **Database Host:** db.ubbnhtkzsrcexiwgimnp.supabase.co
- **Port:** 5432
- **Database:** postgres
- **Username:** postgres
- **Password:** naomicomekk123xd

### Service Role Key (para API REST):
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InViYm5odGt6c3JjZXhpd2dpbW5wIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc2MzkxODgzMSwiZXhwIjoyMDc5NDk0ODMxfQ.m0joVinUQ8F7KV4SDFCJhr0xl50pbOfm9U_3L3Ffekw
```

---

## 🔒 Seguridad

⚠️ **IMPORTANTE:** Las credenciales están en texto plano en `application.properties`

Para producción, usa variables de entorno:

```properties
spring.datasource.url=${SUPABASE_DB_URL}
spring.datasource.username=${SUPABASE_DB_USER}
spring.datasource.password=${SUPABASE_DB_PASSWORD}
```

Y configura las variables en tu servidor:
```bash
export SUPABASE_DB_URL=jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres
export SUPABASE_DB_USER=postgres
export SUPABASE_DB_PASSWORD=naomicomekk123xd
```

---

## 🐛 Problemas Comunes

### Error: "Connection refused"
- ✅ Verifica que las credenciales sean correctas
- ✅ Verifica que tu IP esté permitida en Supabase (Settings → Database → Connection Pooling)

### Error: "Authentication failed"
- ✅ Verifica el password
- ✅ Verifica el username (debe ser `postgres`)

### Error: "Relation does not exist"
- ✅ Asegúrate de que `spring.jpa.hibernate.ddl-auto=update` esté configurado
- ✅ O ejecuta manualmente el script `supabase_schema.sql`

### Las tablas no se crean automáticamente
- ✅ Cambia `ddl-auto` a `create` la primera vez (luego vuelve a `update`)
- ✅ O crea las tablas manualmente con el SQL

---

## 📝 Comandos Útiles

### Iniciar con H2 (desarrollo):
```bash
./mvnw spring-boot:run
```

### Iniciar con Supabase (producción):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase
```

### Ver logs de SQL:
Agrega en `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## ✅ Checklist de Migración

- [ ] PostgreSQL agregado al `pom.xml`
- [ ] Credenciales configuradas en `application.properties`
- [ ] Aplicación reiniciada
- [ ] Conexión verificada en logs
- [ ] Tablas creadas en Supabase
- [ ] Registro de prueba exitoso
- [ ] Datos visibles en Supabase Dashboard

---

## 🎉 ¡Listo!

Tu aplicación ahora puede usar Supabase como base de datos en producción.

**Ventajas de Supabase:**
- ✅ Base de datos PostgreSQL en la nube
- ✅ Backups automáticos
- ✅ Escalable
- ✅ Dashboard visual
- ✅ API REST automática
- ✅ Autenticación integrada (opcional)
