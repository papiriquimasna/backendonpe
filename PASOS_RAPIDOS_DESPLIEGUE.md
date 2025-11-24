# 🚀 Pasos Rápidos para Desplegar Backend

## ✅ Archivos ya creados:
- `railway.json` - Configuración de Railway
- `nixpacks.toml` - Configuración de build
- `src/main/java/com/proyecto/proyecto/config/WebConfig.java` - CORS configurado
- `onpe-app/src/services/api.ts` - Actualizado para usar variables de entorno

---

## 📝 PASO 1: Subir a GitHub

```bash
git add .
git commit -m "Preparar para despliegue en Railway"
git push
```

---

## 🚂 PASO 2: Desplegar en Railway

1. Ve a https://railway.app
2. Click "Start a New Project"
3. Conecta con GitHub
4. Selecciona tu repositorio
5. Agrega estas variables de entorno:
   ```
   SPRING_PROFILES_ACTIVE=supabase
   PORT=8080
   ```
6. Espera 2-5 minutos
7. Copia la URL que te da Railway (ej: `https://proyecto-production-xxxx.up.railway.app`)

---

## 🎨 PASO 3: Configurar Frontend

Crea archivo `onpe-app/.env.local`:

```bash
VITE_API_URL=https://tu-proyecto.up.railway.app/api
```

Reemplaza `tu-proyecto.up.railway.app` con la URL real de Railway.

---

## ▶️ PASO 4: Ejecutar Frontend

```bash
cd onpe-app
npm run dev
```

---

## ✅ PASO 5: Probar

1. Abre http://localhost:5173
2. Intenta registrarte
3. El frontend local se conectará al backend en Railway
4. El backend en Railway se conectará a Supabase

---

## 🎉 ¡Listo!

Tu arquitectura:
- Frontend: `http://localhost:5173` (tu PC)
- Backend: `https://tu-proyecto.up.railway.app` (Railway)
- Base de datos: Supabase PostgreSQL (nube)

---

## 🔍 Verificar que funciona

Abre en el navegador:
```
https://tu-proyecto.up.railway.app/actuator/health
```

Deberías ver: `{"status":"UP"}`
