# 🚀 Desplegar Backend en Railway (GRATIS)

## 📋 Requisitos previos
- ✅ Cuenta de GitHub
- ✅ Tu proyecto subido a GitHub
- ✅ Cuenta en Railway (gratis)

---

## 🎯 PASO 1: Preparar el proyecto

### 1.1 Crear archivo `railway.json` en la raíz del proyecto
Este archivo le dice a Railway cómo construir tu app:

```json
{
  "$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS"
  },
  "deploy": {
    "startCommand": "java -jar target/proyecto-0.0.1-SNAPSHOT.jar --spring.profiles.active=supabase",
    "restartPolicyType": "ON_FAILURE",
    "restartPolicyMaxRetries": 10
  }
}
```

### 1.2 Crear archivo `nixpacks.toml` en la raíz del proyecto
Este archivo configura el build:

```toml
[phases.setup]
nixPkgs = ['maven', 'jdk21']

[phases.build]
cmds = ['mvn clean package -DskipTests']

[start]
cmd = 'java -jar target/proyecto-0.0.1-SNAPSHOT.jar --spring.profiles.active=supabase'
```

### 1.3 Verificar que tienes `.gitignore` correcto
Asegúrate de que `target/` esté en `.gitignore` (ya debería estar).

---

## 🎯 PASO 2: Subir a GitHub

```bash
# Si aún no has inicializado git:
git init
git add .
git commit -m "Preparar para despliegue en Railway"

# Crear repositorio en GitHub y conectarlo:
git remote add origin https://github.com/TU_USUARIO/TU_REPO.git
git branch -M main
git push -u origin main
```

---

## 🎯 PASO 3: Desplegar en Railway

### 3.1 Crear cuenta en Railway
1. Ve a https://railway.app
2. Click en "Start a New Project"
3. Conecta con GitHub

### 3.2 Crear nuevo proyecto
1. Click en "Deploy from GitHub repo"
2. Selecciona tu repositorio
3. Railway detectará automáticamente que es un proyecto Java/Maven

### 3.3 Configurar variables de entorno
En el dashboard de Railway, ve a "Variables" y agrega:

```bash
SPRING_PROFILES_ACTIVE=supabase
PORT=8080
```

### 3.4 Esperar el despliegue
- Railway construirá tu proyecto automáticamente
- Tomará 2-5 minutos la primera vez
- Te dará una URL pública como: `https://tu-proyecto.up.railway.app`

---

## 🎯 PASO 4: Configurar CORS en el backend

Antes de desplegar, asegúrate de que tu backend permita peticiones desde localhost:

En `src/main/java/com/proyecto/proyecto/config/WebConfig.java` (o donde tengas CORS):

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5173",  // Vite local
                    "http://localhost:3000",  // React local
                    "https://tu-frontend.vercel.app"  // Si despliegas frontend después
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

---

## 🎯 PASO 5: Actualizar frontend para usar la URL de Railway

En `onpe-app/src/services/api.ts`:

```typescript
const API_URL = import.meta.env.VITE_API_URL || 'https://tu-proyecto.up.railway.app';

export const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});
```

Crea archivo `.env.local` en `onpe-app/`:

```bash
VITE_API_URL=https://tu-proyecto.up.railway.app
```

---

## 🎯 PASO 6: Probar la conexión

### 6.1 Verificar que el backend está corriendo
Abre en el navegador:
```
https://tu-proyecto.up.railway.app/actuator/health
```

Deberías ver:
```json
{"status":"UP"}
```

### 6.2 Probar un endpoint
```
https://tu-proyecto.up.railway.app/api/auth/test
```

### 6.3 Ejecutar frontend localmente
```bash
cd onpe-app
npm run dev
```

Tu frontend local ahora se conectará al backend en Railway.

---

## 🔧 Comandos útiles de Railway

### Ver logs en tiempo real:
```bash
# Instalar Railway CLI (opcional)
npm i -g @railway/cli

# Login
railway login

# Ver logs
railway logs
```

---

## ⚠️ Troubleshooting

### Error: "Application failed to start"
- Revisa los logs en Railway
- Verifica que las variables de entorno estén correctas
- Asegúrate de que Supabase esté accesible

### Error: "CORS policy"
- Verifica que la URL de Railway esté en `allowedOrigins`
- Asegúrate de que `allowCredentials(true)` esté configurado

### Error: "Connection refused"
- Verifica que el puerto sea 8080
- Asegúrate de que la variable `PORT=8080` esté configurada

---

## 💰 Costos

Railway ofrece:
- ✅ **$5 USD gratis al mes** (suficiente para desarrollo)
- ✅ 500 horas de ejecución gratis
- ✅ Sin tarjeta de crédito requerida inicialmente

---

## 🎉 ¡Listo!

Ahora tienes:
- ✅ Backend desplegado en Railway con Supabase
- ✅ Frontend corriendo localmente conectándose al backend en la nube
- ✅ Base de datos PostgreSQL en Supabase funcionando

**Próximo paso:** Desplegar el frontend en Vercel o Netlify (opcional)
