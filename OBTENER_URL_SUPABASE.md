# 🔗 Cómo Obtener la URL Correcta de Supabase

## ❌ Problema Actual

La conexión a Supabase está fallando porque necesitamos la cadena de conexión exacta desde tu dashboard.

## ✅ Solución: Obtener la URL desde Supabase

### Paso 1: Ir al Dashboard de Supabase

1. Ve a: **https://ubbnhtkzsrcexiwgimnp.supabase.co**
2. Inicia sesión con tu cuenta

### Paso 2: Ir a Database Settings

1. En el menú lateral izquierdo, haz clic en el ícono de **Settings** (⚙️)
2. Luego haz clic en **Database**

### Paso 3: Copiar la Connection String

Busca la sección **Connection String** y verás varias opciones:

#### Opción A: URI (Recomendada)
```
postgresql://postgres:[YOUR-PASSWORD]@db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres
```

#### Opción B: JDBC
```
jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres
```

#### Opción C: Connection Pooling (Session Mode)
```
postgresql://postgres:[YOUR-PASSWORD]@aws-0-us-east-1.pooler.supabase.com:6543/postgres
```

### Paso 4: Reemplazar el Password

En la cadena que copiaste, reemplaza `[YOUR-PASSWORD]` con tu password real:
```
naomicomekk123xd
```

### Paso 5: Configurar en application.properties

Abre: `src/main/resources/application.properties`

**Si copiaste la URI:**
```properties
spring.datasource.url=jdbc:postgresql://db.ubbnhtkzsrcexiwgimnp.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=naomicomekk123xd
```

**Si copiaste la Connection Pooling:**
```properties
spring.datasource.url=jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres
spring.datasource.username=postgres
spring.datasource.password=naomicomekk123xd
```

---

## 📸 Guía Visual

### 1. Dashboard de Supabase
```
┌─────────────────────────────────────┐
│ 🏠 Home                             │
│ 📊 Table Editor                     │
│ 🔐 Authentication                   │
│ 📁 Storage                          │
│ ⚙️  Settings  ← CLIC AQUÍ          │
│    └─ Database  ← LUEGO AQUÍ       │
└─────────────────────────────────────┘
```

### 2. Database Settings
```
┌─────────────────────────────────────┐
│ Database Settings                   │
├─────────────────────────────────────┤
│ Connection String                   │
│                                     │
│ URI:                                │
│ postgresql://postgres:[YOUR-PASS... │
│ [Copy] ← CLIC AQUÍ                  │
│                                     │
│ JDBC:                               │
│ jdbc:postgresql://db.ubbnhtk...     │
│ [Copy]                              │
│                                     │
│ Connection Pooling:                 │
│ postgresql://postgres:[YOUR-PASS... │
│ [Copy]                              │
└─────────────────────────────────────┘
```

---

## 🧪 Probar la Conexión

Una vez que tengas la URL correcta:

### 1. Actualizar application.properties
```properties
# Comentar H2
#spring.datasource.url=jdbc:h2:mem:testdb
#...

# Descomentar y pegar la URL de Supabase
spring.datasource.url=LA_URL_QUE_COPIASTE
spring.datasource.username=postgres
spring.datasource.password=naomicomekk123xd
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 2. Reiniciar la aplicación
```bash
./mvnw spring-boot:run
```

### 3. Verificar en los logs
Deberías ver:
```
HikariPool-1 - Starting...
HikariPool-1 - Added connection
Database JDBC URL [jdbc:postgresql://...]
Hibernate: create table if not exists usuarios ...
```

---

## 🔧 Alternativa: Usar Supabase CLI

Si tienes problemas con el dashboard, puedes usar el CLI:

```bash
# Instalar Supabase CLI
npm install -g supabase

# Login
supabase login

# Ver connection string
supabase db show
```

---

## 📝 Información que Necesito

Para ayudarte mejor, copia y pégame:

1. **La Connection String completa** (sin el password, solo la estructura)
2. **El tipo de conexión** (Direct o Pooling)
3. **La región** (us-east-1, eu-west-1, etc.)

Ejemplo:
```
jdbc:postgresql://db.XXXXX.supabase.co:5432/postgres
```

O si prefieres, toma una captura de pantalla de la sección "Connection String" en tu dashboard (ocultando el password).

---

## ⚠️ Nota Importante

Por ahora, la aplicación está usando **H2** (base de datos en memoria) para que puedas seguir trabajando.

Cuando tengas la URL correcta de Supabase, solo necesitas:
1. Actualizar `application.properties`
2. Reiniciar la aplicación
3. ¡Listo!
