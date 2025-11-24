## 🗄️ Configuración de Supabase para el Frontend

## 📋 Pasos para Configurar

### 1. Crear Proyecto en Supabase

1. Ve a: https://supabase.com/
2. Click en **"Start your project"**
3. Crea una cuenta o inicia sesión
4. Click en **"New Project"**
5. Completa:
   - **Name:** Sistema Votación
   - **Database Password:** (guarda esta contraseña)
   - **Region:** South America (São Paulo)
6. Click en **"Create new project"**

---

### 2. Ejecutar el Script SQL

1. En tu proyecto de Supabase, ve a **SQL Editor** (menú lateral)
2. Click en **"New query"**
3. Copia todo el contenido de `supabase_schema.sql`
4. Pégalo en el editor
5. Click en **"Run"** (o presiona Ctrl+Enter)
6. Verás el mensaje: **"Tablas creadas exitosamente"**

---

### 3. Obtener Credenciales

1. Ve a **Settings** → **API**
2. Copia estos valores:

```
Project URL: https://xxxxxxxxxxxxx.supabase.co
anon/public key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
service_role key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

### 4. Configurar en tu Frontend

#### React/Next.js:

```bash
npm install @supabase/supabase-js
```

```javascript
// lib/supabase.js
import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://xxxxxxxxxxxxx.supabase.co'
const supabaseAnonKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'

export const supabase = createClient(supabaseUrl, supabaseAnonKey)
```

#### Vue.js:

```bash
npm install @supabase/supabase-js
```

```javascript
// plugins/supabase.js
import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://xxxxxxxxxxxxx.supabase.co'
const supabaseAnonKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'

export const supabase = createClient(supabaseUrl, supabaseAnonKey)
```

---

## 📊 Estructura de Tablas

### USUARIOS
```sql
- id (BIGSERIAL)
- nombres (VARCHAR)
- apellidos (VARCHAR)
- dni (VARCHAR) UNIQUE
- dni_digito_verificador (VARCHAR)
- correo (VARCHAR) UNIQUE
- distrito (VARCHAR)
- departamento (VARCHAR)
- pin (VARCHAR) -- BCrypt hash
- foto_perfil (VARCHAR)
- role (VARCHAR) -- USUARIO, ADMINISTRADOR, SUPERADMINISTRADOR
- codigo_verificacion (VARCHAR)
- codigo_expiracion (TIMESTAMP)
- verificado (BOOLEAN)
- activo (BOOLEAN)
- fecha_creacion (TIMESTAMP)
```

### CANDIDATOS
```sql
- id (BIGSERIAL)
- nombre (VARCHAR)
- partido (VARCHAR)
- tipo (VARCHAR) -- PRESIDENTE, ALCALDE
- propuestas (TEXT)
- activo (BOOLEAN)
- votos (INTEGER)
- fecha_creacion (TIMESTAMP)
```

### VOTOS
```sql
- id (BIGSERIAL)
- usuario_id (BIGINT) FK → usuarios
- candidato_presidente_id (BIGINT) FK → candidatos
- candidato_alcalde_id (BIGINT) FK → candidatos
- fecha_voto (TIMESTAMP)
- completado (BOOLEAN)
```

### RECLAMACIONES
```sql
- id (BIGSERIAL)
- usuario_id (BIGINT) FK → usuarios
- asunto (VARCHAR)
- descripcion (TEXT)
- tipo (VARCHAR) -- QUEJA, RECLAMO, SUGERENCIA
- estado (VARCHAR) -- PENDIENTE, EN_PROCESO, RESUELTO, CERRADO
- evidencia (VARCHAR)
- respuesta (TEXT)
- fecha_creacion (TIMESTAMP)
- fecha_respuesta (TIMESTAMP)
```

### MENSAJES_CHAT
```sql
- id (BIGSERIAL)
- usuario_id (BIGINT) FK → usuarios
- mensaje (TEXT)
- respuesta (TEXT)
- fecha_creacion (TIMESTAMP)
```

---

## 🔐 Datos Iniciales

### SuperAdministrador
```
DNI: 99999999
PIN: 999999
Correo: admin@sistema.com
```

### Candidatos Presidentes
1. María Elena Torres - Partido Progresista
2. Carlos Mendoza - Alianza Nacional
3. Ana Lucía Vargas - Frente Democrático

### Candidatos Alcaldes
4. Jorge Ramírez - Movimiento Municipal
5. Patricia Flores - Lima para Todos
6. Roberto Silva - Independiente

---

## 💻 Ejemplos de Uso en Frontend

### Registro de Usuario

```javascript
// Registrar usuario
const { data, error } = await supabase
  .from('usuarios')
  .insert([
    {
      nombres: 'Juan',
      apellidos: 'Pérez',
      dni: '12345678',
      dni_digito_verificador: '9',
      correo: 'juan@example.com',
      distrito: 'Lima',
      departamento: 'Lima',
      pin: '$2a$10$...', // Hash BCrypt
      verificado: false
    }
  ])
  .select()
```

### Login

```javascript
// Buscar usuario por DNI
const { data: usuario, error } = await supabase
  .from('usuarios')
  .select('*')
  .eq('dni', '12345678')
  .single()

