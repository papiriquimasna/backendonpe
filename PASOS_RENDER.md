# 🚀 Pasos Rápidos - Desplegar en Render

## 1️⃣ Ir a Render
https://render.com → "Get Started for Free"

## 2️⃣ Crear Web Service
- New + → Web Service
- Conectar: `papiriquimasna/backendonpe`

## 3️⃣ Configurar

**Build Command**:
```
./mvnw clean package -DskipTests
```

**Start Command**:
```
java -jar target/proyecto-0.0.1-SNAPSHOT.jar --spring.profiles.active=supabase
```

**Instance Type**: Free

## 4️⃣ Variables de entorno

Agregar del archivo `.env`:
- SPRING_PROFILES_ACTIVE
- PORT
- DATABASE_URL
- DATABASE_USERNAME
- DATABASE_PASSWORD
- SUPABASE_URL
- SUPABASE_ANON_KEY
- SUPABASE_SERVICE_ROLE_KEY
- JWT_SECRET
- MAIL_USERNAME
- MAIL_PASSWORD
- RENIEC_API_TOKEN

## 5️⃣ Deploy
Click "Create Web Service" → Esperar 3-5 minutos

## 6️⃣ Probar
```
https://tu-app.onrender.com/actuator/health
```

## 7️⃣ Frontend
Crear `onpe-app/.env.local`:
```
VITE_API_URL=https://tu-app.onrender.com/api
```

---

**Guía completa**: Ver `DESPLEGAR_EN_RENDER.md`
