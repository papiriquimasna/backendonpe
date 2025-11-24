# 🚀 Desplegar Backend en Render con SQLite

## ✅ Configuración lista
- SQLite configurado (base de datos gratis)
- No necesita servidor de base de datos externo
- Código ya está en GitHub

---

## 📝 CREAR NUEVO WEB SERVICE

### 1️⃣ Ir a Render
https://render.com → Dashboard

### 2️⃣ Crear Web Service
- Click **"New +"** → **"Web Service"**
- Click **"Connect a repository"**
- Selecciona: `papiriquimasna/backendonpe`
- Click **"Connect"**

---

## 3️⃣ CONFIGURACIÓN DEL SERVICIO

**Name**: `backend-onpe` (o el que quieras)

**Region**: `Oregon (US West)`

**Branch**: `main`

**Language**: `Docker` ✅

**Root Directory**: (déjalo vacío)

**Build Command**: (déjalo vacío - Docker lo maneja)

**Start Command**: (déjalo vacío - Docker lo maneja)

**Instance Type**: **Free** ✅

---

## 4️⃣ VARIABLES DE ENTORNO

Scroll hacia abajo hasta **"Environment Variables"** y agrega:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `sqlite` |
| `PORT` | `8080` |
| `JWT_SECRET` | `miClaveSecretaSuperSeguraParaJWT2024ProyectoPeruano123456789` |
| `MAIL_USERNAME` | `9909d4001@smtp-brevo.com` |
| `MAIL_PASSWORD` | (tu password de Brevo) |
| `RENIEC_API_TOKEN` | `eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im9jaG9hcmV5ZXNqb3N1ZUBnbWFpbC5jb20ifQ.9t8mfh_fDcqLwuVhi1x43XMLWDJvEjzG7tuXCPJsh2I` |

**Opcionales (si usas Supabase Storage):**
| Key | Value |
|-----|-------|
| `SUPABASE_URL` | `https://ubbnhtkzsrcexiwgimnp.supabase.co` |
| `SUPABASE_ANON_KEY` | `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InViYm5odGt6c3JjZXhpd2dpbW5wIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM5MTg4MzEsImV4cCI6MjA3OTQ5NDgzMX0.FpDrOl2vjy2eRDlixzGyAHKsw2P15P74Cb4ea52Hd6s` |
| `SUPABASE_SERVICE_ROLE_KEY` | `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InViYm5odGt6c3JjZXhpd2dpbW5wIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc2MzkxODgzMSwiZXhwIjoyMDc5NDk0ODMxfQ.m0joVinUQ8F7KV4SDFCJhr0xl50pbOfm9U_3L3Ffekw` |

---

## 5️⃣ CREAR SERVICIO

Click en **"Create Web Service"** (botón azul al final)

Render comenzará a:
1. Clonar el repositorio
2. Construir la imagen Docker
3. Desplegar la aplicación
4. Crear la base de datos SQLite automáticamente

Tomará 3-5 minutos.

---

## 6️⃣ VERIFICAR

Una vez desplegado:

1. Copia la URL (ej: `https://backend-onpe.onrender.com`)

2. Abre en el navegador:
```
https://backend-onpe.onrender.com/actuator/health
```

Deberías ver:
```json
{"status":"UP"}
```

---

## 7️⃣ CONFIGURAR FRONTEND

Crea `onpe-app/.env.local`:
```bash
VITE_API_URL=https://backend-onpe.onrender.com/api
```

Ejecuta el frontend:
```bash
cd onpe-app
npm run dev
```

---

## ✅ ¡LISTO!

Tu arquitectura:
- **Frontend**: `http://localhost:5173` (tu PC)
- **Backend**: `https://backend-onpe.onrender.com` (Render)
- **Base de datos**: SQLite (archivo local en Render)

---

## ⚠️ IMPORTANTE

**SQLite en Render Free:**
- ✅ Funciona perfectamente
- ✅ Gratis
- ⚠️ Los datos se borran cada vez que el servicio se reinicia (después de 15 min de inactividad)
- ⚠️ Para datos persistentes, necesitas un plan de pago o usar Railway

**Para datos persistentes gratis:**
- Usa Railway (tiene PostgreSQL gratis)
- O usa Supabase directamente desde el código

---

## 🔧 Troubleshooting

### Error: "Build failed"
- Verifica que el código esté en GitHub
- Revisa los logs de build

### Error: "Application failed to start"
- Verifica las variables de entorno
- Revisa los logs de la aplicación

### Los datos se borran
- Es normal en Render Free con SQLite
- Usa Railway para persistencia gratis