// Verificar PIN con bcrypt en el frontend
import bcrypt from 'bcryptjs'
const pinValido = await bcrypt.compare('123456', usuario.pin)
```

### Obtener Candidatos

```javascript
// Candidatos a Presidente
const { data: presidentes } = await supabase
  .from('candidatos')
  .select('*')
  .eq('tipo', 'PRESIDENTE')
  .eq('activo', true)
  .order('nombre')

// Candidatos a Alcalde
const { data: alcaldes } = await supabase
  .from('candidatos')
  .select('*')
  .eq('tipo', 'ALCALDE')
  .eq('activo', true)
  .order('nombre')
```

### Votar

```javascript
// Paso 1: Votar por Presidente
const { data: voto } = await supabase
  .from('votos')
  .insert([
    {
      usuario_id: 1,
      candidato_presidente_id: 2,
      completado: false
    }
  ])
  .select()

// Paso 2: Completar con Alcalde
const { data: votoCompleto } = await supabase
  .from('votos')
  .update({
    candidato_alcalde_id: 5,
    completado: true,
    fecha_voto: new Date().toISOString()
  })
  .eq('usuario_id', 1)
  .eq('completado', false)

// Incrementar votos
await supabase.rpc('incrementar_votos', { candidato_id: 2 })
```

### Verificar si ya votó

```javascript
const { data: voto } = await supabase
  .from('votos')
  .select('*')
  .eq('usuario_id', 1)
  .eq('completado', true)
  .single()

const yaVoto = voto !== null
```

### Crear Reclamación

```javascript
const { data: reclamacion } = await supabase
  .from('reclamaciones')
  .insert([
    {
      usuario_id: 1,
      asunto: 'Problema con el servicio',
      descripcion: 'Descripción detallada...',
      tipo: 'QUEJA'
    }
  ])
  .select()
```

### Chatbot

```javascript
// Guardar mensaje
const { data: mensaje } = await supabase
  .from('mensajes_chat')
  .insert([
    {
      usuario_id: 1,
      mensaje: '¿Cómo puedo votar?',
      respuesta: 'Para votar, di "quiero votar"...'
    }
  ])
  .select()

// Obtener historial
const { data: historial } = await supabase
  .from('mensajes_chat')
  .select('*')
  .eq('usuario_id', 1)
  .order('fecha_creacion', { ascending: false })
  .limit(50)
```

### Obtener Resultados

```javascript
// Resultados de Presidentes
const { data: resultados } = await supabase
  .rpc('obtener_resultados_candidato', { tipo_candidato: 'PRESIDENTE' })

// Estadísticas generales
const { data: stats } = await supabase
  .rpc('obtener_estadisticas_votacion')
```

---

## 🔒 Seguridad (Row Level Security)

Las políticas RLS ya están configuradas en el script. Esto significa:

- ✅ Los usuarios solo pueden ver sus propios datos
- ✅ Los usuarios solo pueden votar una vez
- ✅ Los usuarios solo pueden ver sus propias reclamaciones
- ✅ Los administradores tienen acceso completo

---

## 📱 Storage para Imágenes

### Configurar Storage

1. En Supabase, ve a **Storage**
2. Click en **"Create bucket"**
3. Nombre: `fotos-perfil`
4. Public: ✅ (para que las fotos sean accesibles)
5. Click en **"Create bucket"**

6. Repite para: `evidencias-reclamaciones`

### Subir Foto de Perfil

```javascript
// Subir archivo
const { data, error } = await supabase.storage
  .from('fotos-perfil')
  .upload(`${userId}/perfil.jpg`, file)

// Obtener URL pública
const { data: { publicUrl } } = supabase.storage
  .from('fotos-perfil')
  .getPublicUrl(`${userId}/perfil.jpg`)

// Actualizar en la base de datos
await supabase
  .from('usuarios')
  .update({ foto_perfil: publicUrl })
  .eq('id', userId)
```

---

## 🔧 Funciones Útiles

El script incluye funciones SQL útiles:

### `obtener_estadisticas_votacion()`
Devuelve: total_usuarios, total_votos, porcentaje_participacion

### `obtener_resultados_candidato(tipo)`
Devuelve: candidato_id, nombre, partido, total_votos, porcentaje

---

## 📊 Vistas Disponibles

### `vista_usuarios_votacion`
Usuarios con información de si han votado

### `vista_resumen_reclamaciones`
Resumen de reclamaciones por tipo y estado

---

## 🎯 Próximos Pasos

1. ✅ Ejecutar `supabase_schema.sql` en Supabase
2. ✅ Configurar credenciales en tu frontend
3. ✅ Instalar `@supabase/supabase-js`
4. ✅ Crear componentes de UI
5. ✅ Implementar autenticación
6. ✅ Conectar con los endpoints

---

## 📚 Recursos

- Documentación Supabase: https://supabase.com/docs
- Supabase JS Client: https://supabase.com/docs/reference/javascript
- Ejemplos: https://github.com/supabase/supabase/tree/master/examples

---

**¡Todo listo para conectar con tu frontend!** 🚀
