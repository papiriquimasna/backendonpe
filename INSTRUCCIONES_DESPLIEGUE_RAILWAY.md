# 🚀 Desplegar Backend en Railway

## ✅ El código ya está en GitHub
Repositorio: https://github.com/papiriquimasna/backendonpe

---

## 📝 Pasos para desplegar

### 1️⃣ Ir a Railway
1. Abre https://railway.app
2. Click en "Start a New Project"
3. Conecta con GitHub (si no lo has hecho)
4. Click en "Deploy from GitHub repo"
5. Selecciona: `papiriquimasna/backendonpe`

### 2️⃣ Configurar variables de entorno
Railway detectará automáticamente que es un proyecto Java/Maven.

Ve a **Settings → Variables** y agrega las variables del archivo `.env` que tienes localmente.

**IMPORTANTE**: Copia todas las variables del archivo `.env` (que no se subió a GitHub por seguridad).

Las variables necesarias son:
- `SPRING_PROFILES_ACTIVE`
- `PORT`
- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `SUPABASE_URL`
- `SUPABASE_ANON_KEY`
- `SUPABASE_SERVICE_ROLE_KEY`
- `JWT_SECRET`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `RENIEC_API_TOKEN`

### 3️⃣ Esperar el despliegue
- Railway construirá el proyecto automáticamente
- Tomará 2-5 minutos
- Verás los logs en tiempo real
- Cuando veas "Started ProyectoApplication", estará listo

### 4️⃣ Obtener la URL
1. Ve a **Settings → Domains**
2. Click en "Generate Domain"
3. Railway te dará una URL como: `https://backendonpe-production-xxxx.up.railway.app`
4. Copia esta URL

### 5️⃣ Probar el backend
Abre en el navegador:
```
https://tu-url-de-railway.up.railway.app/actuator/health
```

Deberías ver:
```json
{"status":"UP"}
```

---

## 🎨 Configurar el frontend

### Crear archivo `.env.local` en `onpe-app/`:
```bash
VITE_API_URL=https://tu-url-de-railway.up.railway.app/api
```

Reemplaza `tu-url-de-railway.up.railway.app` con la URL real.

### Ejecutar frontend:
```bash
cd onpe-app
npm run dev
```

---

## ✅ ¡Listo!

Tu arquitectura:
- **Frontend**: `http://localhost:5173` (tu PC)
- **Backend**: `https://backendonpe-production-xxxx.up.railway.app` (Railway)
- **Base de datos**: Supabase PostgreSQL (nube)

El frontend local se conectará al backend en Railway, y el backend se conectará a Supabase.

---

## 🔍 Verificar logs

Si algo falla:
1. Ve a Railway → tu proyecto
2. Click en "View Logs"
3. Busca errores en rojo
4. Verifica que todas las variables de entorno estén correctas

---

## 💰 Costos

Railway ofrece:
- ✅ $5 USD gratis al mes
- ✅ 500 horas de ejecución gratis
- ✅ Suficiente para desarrollo y pruebas
