# 🚀 Desplegar Backend en Render (GRATIS)

## ✅ Repositorio listo
**GitHub**: https://github.com/papiriquimasna/backendonpe

---

## 📝 PASOS PARA DESPLEGAR

### 1️⃣ Crear cuenta en Render
1. Ve a https://render.com
2. Click en "Get Started for Free"
3. Regístrate con GitHub (recomendado)
4. Confirma tu email

---

### 2️⃣ Crear nuevo Web Service
1. En el dashboard de Render, click en **"New +"**
2. Selecciona **"Web Service"**
3. Click en **"Connect a repository"**
4. Busca y selecciona: `papiriquimasna/backendonpe`
5. Click en **"Connect"**

---

### 3️⃣ Configurar el servicio

Render detectará automáticamente que es un proyecto Java. Configura así:

**Name**: `backend-onpe` (o el nombre que quieras)

**Region**: `Oregon (US West)` (o el más cercano a ti)

**Branch**: `main`

**Runtime**: `Java` (se detecta automáticamente)

**Build Command**:
```bash
./mvnw clean package -DskipTests
```

**Start Command**:
```bash
java -jar target/proyecto-0.0.1-SNAPSHOT.jar --spring.profiles.active=supabase
```

**Instance Type**: `Free` ✅

---

### 4️⃣ Agregar variables de entorno

Scroll hacia abajo hasta **"Environment Variables"** y agrega estas variables (copia del archivo `.env`):

Click en **"Add Environment Variable"** para cada una:

**IMPORTANTE**: Copia los valores del archivo `.env` que tienes localmente.

Las variables necesarias son:
- `SPRING_PROFILES_ACTIVE` = `supabase`
- `PORT` = `8080`
- `DATABASE_URL` = (del archivo .env)
- `DATABASE_USERNAME` = (del archivo .env)
- `DATABASE_PASSWORD` = (del archivo .env)
- `SUPABASE_URL` = (del archivo .env)
- `SUPABASE_ANON_KEY` = (del archivo .env)
- `SUPABASE_SERVICE_ROLE_KEY` = (del archivo .env)
- `JWT_SECRET` = (del archivo .env)
- `MAIL_USERNAME` = (del archivo .env)
- `MAIL_PASSWORD` = (del archivo .env)
- `RENIEC_API_TOKEN` = (del archivo .env)

---

### 5️⃣ Crear el servicio
1. Click en **"Create Web Service"** (botón azul al final)
2. Render comenzará a construir tu aplicación
3. Verás los logs en tiempo real
4. Tomará 3-5 minutos la primera vez

---

### 6️⃣ Obtener la URL
Una vez que el despliegue termine:
1. Verás un mensaje: **"Your service is live 🎉"**
2. La URL estará arriba, algo como: `https://backend-onpe.onrender.com`
3. Copia esta URL

---

### 7️⃣ Probar el backend
Abre en el navegador:
```
https://backend-onpe.onrender.com/actuator/health
```

Deberías ver:
```json
{"status":"UP"}
```

---

## 🎨 Configurar el frontend

### Crear archivo `.env.local` en `onpe-app/`:
```bash
VITE_API_URL=https://backend-onpe.onrender.com/api
```

Reemplaza `backend-onpe.onrender.com` con tu URL real de Render.

### Ejecutar frontend:
```bash
cd onpe-app
npm run dev
```

---

## ✅ ¡Listo!

Tu arquitectura:
- **Frontend**: `http://localhost:5173` (tu PC)
- **Backend**: `https://backend-onpe.onrender.com` (Render)
- **Base de datos**: Supabase PostgreSQL (nube)

---

## 🔧 Comandos útiles

### Ver logs en tiempo real:
1. Ve a tu servicio en Render
2. Click en la pestaña **"Logs"**
3. Verás todos los logs de la aplicación

### Redesplegar manualmente:
1. Ve a tu servicio en Render
2. Click en **"Manual Deploy"** → **"Deploy latest commit"**

### Actualizar variables de entorno:
1. Ve a **"Environment"** en el menú lateral
2. Edita o agrega variables
3. Click en **"Save Changes"**
4. Render redesplegará automáticamente

---

## ⚠️ Troubleshooting

### Error: "Build failed"
- Revisa los logs de build
- Verifica que el comando de build sea correcto
- Asegúrate de que Java 21 esté configurado

### Error: "Application failed to start"
- Revisa los logs de la aplicación
- Verifica que todas las variables de entorno estén correctas
- Asegúrate de que Supabase esté accesible

### Error: "CORS policy"
- Verifica que `WebConfig.java` tenga la URL de Render en `allowedOrigins`
- Puede que necesites agregar: `https://*.onrender.com`

---

## 💰 Plan Gratuito de Render

Render ofrece:
- ✅ **750 horas gratis al mes** (suficiente para 1 servicio 24/7)
- ✅ Despliegues automáticos desde GitHub
- ✅ SSL/HTTPS gratis
- ✅ Sin tarjeta de crédito requerida
- ⚠️ El servicio se "duerme" después de 15 minutos de inactividad
- ⚠️ La primera petición después de dormir toma ~30 segundos

---

## 🚀 Ventajas de Render sobre Railway

✅ Más estable para Java/Spring Boot
✅ Mejor documentación
✅ Logs más claros
✅ Menos problemas de build
✅ 750 horas gratis vs 500 de Railway

---

## 📞 Soporte

Si tienes problemas:
1. Revisa los logs en Render
2. Verifica las variables de entorno
3. Asegúrate de que la URL en el frontend sea correcta
4. Prueba el endpoint `/actuator/health` primero
